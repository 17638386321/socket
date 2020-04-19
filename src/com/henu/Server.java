package com.henu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private int port = 19900;
    private ServerSocket serverSocket;
    //线程池的默认大小为5
    private final static int POOL_SIZE = 5;
    //接口
    private ExecutorService executorService;

    public static void main(String[] args) {
        Server server = new Server();
        server.service();
    }

    /**
     * 构造方法，创建ServerSocket对象
     */
    public Server(){
        try {
            serverSocket = new ServerSocket(port,5);

            System.out.println("Cpu内核数：" + Runtime.getRuntime().availableProcessors());

            //创建线程池，返回一个固定线程池大小的线程池
            executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*POOL_SIZE);
            System.out.println("服务器已启动……");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * 服务器处理客户请求的方法
     */
    public void service(){
        while (true){
            Socket socket = null;

            try{
                socket = serverSocket.accept();
                /*
                Thread thread = new Thread(new ReplyThread(socket));
                thread.start();
                */
                //切换线程，从线程池里边取出一个空闲线程
                executorService.execute(new ReplyThread(socket));

            }catch (IOException e){
                e.printStackTrace();
            }

        }
    }
}
