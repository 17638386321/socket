package com.chat02;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

//发送数据
public class Send implements Runnable{
    private BufferedReader bf;
    private DataOutputStream dos;
    private Boolean isRunning = true;
    public Send(){
        bf = new BufferedReader(new InputStreamReader(System.in));
    }
    public Send(Socket client) {
        this();
        try {
            dos = new DataOutputStream(client.getOutputStream());
        } catch (IOException e) {
            isRunning = false;
            CloseUtil.closeAll(dos,bf);
        }
    }
    private String getMessage(){
        try {
            return bf.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
    public void send(){
        String msg = getMessage();
        if (msg!=null && msg!=""){
            try {
                dos.writeUTF(msg);
                dos.flush();//强制刷新
            } catch (IOException e) {
                isRunning = false;
                CloseUtil.closeAll(dos,bf);
            }
        }
    }
    public void run() {
        while (isRunning){
            send();
        }
    }
}
