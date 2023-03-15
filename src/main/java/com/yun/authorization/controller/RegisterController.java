package com.yun.authorization.controller;

import com.yun.authorization.comon.domain.JsonVO;
import com.yun.authorization.comon.domain.dto.UserDto;
import com.yun.authorization.mapper.UserMapper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 用户注册接口
 * @auther j2-yizhiyang
 * @date 2023/3/9 20:31
 */
@RestController
@RequestMapping("user")
@Api(tags = "用户注册")
@Validated
public class RegisterController {
    @Autowired
    private UserMapper userMapper;
    //TODO 阿里命名规范post的类型怎么写
    public JsonVO<String> UserRegister(UserDto userDto){
        //TODO 实现注册操作
        return null;
    }
}
