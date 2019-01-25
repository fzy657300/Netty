package com.fzy.netty.entty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;

//Entty提供服务的类
public class EnttyService {

    @Autowired
    private ChildChannelHandler childChannelHandler;

    @Resource(name = "bossGroup")
    private EventLoopGroup bossGroup;

    @Resource(name = "workerGroup")
    private EventLoopGroup workerGroup;

    @Autowired
    private ServerBootstrap serverBootstrap;

    @Value("${tcpport}")
    private Integer tcp_port;



    @SuppressWarnings("static-access")
	public void run() throws Exception {


        System.out.println("准备运行端口：" + tcp_port);
        try {
            serverBootstrap = serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.ALLOCATOR.SO_BACKLOG, 128)
                    //指定自定义数据处理类
                    .childHandler(childChannelHandler);
            //绑定端口，同步等待成功
            ChannelFuture f = serverBootstrap.bind(tcp_port).sync();
            //等待服务监听端口关闭
            f.channel().closeFuture().sync();
        } finally {
            //退出，释放线程资源
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
