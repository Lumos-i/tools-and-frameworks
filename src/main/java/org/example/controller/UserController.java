package org.example.controller;

import org.example.annotation.RequestMany;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zrq
 * @ClassName UserController
 * @date 2023/11/22 9:46
 * @Description TODO
 */
@RestController
@RequestMapping("/test")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/demo")
    @RequestMany(value = "redisIdempotentStrategy", expireTime = 2)
    public void test(Integer id){
        userService.get(id);
    }
}
