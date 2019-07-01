package socket12_GDIGame_One;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;

import javax.swing.ImageIcon;


public class myPanel extends Panel {
	Image imageBoard = new ImageIcon("socket12_GDIGame_One/imgs/ChessBoard.jpg").getImage();
	Image imageW = new ImageIcon("socket12_GDIGame_One/imgs/white.png").getImage();
	Image imageB = new ImageIcon("socket12_GDIGame_One/imgs/black.png").getImage();

	int iLeft = 20;
	int iTop = 20;
	int[][] ChessData;

	public myPanel(int[][] ChessData) {
		this.ChessData = ChessData;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		g.drawImage(imageBoard, 0, 0, null);

		for (int i = 0; i < ChessData.length; i++) {
			for (int j = 0; j < ChessData[0].length; j++) {
				int chessX = i * 20 + iLeft - 10;
				int chessY = j * 20 + iTop - 10;
				if (ChessData[i][j] == 1) {
					// 黑
					g.drawImage(imageB, chessX, chessY, null);
				} else if (ChessData[i][j] == 2) {
					// 白
					g.drawImage(imageW, chessX, chessY, null);
				}

			}

		}

	}

}
