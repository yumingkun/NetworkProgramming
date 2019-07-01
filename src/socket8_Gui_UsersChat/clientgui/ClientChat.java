package socket8_Gui_UsersChat.clientgui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import javax.xml.bind.DatatypeConverter;

public class ClientChat extends Thread {

	Socket socket = null;
	BufferedReader br = null;
	PrintWriter pw = null;

	public ClientChat(Socket socket, String name) {
		super(name);
		this.socket = socket;
	}

	public void run() {
		System.out.println("客户端");
		try {
			br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8")));

			String send = "LOGIN|" + this.getName(); // LOGIN|userName
			this.sendMessage(send);

			String str;
			while ((str = br.readLine()) != null) {
				// textSu.setText(str);
				String[] commands = str.split("\\|");
				if (commands[0].equals("USERLISTS")) { // USERLISTS|user1_user2_user3
					String[] sUsers = commands[1].split("\\_"); // 在线用户名称的字符串数组
					ClientMG.getClientMG().addItems(sUsers);

				} else if (commands[0].equals("ADD")) { // ADD||username
					String User = commands[1];
					ClientMG.getClientMG().addItem(User);
				} else if (commands[0].equals("DEL")) { // DEL|UserName
					// 处理下线用户信息：DEL|username --(已在线的其他客户端)
					String sOfflineUser = commands[1];

					// 删除用户列表中的username
					ClientMG.getClientMG().removeItem(sOfflineUser);

					ClientMG.getClientMG().setLogTxt(sOfflineUser + "下线了。");
				} else if (commands[0].equals("MSG")) { // 格式：MSG|SenderName|MSGInfo
					String SenderName = commands[1];
					String strMSG = commands[2];
					// base6
					byte[] bmsg = DatatypeConverter.parseBase64Binary(strMSG);
					String MSGinfo = new String(bmsg, "UTF-8");

					// 将消息内容显示到聊天记录中
					ClientMG.getClientMG().setLogTxt("[" + SenderName + "]:\r\n" + MSGinfo);
				} else if (commands[0].equals("CLOSE")) { // CLOSE
					// 1、处理CLOSE命令：界面显示服务器关闭
					ClientMG.getClientMG().setLogTxt("服务器关闭");
					// 2、清空在线用户列表
					ClientMG.getClientMG().removeItems();
					// //3、关闭ClientChat
					break;
				}

			}

		} catch (Exception e) {
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
				// TODO: handle exception
			}
		}
		// textSu.setText("客户端已退出服务器");

	}

	public void sendMessage(String send) {
		pw.println(send);
		pw.flush();

	}

}
