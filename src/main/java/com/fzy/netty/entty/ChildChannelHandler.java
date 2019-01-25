package com.fzy.netty.entty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.springframework.beans.factory.annotation.Autowired;

/*
* 继承抽象类ChannelInitializer
*   实现方法initChannel
*       将自定义的处理数据的类，交给socketChannel
* */
public class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

    @Autowired
    private DiscardServerHandler discardServerHandler;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast(discardServerHandler);
    }
}
