package com.datealive.shiro;

import com.datealive.pojo.User;
import com.datealive.service.UserService;
import com.datealive.utils.JwtUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName: MyShiroRealm
 * @Description: TODO
 * @author: datealive
 * @date: 2021/3/5  20:54
 */
@Component
public class MyShiroRealm extends AuthorizingRealm {


    @Autowired
    UserService userService;

    /**
     * 这里需要重写supports方法
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = JwtUtils.getUsername(principals.toString());
        User user = userService.getUserByPwd(username);
        SimpleAuthorizationInfo authorizationInfo=new SimpleAuthorizationInfo();
        authorizationInfo.addRole(user.getRole());
        Set<String> permisssion = new HashSet<>(Arrays.asList(user.getPermission().split(",")));
        authorizationInfo.addStringPermissions(permisssion);
        return authorizationInfo;
    }

    /**
     * 认证
     * @param auth
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        String username = JwtUtils.getUsername(token);
        User user = userService.getUserByPwd(username);
        System.out.println("user是==>"+user);
        if(user==null){
            throw new AuthenticationException("用户不存在");
        }
        if(JwtUtils.isTokenExpired(token)){
            throw new AuthenticationException("token过期");
        }
        if(!JwtUtils.verify(token,username,user.getPassword())){
            throw new AuthenticationException("用户名或者密码错误");
        }
        return new SimpleAuthenticationInfo(token,token,"my_realm");
    }
}
