package org.example.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author zrq
 * @ClassName UserServiceImpl
 * @date 2023/11/22 9:45
 * @Description TODO
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Override
    public void get(Integer id) {
        log.info("111111111111111111111111111111");
    }
}
