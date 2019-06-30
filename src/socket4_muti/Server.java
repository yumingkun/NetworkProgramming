package socket4_muti;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 多客户端监听
 */
public class Server {

    public static void main(String[] args) {
        int port = 8848;
        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("服务器已开启");
            while (true) { //监听多个客户端的连接
                socket = serverSocket.accept();
                System.out.println("客户端信息：" + socket);
                //每一个客户端 开启自己的新线程去数据交互
                new SocketThread(socket).start();

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (Exception e2) {

            }
        }

    }

    //每一个用户线程 用于循环监听客户端输入并返回处理结果
    static class SocketThread extends Thread {
        BufferedReader br = null;
        PrintWriter pw = null;
        Socket socket;

        public SocketThread(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8")));

                while (true) {//循环监听
                    String str = br.readLine();
                    System.out.println("客户端：" + str);

                    String rtn = "";
                    int num = Integer.parseInt(str);
                    if (test(num)) {
                        rtn =  num + "是偶数";

                    } else {
                        rtn =  num + "是奇数";
                    }
                    pw.println(rtn);
                    pw.flush();
                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                if (pw != null) {
                    pw.close();
                }
                if (br != null) {
                    pw.close();
                }
                if (socket != null) {
                    pw.close();
                }
            }

        }

    }

    private static boolean test(int num) {
        if (num % 2 == 0) {
            return true;
        } else {
            return false;
        }

    }

}
