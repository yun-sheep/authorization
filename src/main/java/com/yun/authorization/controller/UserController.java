package com.yun.authorization.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yun.authorization.comon.domain.JsonVO;
import com.yun.authorization.comon.domain.JwtUser;
import com.yun.authorization.dao.User;
import com.yun.authorization.mapper.UserMapper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Wrapper;

/**
 * @Description
 * @auther j2-yizhiyang
 * @date 2023/3/10 19:19
 */
@RestController
@RequestMapping("/usermassege")
@Api(value = "用户相关的操作")
public class UserController {
    @Autowired
    private UserMapper userMapper;
    //获取某个用户的详细信息
    @RequestMapping("/detail")
    public JsonVO<User> getUser(){
        //从SecurityContextHolder获取当前用户信息
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        //之前存进去的是什么对象就是什么对象
        JwtUser jwtUser =  (JwtUser) authentication.getPrincipal();
        String username = jwtUser.getUsername();
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username",username);
        User user =  userMapper.selectOne(userQueryWrapper);
        return JsonVO.success(user);
    }

}
