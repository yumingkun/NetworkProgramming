package socket2_input;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

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


        int port = 8849;
        Socket socket = null;
        BufferedReader br = null;
        PrintWriter pw = null;
        BufferedReader brkey = null;//

        try {

            socket = new Socket(ip, port);
            System.out.println("已经连接到服务器");

            br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8")));

            //从键盘获取数据
            brkey = new BufferedReader(new InputStreamReader(System.in));
            String send = brkey.readLine();

            //发送从键盘获得的数据
            pw.println(send);
            pw.flush();


            //获取服务器返回的信息
            String str = br.readLine();
            System.out.println(str);


        } catch (IOException e) {
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

            }
        }
    }

}
