package com.test.activiti.controller;

import com.test.activiti.service.DistributeIdService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lipo
 * @date 2020/7/17
 */

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private DistributeIdService distributeIdService;

    @RequestMapping("/getNextId")
    public Object getNextId(String idName) {
        for (int i = 0; i < 100; i++) {
            new Thread(() -> System.out.println(distributeIdService.getNextId(idName))).start();
        }
        return "ok";
    }

}
