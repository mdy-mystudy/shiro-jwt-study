package com.datealive.exception;

import com.datealive.common.Result;
import com.datealive.common.ResultCode;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @ClassName: ExceptionController
 * @Description: TODO
 * @author: datealive
 * @date: 2021/3/5  20:29
 */
@RestControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = RuntimeException.class)
    public Result handler(RuntimeException e) {
        return Result.error(e.getMessage());
    }


    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = ShiroException.class)
    public Result handler(ShiroException e) {
        return Result.error(ResultCode.Un_authorized,e.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = UnauthorizedException.class)
    public Result handler(UnauthorizedException e) {
        return Result.error(ResultCode.Forbidden,"权限不足");
    }

}
