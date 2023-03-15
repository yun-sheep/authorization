package com.yun.authorization.comon.filter;

import com.yun.authorization.comon.constant.RedisConstant;
import com.yun.authorization.comon.constant.TokenConstant;
import com.yun.authorization.comon.domain.JwtUser;
import com.yun.authorization.comon.utils.JwtUtils;
import com.yun.authorization.comon.utils.RedisCache;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @Description 处理所有（除了/login之外的hhtp请求）认证token的Filter
 * @auther j2-yizhiyang
 * @date 2023/3/8 21:11
 */
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Override
    protected  void doFilterInternal(HttpServletRequest httpServletRequest,
                                     HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //获取请求头的中的token
        String token = httpServletRequest.getParameter(TokenConstant.TOKEN_HEAD);
        //如果请求头中没有token
        if(!StringUtils.hasText(token)){
            //TODO 是否在访问白名单中（在的话，就放行）
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            httpServletResponse.getWriter().write("未经授权访问！");
            return;
        }
        //解析token
        String userid;
        try{
            Claims claims = JwtUtils.getClaimsByToken(token);
            userid = claims.getSubject();
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("token非法");
        }
        //获取用户信息（先从redis找，如果redi没有再找数据库(还要不要在数据库中找））
        String redisKey = RedisConstant.LOGINKEY+userid;
        //TODO 在redis中查询
        JwtUser jwtUser = redisCache.getCacheObject(redisKey);
        if(Objects.isNull(jwtUser)){
            throw new RuntimeException("用户没有登录");
        }
        //TODO 每次携带正确的token访问之后刷新redis中存储的token的过期时间
        //生成UsernamePasswordAuthenticationToken(直接把jwt
        //存入SecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(jwtUser,null,null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //放行
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
