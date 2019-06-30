package socket5_cmd;

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
 * 客户端：
 * op1|2
 * 服务器回复：2是偶数
 * op2|2
 * 服务器回复：2是质数
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


		int port=8848;
		Socket socket=null;
		BufferedReader br=null;
		PrintWriter pw=null;
		BufferedReader brkey=null;

		try {
		    
		   socket =new Socket(ip,port);
		   System.out.println("客户端：");
		   
		   br=new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
		   pw=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8")));
		   
		   brkey=new BufferedReader(new InputStreamReader(System.in));
		   
		   while (true) {
			   String send=brkey.readLine();
			   //命令格式： close    op1|3   op1|4    op2|3  op2|4
			   if ("close".equals(send)) {
				   System.out.println("客户端断开");
				   pw.println("close");
				   pw.flush();
				   break;
			   }

			   //发送
			   pw.println(send);
			   pw.flush();
			   
			   //接收服务器结果
			   String str=br.readLine();
			   System.out.println(str);
		   }
		   
		
		   
		   
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				if (pw!=null) {
					pw.close();
				}
				if (br!=null) {
					pw.close();
				}
				if (socket!=null) {
					pw.close();
				}
				 
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}

}
