package com.datealive.service;

import com.datealive.pojo.User;
import org.springframework.stereotype.Component;

/**
 * @ClassName: UserService
 * @Description: TODO
 * @author: datealive
 * @date: 2021/3/4  18:09
 */
public interface UserService {
    /**
     * 根据用户名获取用户
     * @param username
     * @return
     */
    User getUserByPwd(String username);

    /**
     * 修改密码
     * @param username
     * @param password
     * @return
     */
    boolean updUserPassword(String username,String password);



}
