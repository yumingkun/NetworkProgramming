package socket10_UDP_Computer;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class udpGui extends JFrame {

	private JPanel contentPane;
	public JPanel panel;
	public JPanel panel_1;
	public JButton login;
	public JButton btnNewButton_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					udpGui frame = new udpGui();
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
	public udpGui() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 486, 325);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "\u767B\u5F55\u4FE1\u606F", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		panel.setBounds(10, 10, 450, 83);
		contentPane.add(panel);
		panel.setLayout(null);
		
		login = new JButton("\u767B\u5F55");
		login.addActionListener(new BtnNewButtonActionListener());
		login.setBounds(244, 33, 93, 23);
		panel.add(login);
		
		btnNewButton_1 = new JButton("\u9000\u51FA");
		btnNewButton_1.setBounds(347, 33, 93, 23);
		panel.add(btnNewButton_1);
		
		panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "\u64CD\u4F5C", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 109, 450, 151);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
	}
	private class BtnNewButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
		}
	}
}
