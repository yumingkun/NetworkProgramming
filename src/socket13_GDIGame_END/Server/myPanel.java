package socket13_GDIGame_END.Server;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class myPanel extends JPanel {
	Image imgBoard=new ImageIcon("socket12_GDIGame_One.imgs\\ChessBoard.jpg").getImage();// 读取图片
	Image imgB=new ImageIcon("socket12_GDIGame_One.imgs\\black.png").getImage();// 读取图片
	Image imgW=new ImageIcon("socket12_GDIGame_One.imgs\\white.png").getImage();// 读取图片
	int left=20; //棋盘的边界
	int top=20;
	int [][] chessData;  //15*15, 1:黑子；2：白子；0：空白
	public myPanel(int [][] cData) {
		chessData=cData;
	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(imgBoard, 0, 0, null);  //绘制棋盘
		for(int i=0;i<chessData.length;i++){
			for(int j=0;j<chessData[0].length;j++){
				//从数据源的数组下标===>绘图的坐标值
				int chessX=i*20-10+left;
				int chessY=j*20-10+top;
				if(chessData[i][j]==1){
					g.drawImage(imgB, chessX, chessY, null);
				}
				else if (chessData[i][j]==2){
					g.drawImage(imgW, chessX,chessY, null);
				}
			}
		}

	}
}
