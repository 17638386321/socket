package com.chat03;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class Receive implements Runnable {
    private DataInputStream dis;
    private Boolean isRunning = true;
    public Receive(){}
    public Receive(Socket client){
        try {
            System.out.println(12);
            dis = new DataInputStream(client.getInputStream());
        } catch (IOException e) {
            isRunning = false;
            CloseUtil.closeAll(dis);
        }
    }
    public String receive(){
        try {
            String msg = dis.readUTF();
            return msg;
        } catch (IOException e) {
            isRunning = false;
            CloseUtil.closeAll(dis);
        }
        return "";
    }
    @Override
    public void run() {
        while (isRunning){
            System.out.println("我是客户端，我接收数据："+receive());
        }
    }
}
