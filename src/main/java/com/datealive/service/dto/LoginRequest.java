package com.datealive.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName: LoginRequest
 * @Description: TODO
 * @author: datealive
 * @date: 2021/3/4  18:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest implements Serializable {

    private String username;

    private String password;
}
