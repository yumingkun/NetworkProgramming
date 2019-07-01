package socket13_GDIGame_END.Client;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.MessageFormat;

public class GameFormClient extends JFrame {

	private JPanel contentPane;
	public JPanel panel;
	public JPanel panel_1;
	public JLabel lbBlack;
	public JLabel lbWhite;
	public JPanel panelGame;

	int [][] chessData=new int [15][15];  //15*15, 1:黑子；2：白子；0：空白
	public JButton btnExit;
	public JScrollPane scrollPane;
	public JTextArea textLog;
	public JTextField textSend;
	public JButton btnNewButton_1;

	//相关的游戏变量
	boolean isClick;  //控制出子的开关
	int chessType;
	Client cfm;
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					GameFormClient frame = new GameFormClient();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public GameFormClient(String sUserB,String sUserW,int chessType,Client cfm) {
		this.cfm=cfm;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 801, 429);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "\u9ED1\u65B9", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 10, 150, 155);
		contentPane.add(panel);
		panel.setLayout(null);

		lbBlack = new JLabel("");
		lbBlack.setBounds(30, 68, 54, 15);
		panel.add(lbBlack);

		panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u767D\u65B9", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(10, 190, 150, 141);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		lbWhite = new JLabel("");
		lbWhite.setBounds(32, 76, 54, 15);
		panel_1.add(lbWhite);

		panelGame = new myPanel(chessData);
		panelGame.addMouseListener(new PanelGameMouseListener());
		panelGame.setBounds(162, 10, 320, 320);
		contentPane.add(panelGame);

		btnExit = new JButton("\u9000\u51FA\u6E38\u620F");
		btnExit.setBounds(204, 340, 142, 34);
		contentPane.add(btnExit);

		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBorder(new TitledBorder(null, "\u804A\u5929\u8BB0\u5F55", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrollPane.setBounds(492, 0, 286, 333);
		contentPane.add(scrollPane);

		textLog = new JTextArea();
		textLog.setLineWrap(true);
		scrollPane.setViewportView(textLog);

		textSend = new JTextField();
		textSend.setBounds(491, 338, 226, 34);
		contentPane.add(textSend);
		textSend.setColumns(10);

		btnNewButton_1 = new JButton("\u53D1\u9001");
		btnNewButton_1.setBounds(718, 346, 60, 23);
		contentPane.add(btnNewButton_1);

		lbBlack.setText(sUserB);
		lbWhite.setText(sUserW);
		this.chessType=chessType;
		setTitle("白方");
		isClick=false;
	}
	public void setChessData(int x,int y,int chessType){
		chessData[x][y]=chessType;
		panelGame.repaint();
	}
	private class PanelGameMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if(isClick){
				int x=e.getX();
				int y=e.getY();

				//绘图像素点的坐标===>数据源的数组下标
				int left=20;
				int top=20;
				int xx=Math.round((x-left)/20f);
				int yy=Math.round((y-top)/20f);
				if(xx>=0 && xx<15 && yy>=0 && yy<15){
					chessData[xx][yy]=chessType;
					panelGame.repaint();
					String str="SETPOS|{0}|{1}|{2}";
					str=MessageFormat.format(str, xx,yy,chessType);
					cfm.sthd.sendMsg(str);

					isClick=false;
				}
			}
			else{
				JOptionPane.showMessageDialog(null, "等待对方出子！");
			}
		}
	}
}
