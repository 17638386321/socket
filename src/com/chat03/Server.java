package com.chat03;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private List<MyChannel> all = new ArrayList<MyChannel>();

    public static void main(String[] args) throws IOException {
        new Server().start();
    }
    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(9999);
        while (true){
            Socket socket = serverSocket.accept();
            MyChannel myChannel = new MyChannel(socket);
            all.add(myChannel);
            new Thread(myChannel).start();
        }
    }
    //一个客户端一条道路
    //输入流(接收数据)输出流(发送数据)
    class MyChannel implements Runnable{
        private DataInputStream dis;
        private DataOutputStream dos;
        private Boolean isRunning = true;
        public MyChannel(Socket client) {
            try {
                dis = new DataInputStream(client.getInputStream());
                dos = new DataOutputStream(client.getOutputStream());
            } catch (IOException e) {
                isRunning = false;
                CloseUtil.closeAll(dis,dos);
            }
        }
        private String receive(){
            String msg = "";
            try {
                msg = dis.readUTF();
            } catch (IOException e) {
                isRunning = false;
                    all.remove(this);
                CloseUtil.closeAll(dis);
            }
            return msg;
        }
        private void send(String msg){
            if (msg == null||msg.equals("")){
                try {
                    dos.writeUTF(msg);
                } catch (IOException e) {
                    isRunning = false;
                    CloseUtil.closeAll(dos);
                }
            }
        }
        public void sendOther(){
            String msg = receive();
            for (MyChannel other:all) {
                if (other==this){
                    continue;
                }
                other.send(msg);
            }
        }
        @Override
        public void run() {
            while (isRunning){
                sendOther();
            }
        }
    }
}
