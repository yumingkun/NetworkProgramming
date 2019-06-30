package socket5_cmd;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 接收客户端命令并正确处理
 *
 * 客户端发来的请求op1|2
 * op1：判断奇偶数
 * 客户端发来的请求op2|2
 * op2：判断是否为质数
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
                System.out.println("客户端信息:" + socket);
                //每一个客户端 开启自己的新线程去数据交互
                new SocketThread(socket).start();

            }


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (Exception e2) {
                // TODO: handle exception
            }
        }

    }

    //每一个用户线程 用于循环监听客户端输入并返回处理结果
    static class SocketThread extends Thread {
        BufferedReader br = null;
        PrintWriter pw = null;
        Socket socket = null;

        public SocketThread(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8")));

                while (true) { //监听客户端发送的命令

                    //读取客户端的命令 格式： close    op1|3   op1|4    op2|3  op2|4
                    String str = br.readLine();
                    System.out.println("客户端发来的请求" + str);
                    String[] commands = str.split("\\|");

                    String rtn = "";
                    if ("close".equals(commands[0])) {
                        System.out.println("服务器断开");
                        break;
                    } else if ("op1".equals(commands[0])) {
                        System.out.println("op1：判断奇偶数");
                        int num = Integer.parseInt(commands[1]);
                        if (test1(num)) {
                            rtn = "服务器回复：" + num + "是偶数";

                        } else {
                            rtn = "服务器回复：" + num + "是奇数";
                        }

                    } else if ("op2".equals(commands[0])) {
                        System.out.println("op2：判断是否为质数");
                        int num = Integer.parseInt(commands[1]);
                        if (test2(num)) {
                            rtn = "服务器回复：" + num + "是质数";

                        } else {
                            rtn = "服务器回复：" + num + "不是质数";
                        }
                    }

                    //返回处理结果
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

    /**
     * 判断奇偶数
     * @param num
     * @return
     */
    private static boolean test1(int num) {
        if (num % 2 == 0) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * 判断质数
     * @param num
     * @return
     */
    private static boolean test2(int num) {

        for (int i = 2; i <= (num / 2); i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;

    }

}
