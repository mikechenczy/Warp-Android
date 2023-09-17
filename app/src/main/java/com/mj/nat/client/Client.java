package com.mj.nat.client;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author Mike_Chen
 * @date 2023/9/8
 * @apiNote
 */
public class Client {
    public static String url = "ws://localhost:11451/ws";
    public static int serverPort;
    public static String host;
    public static void main(String[] args) {
        if(args!=null) {
            if(args.length>=1)
                url = args[0];
            try {
                if(args.length>=2)
                    serverPort = Integer.parseInt(args[1]);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
            if(args.length>=3)
                host = args[2];
        }
        System.out.println(url);
        System.out.println(serverPort);
        System.out.println(host);
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ChannelFuture channelFuture = null;
        try {

            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new ClientHandler());
                        }
                    });
            channelFuture = b.bind(serverPort).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (channelFuture != null) {
                channelFuture.channel().close().syncUninterruptibly();
            }
            if (!bossGroup.isShutdown()) {
                bossGroup.shutdownGracefully();
            }
            if (!workerGroup.isShutdown()) {
                workerGroup.shutdownGracefully();
            }
        }
    }
}