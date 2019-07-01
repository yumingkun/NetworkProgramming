package socket8_Gui_UsersChat.servergui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import javax.xml.bind.DatatypeConverter;

public class ServerChat extends Thread {

	BufferedReader br = null;
	PrintWriter pw = null;
	Socket socket = null;

	public ServerChat(Socket socket) {

		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8")));

			String str = "";
			while ((str = br.readLine()) != null) {
				// ServerMG.getServerMG().setLogTest("客户端信息："+str);
				String[] commands = str.split("\\|");
				if (commands[0].equals("LOGIN")) { // 解析登录请求,格式：LOGIN|UserName
					String sUser = commands[1];
					this.setName(sUser);
					// 1、得到所有在线用户信息名称，发回给客户端：USERLISTS|user1_user2_user3
					ServerMG.getServerMG().getOnlineNames(this);
					// 2、将当前登录用户的信息（用户名），发送给已经在线的其他用户：ADD|userName
					ServerMG.getServerMG().sendNewUserToAll(this);
					// 3、将当前登录的Socket信息放入Arraylist中
					ServerMG.getServerMG().addList(this);

				} else if (commands[0].equals("OFFLINE")) {

					// 1、处理OFFLINE,向所有的其他在线用户发送用户下线消息：DEL|username
					String SenderName = commands[1];
					String strSend = "DEL|" + SenderName;
					ServerChat serverChat = ServerMG.getServerMG().getServerChatByName(SenderName);
					ServerMG.getServerMG().sendOffLinetoAll(this); // 补充完整
					ServerMG.getServerMG().setLogTest(SenderName + "下线了");

					// 2、清除ArrayList中的当前用户信息（ServerChat）
					ServerMG.getServerMG().removeListByName(this);

					// 3、退出当前的Socket线程
					break;

				} else if (commands[0].equals("MSG")) { // 服务器转发消息
					// "MSG|"+SenderName+"|"+RecName+"|"+MSGinfo;
					String SenderName = commands[1];
					String RecName = commands[2];
					String MSGinfo = commands[3];

					if (RecName.equals("ALL")) {// 群发
						// 群聊

						// byte[]
						// bmsg=DatatypeConverter.parseBase64Binary(MSGinfo);
						// String strMsg=new String(bmsg, "UTF-8");

						String strSend = "MSG|" + SenderName + "|" + MSGinfo; // 格式：MSG|SenderName|MSGInfo
						System.out.println(MSGinfo + "-----------------");

						ServerMG.getServerMG().sendMsgtoAll(strSend, this); // 补充完整

						ServerMG.getServerMG().setLogTest(SenderName + "发送消息[" + MSGinfo + "]到所有人。");

					} else {// 单发
							// 根据名字查询serverChat
						ServerChat serverChat = ServerMG.getServerMG().getServerChatByName(RecName);// 格式：MSG|SenderName|MSGInfo
						if (serverChat != null) {
							String strSend = "MSG|" + SenderName + "|" + MSGinfo;
							serverChat.sendMessage(strSend);
							ServerMG.getServerMG().setLogTest("[" + SenderName + "]发送消息:" + MSGinfo + "到" + RecName);
						}
					}

				}

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeServerChat();
		}

	}

	/**
	 * 服务端发送消息
	 * 
	 * @param send
	 */
	public void sendMessage(String send) {
		pw.println(send);
		pw.flush();

	}

	public void closeServerChat() {
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
