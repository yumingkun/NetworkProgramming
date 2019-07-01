package demo4;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JProgressBar;
import java.awt.Color;

public class FileGui extends JFrame {

	private JPanel contentPane;
	public JLabel label;
	public JTextField input1;
	public JLabel label_1;
	public JTextField input2;
	public JButton button;
	public JProgressBar progressBar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FileGui frame = new FileGui();
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
	public FileGui() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 570, 277);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		label = new JLabel("\u6E90\u6587\u4EF6\u8DEF\u5F84");
		label.setBounds(40, 32, 88, 32);
		contentPane.add(label);
		
		input1 = new JTextField();
		input1.setBounds(138, 36, 366, 26);
		contentPane.add(input1);
		input1.setColumns(10);
		
		label_1 = new JLabel("\u76EE\u6807\u6587\u4EF6\u8DEF\u5F84");
		label_1.setBounds(40, 91, 118, 23);
		contentPane.add(label_1);
		
		input2 = new JTextField();
		input2.setBounds(138, 90, 366, 26);
		contentPane.add(input2);
		input2.setColumns(10);
		
		button = new JButton("\u5F00\u59CB\u590D\u5236");
		button.addActionListener(new ButtonActionListener());
		button.setBounds(40, 196, 112, 32);
		contentPane.add(button);
		
		progressBar = new JProgressBar();
		progressBar.setBackground(Color.WHITE);
		progressBar.setBounds(40, 157, 464, 14);
		contentPane.add(progressBar);
	}
	/**
	 * 复制文件
	 * @author Administrator
	 *
	 */
	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			String input=input1.getText();
			String output=input2.getText();
			
			FileInputStream fis =null;
			DataInputStream dis=null;
			FileOutputStream fos=null;
			DataOutputStream dos=null;
			try {
				fis =new FileInputStream(input);
			    dis =new DataInputStream(fis);
				
				progressBar.setMaximum(fis.available());//设置进度条最大为源文件大小
				
			    fos =new FileOutputStream(output);
			    dos =new DataOutputStream(fos);
				
				
				byte[] buffer =new byte[1024];
				int icurrpos=0;//储存当前读取文件的有效字节数
				int value=0;
				 
				while ((icurrpos=dis.read(buffer, 0, 1024))!=-1) {
					dos.write(buffer, 0, icurrpos);
					dos.flush();
					value+=icurrpos;
					progressBar.setValue(value);
				}
			
				 
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {

				try {
					dos.close();
					fos.close();
					fis.close();
					dis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		 
		
			
			 
		 
			
		}
	}
}
