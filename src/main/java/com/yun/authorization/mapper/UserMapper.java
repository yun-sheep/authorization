package com.yun.authorization.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yun.authorization.dao.User;
import org.apache.ibatis.annotations.Mapper;



/**
 * @Description 查询User
 * @auther j2-yizhiyang
 * @date 2023/3/9 20:25
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
