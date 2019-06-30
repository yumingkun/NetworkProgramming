package socket3_isEven;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 循环监听客户端请求 并判断奇偶数
 */
public class Server {

    public static void main(String[] args) {
        int port = 8888;

        ServerSocket serverSocket = null;
        Socket socket = null;
        BufferedReader br = null;
        PrintWriter pw = null;

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("服务端已开启：");
            socket = serverSocket.accept();

            br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8")));

            while (true) {
                String str = br.readLine();
                System.out.println("客户端发来的请求" + str);

                String rtn = "";
                int num = Integer.parseInt(str);
                if (test(num)) {
                    rtn = "服务器回复：" + num + "是偶数";

                } else {
                    rtn = "服务器回复：" + num + "是奇数";
                }
                pw.println(rtn);
                pw.flush();
            }


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
                // TODO: handle exception
            }
        }

    }

    /**
     * 判断奇偶
     * @param num
     * @return
     */
    private static boolean test(int num) {
        if (num % 2 == 0) {
            return true;
        } else {
            return false;
        }

    }

}
