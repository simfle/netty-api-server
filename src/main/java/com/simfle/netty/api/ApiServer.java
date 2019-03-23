package com.simfle.netty.api;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

@Component
public class ApiServer {

    @Autowired
    @Qualifier("tcpSocketAddress")
    private InetSocketAddress address;

    @Autowired
    @Qualifier("workerThreadCount")
    private int workerThreadCount;

    @Autowired
    @Qualifier("bossThreadCount")
    private int bossThreadCount;

    public void start() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(bossThreadCount);
        EventLoopGroup workerGroup = new NioEventLoopGroup(workerThreadCount);
        ChannelFuture channelFuture = null;

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO)).childHandler(new ApiServerInitialzier(null));

            Channel ch = b.bind(address).sync().channel();

            channelFuture = ch.closeFuture();
            channelFuture.sync();
        } catch (InterruptedException e){
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
