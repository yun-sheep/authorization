package com.yun.authorization.config;

import com.yun.authorization.comon.filter.JwtAuthenticationFilter;
import com.yun.authorization.comon.filter.JwtAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * @Description 添加SecurityConfig的配置类
 * @auther j2-yizhiyang
 * @date 2023/3/9 15:29
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity httpSecurity)throws Exception{
            httpSecurity
                    .csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/auth/**").permitAll()
                    .anyRequest().hasAnyAuthority("ADMIN") // .authenticated()
                    .and()
                    .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                    // 让校验 Token 的过滤器在身份认证过滤器之后
                    .addFilterAfter(new JwtAuthorizationFilter(), JwtAuthenticationFilter.class)
                    // 既然启用 JWT, 那就彻底点, 不需要 Session
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
