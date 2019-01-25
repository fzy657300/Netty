package com.fzy.netty.config;

import com.fzy.netty.ApplicationRUN;
import com.fzy.netty.entty.ChildChannelHandler;
import com.fzy.netty.entty.DiscardServerHandler;
import com.fzy.netty.entty.EnttyService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class IotSocakeConfig {

    //自定义数据处理类
    @Bean
    public DiscardServerHandler getDiscarServerHandler(){
        return new DiscardServerHandler();
    }

    //传递自定义数据处理类的类
    @Bean
    public ChildChannelHandler getChildChannelHandler(){
        return new ChildChannelHandler();
    }

    //Entty服务
    @Bean
    public EnttyService getEntty(){
        return new EnttyService();
    }

    //
    @Bean(name = "bossGroup")
    public EventLoopGroup gitBossGroup(){
        return new NioEventLoopGroup();
    }

    //
    @Bean(name = "workerGroup")
    public EventLoopGroup gitworkerGroup(){
        return new NioEventLoopGroup();
    }

    @Bean
    public ServerBootstrap girServerBootstrap(){
        return new ServerBootstrap();
    }


    //用于启动接收TCP数据类
    @Bean
    public ApplicationRUN getApplicationRUN() {
        return new ApplicationRUN();
    }



}
