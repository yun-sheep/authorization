package com.yun.authorization.service.Imp;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yun.authorization.comon.domain.JwtUser;
import com.yun.authorization.dao.Authentication;
import com.yun.authorization.mapper.AuthenticationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @Description 重写UserDetailService方法来改变登录校验方式
 * @auther j2-yizhiyang
 * @date 2023/3/9 16:34
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    //重写这个方法，从数据库中查找这个User的信息（默认这个方法是从内存中加载User，所以重写来自定义
    @Autowired
    private AuthenticationMapper authenticationMapper;
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        //根据用户名在数据库中查找
        /*QueryWrapper<Authentication> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",name);
        Authentication authentication =  authenticationMapper.selectOne(queryWrapper);*/
        System.out.println("执行查找数据库");
        String password = "1223";
        JwtUser jwtUser  = new JwtUser();
        jwtUser.setUsername(name);
        jwtUser.setPassword(password);
        return jwtUser;
        //转换成UserDatials返回
        //return BeanUtil.copyProperties(authentication,UserDetails.class);
    }
}
