package socket1_base;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        int port = 8848;

        ServerSocket serverSocket = null;
        Socket socket = null;
        BufferedReader br = null;
        PrintWriter pw = null;

        try {
            serverSocket = new ServerSocket(port);//创建服务，指定端口号
            System.out.println("服务器启动");
            socket = serverSocket.accept();//监听客户端链接，客户端链接后，实例化socket

            //实例化输入输出
            br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8")));

            String str = br.readLine();
            System.out.println("客户端信息：" + str);

            String rtn = "服务器返回的信息" + str;

            pw.println(rtn);
            pw.flush();

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
                if (serverSocket != null) {
                    pw.close();
                }
            } catch (Exception e2) {

            }
        }

    }

}
