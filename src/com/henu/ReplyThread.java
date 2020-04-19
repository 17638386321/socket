package com.henu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Calendar;

public class ReplyThread implements Runnable {
    private Socket socket;

    public ReplyThread(Socket socket){
        this.socket = socket;
    }
    @Override
    public void run() {
        try {
            System.out.println("New Connection accepted（客户机）, " + socket.getInetAddress() + ": " + socket.getPort());
            BufferedReader br = getReader(socket);
            PrintWriter pw = getWriter(socket);

            String msg = null;
            while( (msg=br.readLine())!=null){
                String s = reply(msg);
                System.out.println(s);
                //服务器的线程向客户端发送响应报文（写数据）
                pw.println(s);

                if(msg.equals("bye"))
                    break;
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try {
                if(socket!=null)
                    socket.close();
            }catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    public PrintWriter getWriter(Socket socket) throws IOException {
        return new PrintWriter(socket.getOutputStream(),true);
    }

    public BufferedReader getReader(Socket socket) throws IOException{
        return new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public String reply(String msg){
        if("天气".equals(msg)){
            return "开封今日天气： 多云10-20度";
        }
        else if("出行".equals(msg)){
            //获取系统日期的星期几？
            String info = null;
            int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
            System.out.println("day = " + day); //6
            switch (day){
                case Calendar.MONDAY:
                    info =  "今天周一，车牌尾号限行1和6";
                    break;
                case Calendar.TUESDAY:
                    info = "今天周二，车牌尾号限行2和7";
                    break;
                case Calendar.WEDNESDAY:
                    info =  "今天周三，车牌尾号限行3和8";
                    break;
                case Calendar.THURSDAY:
                    info =  "今天周四，车牌尾号限行4和9";
                    break;
                case Calendar.FRIDAY:
                    info =  "今天周五，车牌尾号限行2和7";
                    break;
                case Calendar.SATURDAY:

                case Calendar.SUNDAY:
                    info =  "今天周末，不限行";
                    break;
                default:
                    info =  "不限行";

            }
            return info;

        }
        else if("bye".equals(msg))
            return "程序即将退出……";
        else
            return "未知信息";
    }

}
