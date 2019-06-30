package socket3_isEven;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 循环输入数字判断奇偶书
 */
public class Client {

    public static void main(String[] args) {
//		String ip="192.168.1.89";
        //获取当前主机IP地址
        InetAddress ipAddress = null;
        String ip = "";
        try {
            ipAddress = InetAddress.getLocalHost();
            ip = ipAddress.getHostAddress();
            System.out.println(ip);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }


        int port = 8888;
        Socket socket = null;
        BufferedReader br = null;
        PrintWriter pw = null;
        BufferedReader brkey = null;

        try {

            socket = new Socket(ip, port);
            System.out.println("客户端已连接到服务器");

            br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8")));

            brkey = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                //读取键盘输入，并发送给服务器
                String send = brkey.readLine();
                if ("end".equals(send)) {
                    System.out.println("客户端断开");
                    break;
                }
                pw.println(send);
                pw.flush();


                //读取服务器返回信息
                String str = br.readLine();
                System.out.println(str);
            }


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (pw != null) {
                    pw.close();
                }
                if (br != null) {
                    pw.close();
                }
                if (socket != null) {
                    pw.close();
                }

            } catch (Exception e2) {
                // TODO: handle exception
            }
        }
    }

}
