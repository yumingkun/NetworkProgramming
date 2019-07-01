package socket8_Gui_UsersChat.servergui;

import java.net.ServerSocket;
import java.net.Socket;

public class ServerLisenter extends Thread {

	ServerSocket server;

	public ServerLisenter(ServerSocket server) {

		this.server = server;
	}

	@Override
	public void run() {
		Socket socket = null;
		try {

			while (true) { // 监听多个客户端的连接
				socket = server.accept(); // 监听客户端连接，客户端连接后，实例化Socket对象
				// textLog.append("客户端信息：" + socket + "\r\n");
				ServerMG.getServerMG().setLogTest("客户端信息：" + socket);
				new ServerChat(socket).start(); // 开启新线程用户与客户的数据交互
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
