package socket9_UDP_Base;

import java.net.DatagramPacket;
import java.net.DatagramSocket;


/**
 * 服务器端
 */
public class Receive {
    public static void main(String[] args) {

        DatagramSocket dsRec = null;
        try {

            //接收消息
            dsRec = new DatagramSocket(8899);//初始化DatagramSocket
            byte[] bsRec = new byte[1024];//数据报大小
            DatagramPacket dpRec = new DatagramPacket(bsRec, bsRec.length);//实例化一个数据报对象
            dsRec.receive(dpRec);//监听数据，阻塞命令

            String strRec = new String(bsRec, 0, dpRec.getLength(), "utf-8");
            System.out.println("收到：" + strRec);

            //处理数据


            //发送消息
            String strRtn = "utp回复：" + strRec;
            byte[] bsSend = strRtn.getBytes("utf-8");
                                                        //发送内容字节数组 目标地址 目标端口
            DatagramPacket dpSend = new DatagramPacket(bsSend, bsSend.length, dpRec.getAddress(), dpRec.getPort());
            dsRec.send(dpSend);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
