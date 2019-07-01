package socket7_Gui_Computer;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;



import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.event.ActionEvent;

/**
 * 计算器
 */
public class ServerGUI extends JFrame {

	private JPanel contentPane;
	public JScrollPane scrollPane;
	public JTextArea textLog;
	public JPanel panel;
	public JLabel label;
	public JTextField textPort;
	public JButton buttonStart;
	public JButton buttonStop;

	ServerSocket server;
	volatile boolean serverFlag;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerGUI frame = new ServerGUI();
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
	public ServerGUI() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 484, 619);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBorder(
				new TitledBorder(null, "\u6D88\u606F\u8BB0\u5F55", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrollPane.setBounds(10, 123, 458, 458);
		contentPane.add(scrollPane);

		textLog = new JTextArea();
		textLog.setLineWrap(true);
		textLog.setEditable(false);
		scrollPane.setViewportView(textLog);

		panel = new JPanel();
		panel.setBorder(
				new TitledBorder(null, "\u914D\u7F6E\u4FE1\u606F", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 10, 458, 103);
		contentPane.add(panel);
		panel.setLayout(null);

		label = new JLabel("\u7AEF\u53E3\uFF1A");
		label.setBounds(10, 35, 54, 45);
		panel.add(label);

		textPort = new JTextField();
		textPort.setText("8848");
		textPort.setBounds(46, 47, 66, 21);
		panel.add(textPort);
		textPort.setColumns(10);

		buttonStart = new JButton("\u5F00\u542F\u670D\u52A1");
		buttonStart.addActionListener(new ButtonStartActionListener());
		buttonStart.setBounds(252, 46, 93, 23);
		panel.add(buttonStart);

		buttonStop = new JButton("\u5173\u95ED\u670D\u52A1");
		buttonStop.addActionListener(new ButtonStopActionListener());
		buttonStop.setBounds(355, 46, 93, 23);
		panel.add(buttonStop);
	}

	/**
	 * 开启服务
	 * 
	 * @author Administrator
	 *
	 */
	private class ButtonStartActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			
			int port = Integer.parseInt(textPort.getText().trim());

			try {
				server = new ServerSocket(port);
				serverFlag = true;
				new serverThd().start();
				
				textLog.append("服务器已经启动...\r\n");
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	}
	/**
	 * 关闭服务
	 * @author Administrator
	 *
	 */
	private class ButtonStopActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			
		}
	}

	class serverThd extends Thread {

		@Override
		public void run() {
			Socket socket= null;
			try {

				while (serverFlag) { // 监听多个客户端的连接
					socket = server.accept(); // 监听客户端连接，客户端连接后，实例化Socket对象
					textLog.append("客户端信息：" + socket + "\r\n");
					// System.out.println("客户端信息：" + socket);
					new ScoketThread(socket).start(); // 开启新线程用户与客户的数据交互
				}
			} catch (Exception e) {
					e.printStackTrace();
			}

		}
	}

	/**
	 * 内部类
	 * 
	 * @author Administrator
	 *
	 */
	class ScoketThread extends Thread {
		BufferedReader br = null;
		PrintWriter pw = null;
		Socket socket = null;

		public ScoketThread(Socket socket) {

			this.socket = socket;
		}

		@Override
		public void run() {
			try {
				br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
				pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8")));

				String str ="";
				while ((str= br.readLine())!=null) {
					
//					System.out.println("客户端发来的请求" + str);
					textLog.append("客户端发来的请求" + str+ "\r\n");
					

					String[] commands = str.split("\\|");
					
					String rtn = "";
					int num1 = Integer.parseInt(commands[1]);
					int num2 = Integer.parseInt(commands[2]);
				 
					if ("close".equals(commands[0])) {
//						System.out.println("服务器断开");
						textLog.append("服务器断开");
						break;
					} else if ("+".equals(commands[0])) {
						int su=num1+num2;
						 
						rtn = "服务器回复：" + num1+"+"+num2 + "="+su;
						textLog.append("和："+su);

					} else if ("-".equals(commands[0])) {
					 
						int su=num1-num2;
						 
						rtn = "服务器回复：" + num1+"-"+num2 + "="+su;
						textLog.append("差："+su);
					}	else if ("*".equals(commands[0])) {
					 
					 
						int su=num1*num2;
						 
						rtn = "服务器回复：" + num1+"*"+num2 + "="+su;
						textLog.append("乘："+su);
					}else if ("/".equals(commands[0])) {		
						if (num2==0) {
							rtn="操作数2不能为0";
							textLog.append("操作数2不能为0");
						}else {
							int su=num1/num2;
							rtn = "服务器回复：" + num1+"/"+num2 + "="+su;
							textLog.append("除："+su);
						}
						 
					
						
					}

					pw.println(rtn);	
					pw.flush();

				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
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

	}


}
