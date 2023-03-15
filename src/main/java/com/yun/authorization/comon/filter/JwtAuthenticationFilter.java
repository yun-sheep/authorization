package com.yun.authorization.comon.filter;

import com.yun.authorization.comon.constant.TokenConstant;
import com.yun.authorization.comon.domain.JwtUser;
import com.yun.authorization.comon.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description 自定义认证过滤器，表单登录拦截器，校验成功之后返回一个token给客户端
 * @auther j2-yizhiyang
 * @date 2023/3/8 15:27
 */

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    /**
     * 认证管理器
     */

    private final AuthenticationManager authenticationManager;
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
        //浏览器访问 http://localhost:8080/user/login 会通过 JWTAuthenticationFilter
        super.setFilterProcessesUrl("/user/login");
        super.setUsernameParameter("name");
    }
    /**
     * 获取用户名和密码
     * @param  request 请求
     * @return Authentication
     * */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
    throws AuthenticationException {
        //从http中获得表单参数（是在哪里实现查询数据库进行验证的）
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        try{
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userName,password)
            );

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 验证成功之后，生成token返回
     *
     * */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        //验证成功，就生成Token返回
        JwtUser jwtUser = (JwtUser) authResult.getPrincipal();
        //TODO 使用设置的统一返回格式返回信息
        String token = JwtUtils.generateToken(jwtUser.getUsername());
        response.getWriter().write(TokenConstant.TOKEN_HEAD+token);
        System.out.println(token);
        //TODO 把用户信息存在redis

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
        response.getWriter().write("authentication failed, reason: " + failed.getMessage());
    }
}
