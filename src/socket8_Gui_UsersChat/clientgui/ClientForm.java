package socket8_Gui_UsersChat.clientgui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.xml.bind.DatatypeConverter;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.JList;

public class ClientForm extends JFrame {

	private JPanel contentPane;
	public JPanel panel;
	public JLabel lblip;
	public JTextField textIP;
	public JLabel label;
	public JTextField textPot;
	public JButton buttonLogin;
	public JButton buttonExit;
	public JPanel panel_1;

	public JLabel label_4;
	public JTextField txtUser;
	public JScrollPane scrollPane;
	public JTextArea textSend;
	public JButton buttonAllSend;
	public JButton buttonSend;
	public JScrollPane scrollPane_1;
	public JTextArea textLog;
	public JScrollPane scrollPane_2;
	public JList listUser;

	// 定义listModel
	DefaultListModel<String> userListModel;
	public JButton sendAll;

	// ClientChat ctThread;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					ClientForm frame = new ClientForm();
					frame.setVisible(true);
					// 设置管理类
					ClientMG.getClientMG().setClientForm(frame);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ClientForm() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 567, 692);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		panel = new JPanel();
		panel.setBorder(
				new TitledBorder(null, "\u767B\u5F55\u4FE1\u606F", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 0, 531, 85);
		contentPane.add(panel);
		panel.setLayout(null);

		lblip = new JLabel("\u670D\u52A1\u5668IP\uFF1A");
		lblip.setBounds(10, 48, 75, 15);
		panel.add(lblip);

		textIP = new JTextField();
		textIP.setText("192.168.1.89");
		textIP.setBounds(69, 30, 84, 21);
		panel.add(textIP);
		textIP.setColumns(10);

		label = new JLabel("\u7AEF\u53E3\uFF1A");
		label.setBounds(163, 33, 54, 15);
		panel.add(label);

		textPot = new JTextField();
		textPot.setText("8848");
		textPot.setBounds(196, 30, 42, 21);
		panel.add(textPot);
		textPot.setColumns(10);

		buttonLogin = new JButton("\u767B\u5F55");
		buttonLogin.addActionListener(new ButtonLoginActionListener());
		buttonLogin.setBounds(369, 29, 75, 23);
		panel.add(buttonLogin);

		buttonExit = new JButton("\u9000\u51FA");
		buttonExit.addActionListener(new ButtonExitActionListener());
		buttonExit.setBounds(454, 29, 67, 23);
		panel.add(buttonExit);

		label_4 = new JLabel("\u7528\u6237\u540D:");
		label_4.setBounds(245, 33, 54, 15);
		panel.add(label_4);

		txtUser = new JTextField();
		txtUser.setText("yy");
		txtUser.setBounds(293, 30, 66, 21);
		panel.add(txtUser);
		txtUser.setColumns(10);

		panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "\u64CD\u4F5C", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 479, 531, 165);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(24, 36, 497, 77);
		panel_1.add(scrollPane);

		textSend = new JTextArea();
		textSend.setLineWrap(true);
		scrollPane.setViewportView(textSend);

		buttonSend = new JButton("\u53D1\u9001");
		buttonSend.addActionListener(new ButtonSendActionListener());
		buttonSend.setBounds(428, 123, 93, 23);
		panel_1.add(buttonSend);

		sendAll = new JButton("\u7FA4\u53D1");
		sendAll.addActionListener(new SendAllActionListener());
		sendAll.setBounds(328, 123, 93, 23);
		panel_1.add(sendAll);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBorder(
				new TitledBorder(null, "\u804A\u5929\u8BB0\u5F55", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_1.setBounds(20, 95, 291, 374);
		contentPane.add(scrollPane_1);

		textLog = new JTextArea();
		textLog.setLineWrap(true);
		scrollPane_1.setViewportView(textLog);

		scrollPane_2 = new JScrollPane();
		scrollPane_2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_2.setBorder(
				new TitledBorder(null, "\u5728\u7EBF\u7528\u6237", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrollPane_2.setBounds(321, 95, 220, 375);
		contentPane.add(scrollPane_2);

		// 加入list
		userListModel = new DefaultListModel<>();
		listUser = new JList(userListModel);
		scrollPane_2.setViewportView(listUser);
	}

	/**
	 * 登录
	 * 
	 * @author Administrator
	 *
	 */
	private class ButtonLoginActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {

			String ip = textIP.getText().trim();
			int port = Integer.parseInt(textPot.getText().trim());

			if (ClientMG.getClientMG().Connect(ip, port, txtUser.getText().trim())) {
				ClientMG.getClientMG().setLogTxt("已连接到服务器\r\n");
			} else {
				ClientMG.getClientMG().setLogTxt("连接服务器失败\r\n");
			}

			// try {
			//
			// socket = new Socket(ip, port);
			// System.out.println("已经连接到服务器：");
			//// textSu.setText("已经连接到服务器");
			// ctThread=new ClientChat(socket);
			// ctThread.start();
			// } catch (IOException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }

		}
	}

	/**
	 * 客户端退出
	 * 
	 * @author Administrator
	 *
	 */
	private class ButtonExitActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {

			// 1、向服务器发送下线消息：OFFLINE|username ---（退出请求发送的客户端）
			String SenderName = ClientMG.getClientMG().getClientChat().getName();
			String strSend = "OFFLINE|" + SenderName;
			ClientMG.getClientMG().getClientChat().sendMessage(strSend);

			// 2、清空在线用户列表
			ClientMG.getClientMG().removeItems();

			// 3、消息提示已断开连接
			System.out.println("已断开连接");

		}
	}

	/**
	 * 发送
	 * 
	 * @author Administrator
	 *
	 */
	private class ButtonSendActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			if (listUser.getSelectedIndex() >= 0) {
				String SenderName = ClientMG.getClientMG().getClientChat().getName();
				String RecName = listUser.getSelectedValue().toString();
				String strMsg = textSend.getText().trim();

				String MSGinfo;// base64
				try {
					MSGinfo = DatatypeConverter.printBase64Binary(strMsg.getBytes("UTF-8"));
					String strSend = "MSG|" + SenderName + "|" + RecName + "|" + MSGinfo;

					ClientMG.getClientMG().getClientChat().sendMessage(strSend);

					ClientMG.getClientMG().setLogTxt("[我]");
					ClientMG.getClientMG().setLogTxt(strMsg);
					textSend.setText("");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}
	}

	// send all
	private class SendAllActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {

			try {
				String SenderName = ClientMG.getClientMG().getClientChat().getName();
				String RecName = "ALL";

				String strMsg = textSend.getText().trim();
				//base64
				String MSGinfo = DatatypeConverter.printBase64Binary(strMsg.getBytes("UTF-8"));
				String strSend = "MSG|" + SenderName + "|" + RecName + "|" + MSGinfo;
				ClientMG.getClientMG().getClientChat().sendMessage(strSend);

				ClientMG.getClientMG().setLogTxt("[我]");
				ClientMG.getClientMG().setLogTxt(strMsg);
				textSend.setText("");

			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
