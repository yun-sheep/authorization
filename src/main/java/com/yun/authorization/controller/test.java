package com.yun.authorization.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @Description 测试knife4接口文档
 * @auther j2-yizhiyang
 * @date 2023/3/7 14:34
 */
@Api("测试文档")
@RestController
@RequestMapping("test")
public class test {
    @ApiOperation(value = "测试接口")
    @GetMapping("hello")
    public String hello(@RequestParam String name){
        return "测试结果"+name;

    }
}
