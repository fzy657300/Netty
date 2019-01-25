package com.fzy.netty.entty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

import java.net.InetSocketAddress;

/**
 * 继承类ChannelHandlerAdapter
 * 覆盖方法1：channelRead 主要功能：处理接收的数据（转码，输出，入库.......）
 * 覆盖方法2：exceptionCaught 主要功能：出现异常如何处理
 * 覆盖方法3：channelActive 主要功能：当有客户端连接上时，执行的方法。
 * 此类须加上@Sharable注解 表示该对象可以被channel共享
 * 意思就是多个设备连接，使用这一个数据处理对象，如果注解，一个程序只能连接一个设备，且不能断开
 */
@ChannelHandler.Sharable
public class DiscardServerHandler extends ChannelHandlerAdapter {


	// 自定义数据处理
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		try {
			// 本次数据来源IP
			InetSocketAddress insoket = (InetSocketAddress) ctx.channel().remoteAddress();
			String IP = insoket.getAddress().getHostAddress();
			ByteBuf in = (ByteBuf) msg;
			// 接收的数据
			String message = in.toString(CharsetUtil.UTF_8);
			//梳理数据
			System.out.println("收到IP为"+IP+"的数据:" + message);
			// 向客户端响应(可以不返回)
			ByteBuf resp = Unpooled.copiedBuffer("success".getBytes());
			ctx.writeAndFlush(resp);
		} finally {
			ReferenceCountUtil.release(msg);
		}
	}

	// 发生异常时如何处理
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		InetSocketAddress insoket = (InetSocketAddress) ctx.channel().remoteAddress();
		String IP = insoket.getAddress().getHostAddress();
		System.out.println(IP);
		cause.printStackTrace();
		ctx.close();
	}

	// 设备连接时
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		InetSocketAddress insoket = (InetSocketAddress) ctx.channel().remoteAddress();
		String port = insoket.getAddress().getHostAddress();
		// 存储ctx，发送信息时使用
		System.out.println("ip为 " + port + "的客户端已连接" + "--" + insoket.getAddress().getCanonicalHostName() + "---"
				+ insoket.getAddress().getAddress());
	}
}
