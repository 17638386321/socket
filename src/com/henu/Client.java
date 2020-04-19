package com.henu;
import java.io.*;
import java.net.Socket;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Auther: 屈志豪
 * @Date: 2020/4/3 16:54
 * @Description:
 */
public class Client implements ActionListener{
    private String host = "localhost";
    private int port = 19900;
    private Socket socket;
    PrintWriter printWriter =null;
    String question =null;

    JFrame jFrame = null;
    JPanel jPanel = null;
    JTextArea jTextArea = null;
    JTextField jTextField = null;
    JButton jButton = null;
    JScrollPane jScrollPane = null;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String str = null;
    public static void main(String[] args) throws Exception {
        Client client = new Client();
        try{
            client.ask();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * 构造方法
     */
    public Client() throws Exception{
        jFrame = new JFrame("问答机器人-客户端");
        jPanel = new JPanel();
        jTextArea = new JTextArea(16,45);
        jTextField = new JTextField(40);
        jButton = new JButton("发送");
        jScrollPane = new JScrollPane(jTextArea);

        //设置竖直滚动条总是显示
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        Dimension dimension = jTextArea.getPreferredSize();
        jScrollPane.setBounds(600, 250,dimension.width,dimension.width);

        //向窗口添加组件
        jPanel.add(jScrollPane);
        jPanel.add(jTextField);
        jPanel.add(jButton);
        jFrame.add(jPanel);

        //设置窗口的位置和大小
        jFrame.setBounds(400, 200,500,350);
        //设置窗口关闭时结束程序
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置窗口可见
        jFrame.setVisible(true);

        //为按钮添加监听器
        jButton.addActionListener(this);

        //构造Socket对象
        try  {
            socket = new Socket(host, port);
            str = df.format(new Date());
            jTextArea.append("与服务器建立连接......"+"    "+str+"\n");
            System.out.println("与服务器建立连接......");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //创建监听器
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==jButton){
            question = jTextField.getText();
            str = df.format(new Date());
            jTextArea.append("客户端提问:" + question+"    "+str+"\n");
            printWriter.println(question);
            jTextField.setText(null);
        }
    }

    public PrintWriter getWriter(Socket socket) throws IOException{
        return new PrintWriter(socket.getOutputStream(),true);
    }

    public BufferedReader getReader(Socket socket) throws IOException{
        return new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    //客户机请求
    public void ask() throws IOException, InterruptedException {
        BufferedReader local = new BufferedReader(new InputStreamReader(System.in));
        // String question = scan.next();
        //创建Socket的输入流、输出流
        BufferedReader reader = getReader(socket);
        printWriter = getWriter(socket);

        while( true ){
            //从服务器接收请求
            try{
                String answer  = reader.readLine();
                str = df.format(new Date());
                jTextArea.append("来自服务器的回复:" + answer+"    "+str+"\n");
            }catch (IOException e){
                e.printStackTrace();
            }
            //writer.flush();
            if("bye".equals(question)){
                Thread.sleep(2000);
                System.exit(0);
                break;
            }
        }

        if(!socket.isClosed() && socket.isConnected())
        {
            try{
                socket.close();
            }catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

}

