package socket8_Gui_UsersChat.servergui;

import java.awt.Dialog;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
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
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.event.ActionEvent;

public class ServerForm extends JFrame {

	private JPanel contentPane;
	public JScrollPane scrollPane;
	public JTextArea textLog;
	public JPanel panel;
	public JLabel label;
	public JTextField textPort;
	public JButton buttonStart;
	public JButton buttonStop;

	volatile boolean serverFlag;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerForm frame = new ServerForm();
					frame.setVisible(true);

					// 将frame传入管理类
					ServerMG.getServerMG().setServerForm(frame);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ServerForm() {
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
			if (ServerMG.getServerMG().createServer(port)) {
				ServerMG.getServerMG().setLogTest("服务器已经启动...\r\n");
			} else {
				ServerMG.getServerMG().setLogTest("服务器启动失败...\r\n");
			}

		}

	}

	/**
	 * 关闭服务
	 * 
	 * @author Administrator
	 *
	 */
	private class ButtonStopActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {

			ServerMG.getServerMG().closeServer();
			JOptionPane.showMessageDialog(null,"服务器关闭");
			 
		}
	}

}