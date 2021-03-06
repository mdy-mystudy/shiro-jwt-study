package com.datealive;

import com.datealive.mapper.UserMapper;
import com.datealive.pojo.User;
import com.datealive.utils.JwtUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
class JwtstudyApplicationTests {




    @Test
    void contextLoads() {

        String token = JwtUtils.getToken("admin", "admin");
        System.out.println(token);
        System.out.println(JwtUtils.verify(token,"admin","admin"));
        System.out.println(JwtUtils.getUsername(token));
    }

}
