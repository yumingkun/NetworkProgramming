package socket11_UDP_CMD;

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
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.event.ActionEvent;

/**
 * udp打开电脑程序
 */
public class receiveGUI extends JFrame {

	private JPanel contentPane;
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
					receiveGUI frame = new receiveGUI();
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
	public receiveGUI() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 484, 172);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

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
		textPort.setText("8800");
		textPort.setBounds(46, 47, 66, 21);
		panel.add(textPort);
		textPort.setColumns(10);

		buttonStart = new JButton("\u5F00\u542F\u7A0B\u5E8F");
		buttonStart.addActionListener(new ButtonStartActionListener());
		buttonStart.setBounds(252, 46, 93, 23);
		panel.add(buttonStart);

		buttonStop = new JButton("\u5173\u95ED\u7A0B\u5E8F");
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
	MulticastSocket dsMutiRec = null;

	private class ButtonStartActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {

			int port = Integer.parseInt(textPort.getText().trim());

			try {
				dsMutiRec = new MulticastSocket(port);
				InetAddress ipMuti=InetAddress.getByName("224.1.1.1");
				dsMutiRec.joinGroup(ipMuti);

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

		// 协议  op
		@Override
		public void run() {
			try {
				while (true) {
					byte[] bsRec = new byte[1024];
					DatagramPacket dpRec = new DatagramPacket(bsRec, bsRec.length);
					dsMutiRec.receive(dpRec);// 监听数据

					// 接收消息
					String strRec = new String(bsRec, 0, dpRec.getLength(), "utf-8");

					// 处理数据
					String  commnd =strRec;
					System.out.println(commnd);

					// 协议 +|num1|num2
					if ("MSPAINT".equals(commnd)) {
						Runtime.getRuntime().exec("mspaint");

					} else if ("NOTEPAD".equals(commnd)) {
						Runtime.getRuntime().exec("notepad");
					} else if ("CALC".equals(commnd)) {
						Runtime.getRuntime().exec("calc");
					}



				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (dsMutiRec != null) {
					dsMutiRec.close();
				}
			}

		}
	}
}
