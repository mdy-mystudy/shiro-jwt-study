package com.datealive.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: User
 * @Description: TODO
 * @author: datealive
 * @date: 2021/3/4  16:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;

    private String username;

    private String password;

    private String role;

    private String permission;
}
