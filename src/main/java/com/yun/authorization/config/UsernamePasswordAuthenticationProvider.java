package com.yun.authorization.config;

import com.yun.authorization.service.Imp.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @Description 继承AuthenticationProvider接口，进行用户验证
 * @auther j2-yizhiyang
 * @date 2023/3/9 18:59
 */
@Component
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserDetailServiceImpl userDetailService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //获取用户输入的用户名和密码
        String usename = authentication.getName();
        String password = authentication.getCredentials().toString();
        //获取封装用户信息的对象
        UserDetails userDetails = userDetailService.loadUserByUsername(usename);
        //进行密码的对比
        //boolean flag = bCryptPasswordEncoder.matches(password, userDetails.getPassword());
        boolean flag = password.equals(userDetails.getPassword());
        if (flag) {
            //把当前的用户信息以及权限信息都封装到这个里面
            return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        }
        throw new AuthenticationException("用户名或者密码错误") {
        };

    }
    /**
     *控制对于何种 Authentication 的实现, 启用当前 AuthenticationProvider,
     * 由于我们采用 UsernamePasswordAuthenticationToken, 所以在这里也作出限制
     * */
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
    //密码加密器
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
