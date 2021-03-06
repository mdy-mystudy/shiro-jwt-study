package com.datealive.controller;

import com.datealive.common.Result;
import com.datealive.pojo.User;
import com.datealive.service.UserService;
import com.datealive.service.dto.LoginRequest;
import com.datealive.service.dto.UpdUserPwdDto;
import com.datealive.utils.JwtUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName: UserController
 * @Description: TODO
 * @author: datealive
 * @date: 2021/3/4  16:45
 */
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public Result LoginSys(@RequestBody LoginRequest loginRequest, HttpServletResponse response){
        User userByPwd = userService.getUserByPwd(loginRequest.getUsername());
        String hashMd5AfterPassword = new Md5Hash(loginRequest.getPassword(), loginRequest.getUsername(), 1024).toHex();
        System.out.println("加密后===》"+hashMd5AfterPassword);
        if(!userByPwd.getPassword().equals(hashMd5AfterPassword)){
            return Result.error("登录失败");
        }
        String token = JwtUtils.getToken(userByPwd.getUsername(), hashMd5AfterPassword);

        response.setHeader("Authorization", token);
        response.setHeader("Access-control-Expose-Headers", "Authorization");

        return Result.success("登录成功",userByPwd);
    }

    @GetMapping("/isexpires")
    public Result isExpires(@RequestParam("token")String token){
        boolean tokenExpired = JwtUtils.isTokenExpiredSubHours(token);
        return Result.success("过期了嘛",tokenExpired);
    }

    @PostMapping("/changePassword")
    public Result changePwd(@RequestBody UpdUserPwdDto updUserPwdDto){
        User userByPwd = userService.getUserByPwd(updUserPwdDto.getUsername());
        String oldPassword = new Md5Hash(updUserPwdDto.getOldPassword(), updUserPwdDto.getUsername(), 1024).toHex();
        if(userByPwd!=null&&userByPwd.getPassword().equals(oldPassword)){
            String newPassword = new Md5Hash(updUserPwdDto.getNewPassword(), updUserPwdDto.getUsername(), 1024).toHex();
            userService.updUserPassword(updUserPwdDto.getUsername(),newPassword);
            return Result.success("修改密码成功");
        }else{
            return Result.error("用户不存在");
        }
    }

    @GetMapping("/adminUser")
    @RequiresRoles("admin")
    public Result AdminSys(){
        return Result.success("这是管理员的页面，vip用户不能访问");
    }

    @GetMapping("/system")
    @RequiresAuthentication
    public Result System(){
        return Result.success("登录成功才能看到的页面");
    }

    @GetMapping("/vip1")
    @RequiresPermissions(logical = Logical.AND,value = {"vip1"})
    public Result vip1(){
        return Result.success("vip1页面");
    }

    @GetMapping("/vip2")
    @RequiresPermissions(logical = Logical.AND,value = {"vip2"})
    public Result vip2(){
        return Result.success("vip2页面");
    }

    @GetMapping("/vip3")
    @RequiresPermissions(logical = Logical.AND,value = {"vip3"})
    public Result vip3(){
        return Result.success("vip3页面");
    }

    @GetMapping("/vip12")
    @RequiresPermissions(logical = Logical.AND,value = {"vip1","vip2"})
    public Result vip12(){
        return Result.success("vip12页面");
    }

    @GetMapping("/vip123")
    @RequiresPermissions(logical = Logical.AND,value = {"vip1","vip2","vip3"})
    public Result vip123(){
        return Result.success("vip123页面");
    }





}
