package socket10_UDP_Computer;

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
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.event.ActionEvent;

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
	DatagramSocket dsRec = null;

	private class ButtonStartActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {

			int port = Integer.parseInt(textPort.getText().trim());

			try {
				dsRec = new DatagramSocket(port);
				textLog.append("UDP服务器监听已开启...\r\n");

				new UDPThd().start();

			} catch (Exception e) {
				// TODO: handle exception
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

		}
	}

	class UDPThd extends Thread {

		// 协议 +|num1|num2
		@Override
		public void run() {
			try {
				while (true) {
					byte[] bsRec = new byte[1024];
					DatagramPacket dpRec = new DatagramPacket(bsRec, bsRec.length);
					dsRec.receive(dpRec);// 监听数据

					// 接收消息
					String strRec = new String(bsRec, 0, dpRec.getLength(), "utf-8");
					textLog.append("收到：" + strRec + "\r\n");

					// 处理数据
					String[] commnd = strRec.split("\\|");
					String str = null;
					// 协议 +|num1|num2
					if ("+".equals(commnd[0])) {
						int num1 = Integer.parseInt(commnd[1]);
						int num2 = Integer.parseInt(commnd[2]);
						str = Integer.toString(num1 + num2);
					} else if ("-".equals(commnd[0])) {
						int num1 = Integer.parseInt(commnd[1]);
						int num2 = Integer.parseInt(commnd[2]);
						str = Integer.toString(num1 - num2);
					} else if ("*".equals(commnd[0])) {
						int num1 = Integer.parseInt(commnd[1]);
						int num2 = Integer.parseInt(commnd[2]);
						str = Integer.toString(num1 * num2);
					} else if ("/".equals(commnd[0])) {
						float  num1 = Float.parseFloat(commnd[1]);
						float num2 = Float.parseFloat(commnd[2]);
						if (num2==0) {
							str="num2不能为0";
						}
						str = Float.toString(num1 / num2);
					}

					// 发送消息
					String strRtn = ":" + str;
					byte[] bsSend = strRtn.getBytes("utf-8");
					DatagramPacket dpSend = new DatagramPacket(bsSend, bsSend.length, dpRec.getAddress(),
							dpRec.getPort());
					dsRec.send(dpSend);

				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (dsRec != null) {
					dsRec.close();
				}
			}

		}
	}

}
