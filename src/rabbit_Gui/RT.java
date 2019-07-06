package demo7;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

/**
 * 龟兔赛跑
 */
public class RT extends JFrame {

	private JPanel contentPane;
	public JButton button;
	public JLabel R;
	public JLabel T;
	public JButton button_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RT frame = new RT();
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
	public RT() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 738, 556);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		button = new JButton("\u5F00\u59CB");
		button.addActionListener(new ButtonActionListener());
		button.setBounds(219, 368, 93, 23);
		contentPane.add(button);

		/**
		 * 设置图片
		 */

		ImageIcon imgRabbit1=new ImageIcon("img\\乌龟.png");	//缩放图片
		imgRabbit1.setImage(imgRabbit1.getImage().getScaledInstance(60, 80,Image.SCALE_SMOOTH));

		R = new JLabel("");
		R.setBounds(10, 10, 128, 99);
		contentPane.add(R);

		R.setIcon(imgRabbit1);



		ImageIcon imgRabbit2=new ImageIcon("img\\兔子.png");	//缩放图片
		imgRabbit2.setImage(imgRabbit2.getImage().getScaledInstance(60, 80,Image.SCALE_SMOOTH));
		T = new JLabel("");
		T.setBounds(10, 122, 128, 109);
		contentPane.add(T);

		T.setIcon(imgRabbit2);

		button_1 = new JButton("\u91CD\u7F6E");
		button_1.addActionListener(new Button_1ActionListener());
		button_1.setBounds(372, 368, 93, 23);
		contentPane.add(button_1);

	}

	/**
	 * 点击开始
	 * @author Administrator
	 *
	 */
	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {

			T.setLocation(0, T.getLocation().y);
			R.setLocation(0, R.getLocation().y);
			Tortoise tortoise=new Tortoise();
			Rabbit rabbit=new Rabbit();
			tortoise.start();
			rabbit.start();
		}
	}

	/**
	 * 重置
	 * @author Administrator
	 *
	 */
	private class Button_1ActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			T.setLocation(0, T.getLocation().y);
			R.setLocation(0, R.getLocation().y);
		}
	}


	/**
	 * 乌龟
	 * @author Administrator
	 *
	 */
	public class Tortoise extends Thread{
		int tx=R.getLocation().x;


		int racelen=500;
		int timespace=10;
		public void run(){
			int step=0;
			while (step<racelen) {
				R.setLocation(tx+step, R.getLocation().y);
				try {
					Thread.sleep(timespace);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				step++;
				System.out.println("乌龟爬了："+step);
			}
			System.out.println("乌龟到达了终点=============");
		}

	}


	/**
	 * 兔子
	 * @author Administrator
	 *
	 */
	public class Rabbit extends Thread{

		int rx=T.getLocation().x;
		int racelen=500;
		int timespace=3;
		int sleeppoint=0;//兔子睡觉的时间点
		public void run(){
			int step=0;
			sleeppoint=(int)(Math.random()*400)+50;
			while (step<racelen) {
				T.setLocation(rx+step, T.getLocation().y);
				try {
					Thread.sleep(timespace);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (step==sleeppoint) {

					ImageIcon imgRabbit3=new ImageIcon("img\\Rsleep.png");	//缩放图片
					imgRabbit3.setImage(imgRabbit3.getImage().getScaledInstance(60, 80,Image.SCALE_SMOOTH));
					T.setIcon(imgRabbit3);
					System.out.println("兔子睡觉了-----------------------");
					int sleeplen=(int)(Math.random()*3000);//兔子睡觉时间
					try {
						Thread.sleep(sleeplen);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("兔子醒了++++++++++++++++++++++++");
				}

				ImageIcon imgRabbit2=new ImageIcon("img\\兔子.png");	//缩放图片
				imgRabbit2.setImage(imgRabbit2.getImage().getScaledInstance(60, 80,Image.SCALE_SMOOTH));

				T.setIcon(imgRabbit2);
				step++;
				System.out.println("兔子跑了："+step);
			}
			System.out.println("兔子到达了终点=============");
		}

	}
}
