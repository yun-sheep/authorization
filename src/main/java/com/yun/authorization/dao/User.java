package com.yun.authorization.dao;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Description 用户的详细信息（头像，昵称，账号等等）
 * @auther j2-yizhiyang
 * @date 2023/3/9 20:28
 */
@Getter
@Setter
@Data
public class User implements Serializable {
    private String name;
}
