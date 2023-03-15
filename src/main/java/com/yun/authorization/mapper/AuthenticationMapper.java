package com.yun.authorization.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yun.authorization.dao.Authentication;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description
 * @auther j2-yizhiyang
 * @date 2023/3/11 14:44
 */
@Mapper
public interface AuthenticationMapper extends BaseMapper<Authentication> {
}
