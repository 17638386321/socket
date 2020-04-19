package com.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9999);
        Socket socket = serverSocket.accept();
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        System.out.println(dis.readUTF());
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        dos.writeUTF("你好客户机"+dis.readUTF());

    }
}
