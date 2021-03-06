package com.datealive.service.impl;

import com.datealive.mapper.UserMapper;
import com.datealive.pojo.User;
import com.datealive.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: UserServiceImpl
 * @Description: TODO
 * @author: datealive
 * @date: 2021/3/4  18:24
 */
@Service("UserServiceImpl")
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User getUserByPwd(String username) {
        User userByPwd = userMapper.getUserByPwd(username);
        if(userByPwd!=null){
            return userByPwd;
        }else{
            return null;
        }
    }

    @Override
    public boolean updUserPassword(String username, String password) {
        int updUserPassword = userMapper.updUserPassword(username, password);
        if(updUserPassword>0){
            return true;
        }else{
            return false;
        }
    }
}
