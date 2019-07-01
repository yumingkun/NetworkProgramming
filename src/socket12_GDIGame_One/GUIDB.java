package socket12_GDIGame_One;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Panel;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUIDB extends JFrame {

	private JPanel contentPane;
	public JButton button;
	public JButton button_1;
	public Panel panel;

	int[][] ChessData = new int[15][15];

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIDB frame = new GUIDB();
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
	public GUIDB() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 363, 524);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		button = new JButton("绘制");
		button.addActionListener(new ButtonActionListener());
		button.setBounds(44, 422, 93, 23);
		contentPane.add(button);

		button_1 = new JButton("重绘");
		button_1.addActionListener(new Button_1ActionListener());
		button_1.setBounds(212, 422, 93, 23);
		contentPane.add(button_1);

		 
		panel = new myPanel(ChessData);
		panel.addMouseListener(new PanelMouseListener());
		panel.setBounds(10, 10, 326, 402);
		contentPane.add(panel);
	}

	 
	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {

			for (int i = 0; i < ChessData.length; i++) {
				for (int j = 0; j < ChessData[0].length; j++) {
					ChessData[i][j] = 1;
				}
			}
			panel.repaint();

		}
	}

	 
	private class Button_1ActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {

			for (int i = 0; i < ChessData.length; i++) {
				for (int j = 0; j < ChessData[0].length; j++) {
					ChessData[i][j] = 0;
				}
			}
			panel.repaint();

		}
	}

	 
	int chessType = 1;

	private class PanelMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			int x = e.getX();
			int y = e.getY();

			setTitle(x + "|" + y);
			int left = 20;
			int top = 20;

			int xx = Math.round((x - left) / 20f);
			int yy = Math.round((y - top) / 20f);
			if (xx >= 0 && xx < 15 && yy >= 0 && yy < 15) {
				ChessData[xx][yy] = chessType;
				panel.repaint();
				
				if(isWin(xx,yy,chessType)){
					String str=chessType==1?"黑子":"白子";
					JOptionPane.showMessageDialog(null, str+"胜了");
				}
				
				chessType=chessType % 2==0? 1:2;
				 
				
			}

		}
	}
	
	//鍒ゆ柇杈撹耽
	public boolean isWin(int x, int y, int chessType) {

		int [][]GameData=ChessData;
		// =======对水平方向的连接棋子数进行判断
		int c1 = 0, c2 = 0;
		// 水平向左数
		for (int j = x; j >= 0; j--) {
			if (GameData[j][y] == chessType)
				c1++;
			else
				break;
		}
		// 水平向右数
		for (int j = x; j < 15; j++) {
			if (GameData[j][y] == chessType)
				c2++;
			else
				break;
		}
		if (c1 + c2 - 1 >= 5) {
			return true;
		}
		// ==========对垂直方向的连接棋子数进行判断
		//垂直向上
		c1 = 0;
		c2 = 0;
		for (int j = y; j >= 0; j--) {
			if (GameData[x][j] == chessType)
				c1++;
			else
				break;
		}
		// 垂直向下
		for (int j = y; j < 15; j++) {
			if (GameData[x][j] == chessType)
				c2++;
			else
				break;
		}
		if (c1 + c2 - 1 >= 5) {
			return true;
		}

		// ========对左上方到右下方向的连接棋子数进行判断
		/* 向左上方数 */
		c1 = 0;
		c2 = 0;
		for (int j = x, k = y; (j >= 0) && (k >= 0); j--, k--) {
			if (GameData[j][k] == chessType)
				c1++;
			else
				break;
		}
		/* 向右下方数 */
		for (int j = x, k = y; (j < 15) && (k < 15); j++, k++) {
			if (GameData[j][k] == chessType)
				c2++;
			else
				break;
		}
		if (c1 + c2 - 1 >= 5) {
			return true;
		}

		// ========对右上方到左下方向的连接棋子数进行判断
		/* 向右上方数 */
		c1 = 0;
		c2 = 0;
		for (int j = x, k = y; (j >= 0) && (k >= 0); j++, k--) {
			if (GameData[j][k] == chessType)
				c1++;
			else
				break;
		}
		/* 向左下方数 */
		for (int j = x, k = y; (j < 15) && (k < 15); j--, k++) {
			if (GameData[j][k] == chessType)
				c2++;
			else
				break;
		}
		if (c1 + c2 - 1 >= 5) {
			return true;
		}

		return false;

	}
	
	
}
