package socket11_UDP_CMD;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;



import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.awt.event.ActionEvent;

public class sendGUI extends JFrame {

	private JPanel contentPane;
	public JPanel panel;
	public JButton buttonInit;
	public JPanel panel_1;
	public JButton buttonPlanit;
	public JButton buttonNod;
	public JButton button;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					sendGUI frame = new sendGUI();
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
	public sendGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "\u8BBE\u7F6E\u4FE1\u606F", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		panel.setBounds(10, 10, 414, 96);
		contentPane.add(panel);
		panel.setLayout(null);

		buttonInit = new JButton("\u521D\u59CB\u5316");
		buttonInit.addActionListener(new ButtonInitActionListener());
		buttonInit.setBounds(311, 37, 93, 23);
		panel.add(buttonInit);

		panel_1 = new JPanel();
		panel_1.setBounds(10, 116, 414, 108);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		buttonPlanit = new JButton("\u6253\u5F00\u753B\u56FE");
		buttonPlanit.addActionListener(new ButtonPlanitActionListener());
		buttonPlanit.setBounds(0, 34, 105, 45);
		panel_1.add(buttonPlanit);

		buttonNod = new JButton("\u6253\u5F00\u8BB0\u4E8B\u672C");
		buttonNod.addActionListener(new ButtonNodActionListener());
		buttonNod.setBounds(159, 34, 105, 45);
		panel_1.add(buttonNod);

		button = new JButton("\u6253\u5F00\u8BA1\u7B97\u5668");
		button.addActionListener(new ButtonActionListener());
		button.setBounds(309, 34, 105, 45);
		panel_1.add(button);
	}

	/**
	 * 初始化
	 * @author Administrator
	 *
	 */
	MulticastSocket dsSocket=null;
	private class ButtonInitActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {

			try {
				dsSocket=new MulticastSocket();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * 打开画图
	 * @author Administrator
	 *
	 */
	private class ButtonPlanitActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			sendMessage("MSPAINT");
		}
	}
	private class ButtonNodActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			sendMessage("NOTEPAD");
		}
	}
	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			sendMessage("CALC");
		}
	}



	public void sendMessage(String send) {
		try {
			byte[] bsSend=send.getBytes("utf-8");
			InetAddress ipSend=InetAddress.getByName("224.1.1.1");
			int port=8800;
			DatagramPacket dpSend=new DatagramPacket(bsSend, bsSend.length,ipSend,port);
			dsSocket.send(dpSend);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


}
