package com.chat;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket client = new Socket("localhost",9999);
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

        DataOutputStream dos = new DataOutputStream(client.getOutputStream());
        DataInputStream dis = new DataInputStream(client.getInputStream());
        String info = bf.readLine();
        dos.writeUTF(info);

        String echo = dis.readUTF();
        System.out.println(echo);
    }
}
