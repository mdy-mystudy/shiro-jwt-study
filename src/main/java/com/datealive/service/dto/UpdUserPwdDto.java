package com.datealive.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName: UpdUserPwdDto
 * @Description: TODO
 * @author: datealive
 * @date: 2021/3/5  22:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdUserPwdDto implements Serializable {

    private String username;
    private String oldPassword;
    private String newPassword;
}
