package socket4_muti;

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

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("服务器已开启");
            while (true) { //监听多个客户端连接
                socket = serverSocket.accept();
                System.out.println("客户端连接信息：" + socket);
                //开启新线程，用户与数据的交互
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

    //建立新的线程，用于循环监听，执行客户端的数据交互
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

                while (true) {
                    String str = br.readLine();
                    System.out.println("�ͻ��˷���������" + str);

                    String rtn = "";
                    int num = Integer.parseInt(str);
                    if (test(num)) {
                        rtn = "�������ظ���" + num + "��ż��";

                    } else {
                        rtn = "�������ظ���" + num + "������";
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
