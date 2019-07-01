package socket8_Gui_UsersChat.servergui;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class ServerMG {
	private static final ServerMG serverMG = new ServerMG();

	private ServerMG() {
	};

	public static ServerMG getServerMG() {
		return serverMG;
	}

	private ServerForm serverForm;

	public void setServerForm(ServerForm serverForm) {
		this.serverForm = serverForm;
	}

	private ServerSocket server;

	public boolean createServer(int port) {

		try {
			server = new ServerSocket(port);
			new ServerLisenter(server).start();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			return false;
		}

	}

	/**
	 * 关闭服务器
	 */
	public void closeServer() {

		if (server != null) {
			try {
				// 关闭服务器，同时关闭所有连接到服务器Socket
				// 1、向所有在线用户发送关闭服务器的信息：CLOSE
				this.sendCloseToAll();

				// 2、遍历Arraylist将其中的每一个ServerChat关闭
				CloseAllServerChat();

				// 3、ArrayList要清空
				clearList();

				// 4、关闭ServerListener
				// 5、关闭ServerSocket

				if (server != null){
					server.close(); 
				}
					
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 设置服务器聊天记录
	 * 
	 * @param string
	 */
	public void setLogTest(String string) {
		serverForm.textLog.append(string + "\r\n");
	}

	// 在线用户操作
	ArrayList<ServerChat> alOnlineList = new ArrayList<>();

	// 移除用户
	public void removeListByName(ServerChat sc) {
		for (int i = 0; i < alOnlineList.size(); i++) {
			ServerChat s = alOnlineList.get(i);
			if (!s.equals(sc)) {
				alOnlineList.remove(sc);
			}
		}
	}

	// remove all user list
	public void clearList() {
		alOnlineList.clear();

	}

	public synchronized void addList(ServerChat sc) {
		// 限制重名
		alOnlineList.add(sc);
	}

	// 通知所有客户端，下线
	public void sendOffLinetoAll(ServerChat sc) {
		for (int i = 0; i < alOnlineList.size(); i++) {
			ServerChat s = alOnlineList.get(i);
			s.sendMessage("DEL|" + sc.getName());
		}
	}

	public void getOnlineNames(ServerChat sc) {
		if (alOnlineList.size() > 0) { // 非第1次登录时，得到所有的在线用户
			String sUsers = "";
			for (int i = 0; i < alOnlineList.size(); i++) {
				ServerChat s = alOnlineList.get(i);
				sUsers += s.getName() + "_";
			}
			sc.sendMessage("USERLISTS|" + sUsers);
		}
	}

	public void sendNewUserToAll(ServerChat sc) {
		for (int i = 0; i < alOnlineList.size(); i++) {
			ServerChat s = alOnlineList.get(i);
			s.sendMessage("ADD|" + sc.getName());
		}
	}

	public ServerChat getServerChatByName(String name) {
		for (int i = 0; i < alOnlineList.size(); i++) {
			ServerChat s = alOnlineList.get(i);
			if (s.getName().equals(name)) {
				return s;
			}
		}

		return null;

	}

	public void sendMsgtoAll(String sMsg, ServerChat sc) {
		// 发送给所有人，但是要排除自身
		for (int i = 0; i < alOnlineList.size(); i++) {
			ServerChat s = alOnlineList.get(i);
			if (!s.equals(sc)) {
				s.sendMessage(sMsg);
			}
		}

	}

	public void sendCloseToAll() {
		for (int i = 0; i < alOnlineList.size(); i++) {
			ServerChat s = alOnlineList.get(i);
			s.sendMessage("CLOSE");
		}
	}

	public void CloseAllServerChat() {
		for (int i = 0; i < alOnlineList.size(); i++) {
			ServerChat s = alOnlineList.get(i);
			s.closeServerChat();
		}
	}

}
