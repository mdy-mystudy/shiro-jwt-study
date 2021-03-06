package com.datealive.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @ClassName: JWTToken
 * @Description: TODO
 * @author: datealive
 * @date: 2021/3/5  20:41
 */
public class JwtToken implements AuthenticationToken {

    private String token;

    public JwtToken(String token){
        this.token=token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
