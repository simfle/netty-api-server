package com.simfle.netty.api;

import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class ApiRequestParser extends SimpleChannelInboundHandler<FullHttpMessage> {

    private static final Logger logger = LogManager.getLogger(ApiRequestParser.class);

    private HttpRequest request;
    private JsonObject apiResult;
    private HttpPostRequestDecoder decoder;

    private static final HttpDataFactory factory = new DefaultHttpDataFactory(DefaultHttpDataFactory.MINSIZE);

    private Map<String, String> reqData = new HashMap<String, String>();

    private static final Set<String> usingHeader = new HashSet<String>();

    static {
        usingHeader.add("token");
        usingHeader.add("email");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpMessage msg) {
        if (msg instanceof HttpRequest) {
            this.request = (HttpRequest) msg;

            if (HttpHeaders.is100ContinueExpected(request)) {
                //send100Continue(ctx);
            }

            HttpHeaders headers = request.headers();

            if (!headers.isEmpty()) {
                for (Map.Entry<String, String> h : headers) {
                    String key = h.getKey();
                    if (usingHeader.contains(key)) {
                        reqData.put(key, h.getValue());
                    }
                }
            }

            reqData.put("REQUEST_URI", request.getUri());
            reqData.put("REQUEST_METHOD", request.getMethod().name());
        }
    }

    private void readPostData() {

    }
}
