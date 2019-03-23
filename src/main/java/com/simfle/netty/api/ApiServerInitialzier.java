package com.simfle.netty.api;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslContext;

public class ApiServerInitialzier extends ChannelInitializer<SocketChannel> {
    private final SslContext sslCtx;

    public ApiServerInitialzier(SslContext sslCtx) {
        this.sslCtx = sslCtx;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

    }
}
