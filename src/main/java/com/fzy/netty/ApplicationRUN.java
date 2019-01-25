package com.fzy.netty;

import com.fzy.netty.entty.EnttyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;

//该实现类用于在springBoot启动后 执行一些特定的方法
//Oreder值为启动顺序 值越大 越早启动
@Order(value = 1)
public class ApplicationRUN implements ApplicationRunner {


    @Autowired
    private EnttyService enttyService;


    public void run(ApplicationArguments args) throws Exception {
        enttyService.run();
    }

}
