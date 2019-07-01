package socket9_UDP_Base;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Sender {

	public static void main(String[] args) {

		DatagramSocket dsRec=null;
		try {


			//接收消息
			dsRec=new DatagramSocket();//初始化DatagramSocket



			//发送消息
			String strSend="utp发送消息测试";
			byte[] bsSend=strSend.getBytes("UTF-8");

			InetAddress ipSend=InetAddress.getByName("192.168.1.89");
			int port=8899;


			DatagramPacket dpSend=new DatagramPacket(bsSend, bsSend.length, ipSend, port);
			dsRec.send(dpSend);


			byte [] bsRec=new byte[1024];//数据报大小
			DatagramPacket dpRec=new DatagramPacket(bsRec, bsRec.length);
			dsRec.receive(dpRec);//监听数据

			String strRec=new String(bsRec,0,dpRec.getLength(),"UTF-8");
			System.out.println("收到回复："+strRec);





		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
