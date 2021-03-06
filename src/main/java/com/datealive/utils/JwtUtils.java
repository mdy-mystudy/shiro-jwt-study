package com.datealive.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Data;
import org.springframework.stereotype.Component;


import java.util.Calendar;
import java.util.Date;

/**
 * @ClassName: JwtUtils
 * @Description: TODO
 * @author: datealive
 * @date: 2021/3/5  10:43
 */
@Data
public class JwtUtils {

    /**
     * 过期时间 2分钟
     */
    private static final long EXPIRE_TIME = 3*60*1000;


    /**
     * 校验token是否正确
     * @param token 密钥
     * @param username 用户名
     * @param secret 密钥
     * @return
     */
    public static boolean verify(String token,String username,String secret){
        try {
            Algorithm algorithm=Algorithm.HMAC256(secret);
            JWTVerifier verifier= JWT.require(algorithm)
                    .withClaim("username",username)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        }catch (Exception e){
            return  false;
        }
    }

    /**
     * 获取token中的信息
     * @param token
     * @return
     */
    public static String getUsername(String token){
        try{
            DecodedJWT decode = JWT.decode(token);
            return decode.getClaim("username").asString();
        }catch (JWTDecodeException e){
            return null;
        }

    }


    /**
     * 生成token
     * @param username 用户名
     * @param secret 用户密码
     * @return
     */
    public static String getToken(String username,String secret){
        try{
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            Algorithm algorithm=Algorithm.HMAC256(secret);
            return JWT.create()
                    .withClaim("username",username)
                    .withExpiresAt(date)
                    .sign(algorithm);
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 判断token是否过期，这里采取了返回过期时间比实际时间少
     * @param token
     * @return
     */
    public static boolean isTokenExpiredSubHours(String token){
        Date expiresAt = JWT.decode(token).getExpiresAt();
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(expiresAt);
        calendar.add(Calendar.MINUTE,-1);
        Date toExpiresAt = calendar.getTime();
        return toExpiresAt.before(new Date());
    }

    /**
     * 判断token是否过期
     * @param token
     * @return
     */
    public static boolean isTokenExpired(String token){
        Date expiresAt = JWT.decode(token).getExpiresAt();
        return expiresAt.before(new Date());
    }




}
