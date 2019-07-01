package socket13_GDIGame_END.Server;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;



import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.*;
import java.io.*;

public class Server extends JFrame {

	private JPanel contentPane;
	public JPanel panel;
	public JLabel label;
	public JTextField textPort;
	public JButton btnStart;
	public JButton btnEND;
	public JScrollPane scrollPane;
	public JTextArea textLog;


	ServerSocket server = null;
	public JLabel label_1;
	public JTextField textUser;

	GameFormServer gfm;//游戏主界面

	static Server frame;
	SocketThd sThd;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new Server();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Server() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 293);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "\u914D\u7F6E\u4FE1\u606F", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 10, 424, 66);
		contentPane.add(panel);
		panel.setLayout(null);

		label = new JLabel("\u7AEF\u53E3\uFF1A");
		label.setBounds(24, 31, 54, 15);
		panel.add(label);

		textPort = new JTextField();
		textPort.setText("8800");
		textPort.setBounds(62, 28, 38, 21);
		panel.add(textPort);
		textPort.setColumns(10);

		btnStart = new JButton("\u5F00\u542F\u6E38\u620F");
		btnStart.addActionListener(new BtnStartActionListener());
		btnStart.setBounds(229, 27, 93, 23);
		panel.add(btnStart);

		btnEND = new JButton("\u5173\u95ED\u6E38\u620F");
		btnEND.addActionListener(new BtnENDActionListener());
		btnEND.setBounds(321, 27, 93, 23);
		panel.add(btnEND);

		label_1 = new JLabel("\u7528\u6237\u540D\uFF1A");
		label_1.setBounds(108, 28, 54, 15);
		panel.add(label_1);

		textUser = new JTextField();
		textUser.setText("syl");
		textUser.setColumns(10);
		textUser.setBounds(161, 28, 38, 21);
		panel.add(textUser);

		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBorder(new TitledBorder(null, "\u6D88\u606F\u8BB0\u5F55", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrollPane.setBounds(10, 86, 424, 164);
		contentPane.add(scrollPane);

		textLog = new JTextArea();
		textLog.setLineWrap(true);
		scrollPane.setViewportView(textLog);
	}
	private class BtnStartActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			int port = Integer.parseInt(textPort.getText().trim()); // //设定端口号
			try {
				server = new ServerSocket(port);  //创建服务Socket，指定端口号
				//System.out.println("服务器开启...");

				new serverThd().start();
				textLog.append("服务器开启...\r\n"+server);

			} catch (Exception e) {

				e.printStackTrace();
			}
		}
	}
	private class BtnENDActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			try {
				if(server!=null)
					server.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	class serverThd extends Thread{
		@Override
		public void run() {
			Socket socket = null;
			try {
				while(true){ //监听多个客户端的连接
					socket = server.accept(); //监听客户端连接，客户端连接后，实例化Socket对象
					textLog.append("客户端信息：" + socket+"\r\n");
					sThd=new SocketThd(socket,textUser.getText().trim());		//开启新线程用户与客户的数据交互
					sThd.start();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
	//建立新的线程，用于循环监听，执行客户端的数据交互
	class SocketThd extends Thread{
		BufferedReader br = null;
		PrintWriter pw = null;
		Socket socket=null;
		public SocketThd(Socket socket,String sName) {
			super(sName);
			this.socket = socket;

		}
		public void run() {

			try {
				//实例化br与pw
				br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
				pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8")));

				pw.println("客户端登录确认。"); //发回给客户端
				pw.flush();
				String str ="";
				while((str = br.readLine())!=null){  //循环响应客户的发送信息//接收客户端发过来的信息,阻塞命令

					String [] commands=str.split("\\|");
					if(commands[0].equals("CLOSE")){
						//命令1时的处理
						textLog.append(socket+"退出连接。");
						break;
					}
					else if(commands[0].equals("LOGIN")){
						String sUserWhite=commands[1];
						String sUserBlack=this.getName();

						gfm=new GameFormServer(sUserBlack,sUserWhite,1,frame);
						gfm.setVisible(true);

						sendMsg("GAMEREADY|"+this.getName());  //将就绪信息发送给游戏对方
					}
					else if(commands[0].equals("SETPOS")){
						int x=Integer.parseInt(commands[1]);
						int y=Integer.parseInt(commands[2]);
						int chessType=Integer.parseInt(commands[3]);

						gfm.setChessData(x, y, chessType);
						gfm.isClick=true;
						//更新棋盘信息后，再判断
						if(gfm.isWin(x,y,chessType)){
							String strWin="WIN|"+chessType;
							sendMsg(strWin);

							String strChess=chessType==1?"黑子":"白子";
							JOptionPane.showMessageDialog(null, strChess+"胜了");
							gfm.isClick=false;
						}

					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				try {
					if (pw != null)
						pw.close();
					if (br != null)
						br.close();
					if (socket != null)
						socket.close();

				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		public void sendMsg(String str){
			pw.println(str); //发回给客户端
			pw.flush();
		}
	}
}
