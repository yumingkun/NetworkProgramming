package socket2_input;

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

        int port = 8849;
        ServerSocket serverSocket = null;
        Socket socket = null;
        BufferedReader br = null;
        PrintWriter pw = null;

        try {
            serverSocket = new ServerSocket(port);//创建服务，指定端口号
            System.out.println("服务器启动");
            socket = serverSocket.accept();//监听客户端链接，客户端链接后，实例化socket

            //实例化pw br
            br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8")));


            //接收客户端信息
            String str = br.readLine();
            System.out.println("客户端信息：" + str);


            //判断客户端输入是否为质数
            int num = Integer.parseInt(str);
            String is = "是质数";
            for (int i = 2; i <= (num / 2); i++) {
                if (num % i == 0) {
                    is = "不是质数";
                    break;
                }
            }


            //返回给客户端结果
            String rtn = str + is;
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
                    br.close();
                }
                if (socket != null) {
                    socket.close();
                }
                if (serverSocket != null) {
                    serverSocket.close();
                }

            } catch (Exception e2) {

            }
        }

    }

}
