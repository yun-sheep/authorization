package com.yun.authorization.comon.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Description jwt工具类，生成
 * @auther j2-yizhiyang
 * @date 2023/3/7 16:13
 */
public class JwtUtils {
    private static long expire = 604800;
    private static String secret = "abcdefghabcdefghabcdefghabcdefgh";
    private static String header=  "token";
    //生成JWT
    public static String generateToken(String username) {

        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime() + 1000 * expire);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(username) //设置主题（声明信息）
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)    // 7天过期
                .signWith(SignatureAlgorithm.HS512, secret)//设置安全密钥（生成签名所需要的密钥和算法）
                .compact();//// 生成token（1.编码 Header 和 Payload 2.生成签名 3.拼接字符串）
    }
    // 解析JWT
    public static Claims getClaimsByToken(String jwt) {
        //token的解析内容是封装在claim里面的（类似于一个map）
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }

    // 判断JWT是否过期
    public static boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }
}
