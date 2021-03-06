package com.datealive.mapper;

import com.datealive.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: UserMapper
 * @Description: TODO
 * @author: datealive
 * @date: 2021/3/4  16:30
 */
@Mapper
@Repository
public interface UserMapper {


    /**
     * 根据用户名和密码查询用户
     * @param username
     * @return
     */
    User getUserByPwd(@Param("username")String username);

    /**
     * 用户修改密码
     * @param username
     * @param password
     * @return
     */
    int updUserPassword(@Param("username")String username,@Param("password") String password);

}
