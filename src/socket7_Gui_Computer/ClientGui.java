package socket7_Gui_Computer;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
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
import java.net.Socket;
import java.awt.event.ActionEvent;

public class ClientGui extends JFrame {

	private JPanel contentPane;
	public JPanel panel;
	public JLabel lblip;
	public JTextField textIP;
	public JLabel label;
	public JTextField textPot;
	public JButton buttonLogin;
	public JButton buttonExit;
	public JPanel panel_1;
	public JLabel label_1;

	ClientThread ctThread;
	public JTextField textSu;
	public JLabel label_2;
	public JLabel label_3;
	public JTextField textNum1;
	public JTextField textNum2;
	public JButton button;
	public JButton button_1;
	public JButton button_2;
	public JButton button_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientGui frame = new ClientGui();
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
	public ClientGui() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 567, 390);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		panel = new JPanel();
		panel.setBorder(
				new TitledBorder(null, "\u767B\u5F55\u4FE1\u606F", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 10, 531, 75);
		contentPane.add(panel);
		panel.setLayout(null);

		lblip = new JLabel("\u670D\u52A1\u5668IP\uFF1A");
		lblip.setBounds(10, 25, 75, 15);
		panel.add(lblip);

		textIP = new JTextField();
		textIP.setText("192.168.1.89");
		textIP.setBounds(66, 22, 143, 21);
		panel.add(textIP);
		textIP.setColumns(10);

		label = new JLabel("\u7AEF\u53E3\uFF1A");
		label.setBounds(219, 25, 54, 15);
		panel.add(label);

		textPot = new JTextField();
		textPot.setText("8848");
		textPot.setBounds(258, 22, 66, 21);
		panel.add(textPot);
		textPot.setColumns(10);

		buttonLogin = new JButton("\u767B\u5F55");
		buttonLogin.addActionListener(new ButtonLoginActionListener());
		buttonLogin.setBounds(334, 21, 93, 23);
		panel.add(buttonLogin);

		buttonExit = new JButton("\u9000\u51FA");
		buttonExit.addActionListener(new ButtonExitActionListener());
		buttonExit.setBounds(437, 21, 93, 23);
		panel.add(buttonExit);

		panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "\u64CD\u4F5C", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 111, 531, 231);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		label_1 = new JLabel("\u64CD\u4F5C\u7ED3\u679C");
		label_1.setBounds(10, 164, 54, 15);
		panel_1.add(label_1);
		
		textSu = new JTextField();
		textSu.setBounds(10, 185, 215, 36);
		panel_1.add(textSu);
		textSu.setColumns(10);
		
		label_2 = new JLabel("\u64CD\u4F5C\u65701");
		label_2.setBounds(10, 36, 54, 15);
		panel_1.add(label_2);
		
		label_3 = new JLabel("\u64CD\u4F5C\u65702");
		label_3.setBounds(10, 99, 54, 15);
		panel_1.add(label_3);
		
		textNum1 = new JTextField();
		textNum1.setBounds(10, 53, 215, 36);
		panel_1.add(textNum1);
		textNum1.setColumns(10);
		
		textNum2 = new JTextField();
		textNum2.setBounds(10, 118, 215, 36);
		panel_1.add(textNum2);
		textNum2.setColumns(10);
		
		button = new JButton("\u51CF\u6CD5-");
		button.addActionListener(new ButtonActionListener());
		button.setBounds(416, 53, 93, 61);
		panel_1.add(button);
		
		button_1 = new JButton("\u52A0\u6CD5+");
		button_1.addActionListener(new Button_1ActionListener());
		button_1.setBounds(289, 53, 93, 61);
		panel_1.add(button_1);
		
		button_2 = new JButton("\u4E58\u6CD5*");
		button_2.addActionListener(new Button_2ActionListener());
		button_2.setBounds(289, 160, 93, 61);
		panel_1.add(button_2);
		
		button_3 = new JButton("\u9664\u6CD5/");
		button_3.addActionListener(new Button_3ActionListener());
		button_3.setBounds(416, 160, 93, 61);
		panel_1.add(button_3);
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
			Socket socket = null;

			try {

				socket = new Socket(ip, port);
				System.out.println("已经连接到服务器：");
				textSu.setText("已经连接到服务器");
				ctThread=new ClientThread(socket);
				ctThread.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * 客户端退出
	 * @author Administrator
	 *
	 */
	private class ButtonExitActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			
			ctThread.sendMessage("CLOSE");
		}
	}
	
	
	/**
	 * 加法
	 * @author Administrator
	 *
	 */
	private class Button_1ActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			String num1=textNum1.getText().trim();
			String num2=textNum2.getText().trim();
			ctThread.sendMessage("+"+"|"+num1+"|"+num2);
		}
	}
	/**
	 * 减法
	 * @author Administrator
	 *
	 */
	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			String num1=textNum1.getText().trim();
			String num2=textNum2.getText().trim();
			ctThread.sendMessage("-"+"|"+num1+"|"+num2);
		}
	}
	/**
	 * 乘法
	 * @author Administrator
	 *
	 */
	private class Button_2ActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			String num1=textNum1.getText().trim();
			String num2=textNum2.getText().trim();
			ctThread.sendMessage("*"+"|"+num1+"|"+num2);
		}
	}
	
	/**
	 * 除法
	 * @author Administrator
	 *
	 */
	private class Button_3ActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			String num1=textNum1.getText().trim();
			String num2=textNum2.getText().trim();
			ctThread.sendMessage("/"+"|"+num1+"|"+num2);
		}
	}
	

	class ClientThread extends Thread {
		Socket socket = null;
		BufferedReader br = null;
		PrintWriter pw = null;

		public ClientThread(Socket socket) {
			this.socket = socket;
		}
		public void run() {
			System.out.println("11111111111");
			try {
				br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
				pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8")));
 
			 
				String str;
				while ((str=br.readLine())!=null) {
					textSu.setText(str);
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (pw != null) {
						pw.close();
					}
					if (br != null) {
						pw.close();
					}
					if (socket != null) {
						pw.close();
					}

				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
			textSu.setText("客户端已退出服务器");

		}

		public void sendMessage(String send) {
			pw.println(send);
			pw.flush();

		}

	}
}
