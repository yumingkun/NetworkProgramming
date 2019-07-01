package socket13_GDIGame_END.Client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;


import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.awt.event.ActionEvent;

public class Client extends JFrame {

	private JPanel contentPane;
	public JPanel panel;
	public JLabel lblIp;
	public JTextField textIP;
	public JLabel label;
	public JTextField textPort;
	public JButton btnLogin;
	public JButton btnClose;

	GameFormClient gfm;
	static Client frame;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new Client();
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
	public Client() {
		setTitle("\u5BA2\u6237\u7AEF");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 242, 218);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "\u914D\u7F6E\u4FE1\u606F", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 10, 218, 138);
		contentPane.add(panel);
		panel.setLayout(null);

		lblIp = new JLabel("IP");
		lblIp.setBounds(10, 31, 54, 15);
		panel.add(lblIp);

		textIP = new JTextField();
		textIP.setText("192.168.1.201");
		textIP.setBounds(53, 31, 115, 21);
		panel.add(textIP);
		textIP.setColumns(10);

		label = new JLabel("\u7AEF\u53E3");
		label.setBounds(10, 59, 54, 15);
		panel.add(label);

		textPort = new JTextField();
		textPort.setText("8800");
		textPort.setBounds(53, 59, 66, 21);
		panel.add(textPort);
		textPort.setColumns(10);

		label_1 = new JLabel("\u7528\u6237\u540D");
		label_1.setBounds(10, 89, 54, 15);
		panel.add(label_1);

		textUser = new JTextField();
		textUser.setText("AAA");
		textUser.setColumns(10);
		textUser.setBounds(53, 89, 66, 21);
		panel.add(textUser);

		btnLogin = new JButton("\u767B\u5F55");
		btnLogin.setBounds(20, 158, 93, 23);
		contentPane.add(btnLogin);

		btnClose = new JButton("\u5173\u95ED");
		btnClose.setBounds(119, 158, 93, 23);
		contentPane.add(btnClose);
		btnClose.addActionListener(new BtnCloseActionListener());
		btnLogin.addActionListener(new BtnLoginActionListener());
	}
	socketThd sthd;
	public JLabel label_1;
	public JTextField textUser;
	private class BtnLoginActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			String IP=textIP.getText().trim();  //IP
			int port=Integer.parseInt(textPort.getText().trim()); //端口号
			Socket socket=null;
			try {
				socket=new Socket(IP, port);  //建立与服务器的连接
				System.out.println("已连接到服务器。");
				sthd=new socketThd(socket,textUser.getText().trim());
				sthd.start();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	private class BtnCloseActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			//断开连接
			//从客户端发送CLose命令到服务 器端，服务端关闭Socket对象，使当前客户端的br.readLine()返回空值，从而退出While，关闭Socket
			sthd.sendMsg("CLOSE");
		}
	}
	class socketThd extends Thread{
		BufferedReader br=null;
		PrintWriter pw=null;
		Socket socket=null;

		public socketThd(Socket socket,String sName) {
			super(sName);
			this.socket=socket;
		}

		public void run() {
			try {
				br=new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
				pw=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8")));
				sendMsg("LOGIN|"+this.getName());
				String str="";
				while((str=br.readLine())!=null){  //多次发送数据处理的请求
					String [] commands=str.split("\\|");
					if(commands[0].equals("GAMEREADY")){
						String sUserWhite=this.getName();
						String sUserBlack=commands[1];

						gfm=new GameFormClient(sUserBlack,sUserWhite,2,frame);
						gfm.setVisible(true);
					}
					else if(commands[0].equals("SETPOS")){
						int x=Integer.parseInt(commands[1]);
						int y=Integer.parseInt(commands[2]);
						int chessType=Integer.parseInt(commands[3]);

						gfm.setChessData(x, y, chessType);
						gfm.isClick=true;
					}
					else if(commands[0].equals("WIN")){
						String chessType=commands[1];
						String strWin=chessType.equals("1")?"黑子":"白子";
						gfm.isClick=false;
						JOptionPane.showMessageDialog(null, strWin+"胜了");

					}

				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				try {
					if(pw!=null)
						pw.close();
					if(br!=null)
						br.close();
					if(socket!=null)
						socket.close();

				} catch (Exception e2) {
					e2.printStackTrace();
				}

			}
		}
		//发送消息
		public void sendMsg(String str){
			pw.println(str);
			pw.flush();
		}
	}
}
