package com.gushan;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server1 {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8888);
        Socket socket = serverSocket.accept();
        System.out.println("一个客户机建立连接");
        //发送数据
        String msg = "欢迎使用";
        /*BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream())
                );
        writer.write(msg);
        writer.newLine();
        writer.flush();*/
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        dos.writeUTF(msg);
        dos.flush();
    }
}
