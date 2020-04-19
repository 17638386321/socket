package com.gushan;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Client1 {
    //private String localhost = "localhost:";
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost",8888);
        /*BufferedReader reader = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
        );
        String echo = reader.readLine();//阻塞式方法，这个是读一行那边没有行结束符*/
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        String echo = dis.readUTF();
        System.out.println(echo);
    }
}
