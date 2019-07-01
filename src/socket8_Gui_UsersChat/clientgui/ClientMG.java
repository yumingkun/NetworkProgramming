package socket8_Gui_UsersChat.clientgui;

import java.net.Socket;

public class ClientMG {
	private static final ClientMG clientMG = new ClientMG();

	private ClientMG() {
	};

	public static ClientMG getClientMG() {
		return clientMG;
	}

	// 界面操作类
	ClientForm clientForm;

	public void setClientForm(ClientForm clientForm) {
		this.clientForm = clientForm;
	}

	public void setLogTxt(String str) {
		clientForm.textLog.append(str + "\r\n");
	}

	// socket操作类
	ClientChat chat;

	public ClientChat getClientChat() {
		return chat;
	}

	public boolean Connect(String IP, int port, String uName) {
		Socket socket = null;
		try {
			socket = new Socket(IP, port);
			// System.out.println("已经连接到服务器：");
			chat = new ClientChat(socket, uName);
			chat.start();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;

		}

	}

	// 清空在线列表
	public void removeItems() {

		clientForm.userListModel.removeAllElements();

	}

	// 在线用户名 添加到List中
	public void addItems(String[] users) {
		for (int i = 0; i < users.length; i++) {
			clientForm.userListModel.addElement(users[i]);
		}
	}

	// 新添用户
	public void addItem(String user) {

		clientForm.userListModel.addElement(user);

	}

	// 删除用户
	public void removeItem(String user) {

		clientForm.userListModel.removeElement(user);

	}

}
