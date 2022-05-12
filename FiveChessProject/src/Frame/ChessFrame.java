package Frame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ChessFrame extends JFrame implements MouseListener{
	
	int step = 1;
	int x = 0;
	int y = 0;
	BufferedImage image = null;
	boolean canPlay = true;
	int[][] allChess = new int[16][16];
	
	public ChessFrame() {
		this.setSize(1087, 753);
		this.setTitle("五子棋");
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.addMouseListener(this);
		
		this.setVisible(true);
		
		try {
			image = ImageIO.read(new File("img/五子棋盘3.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void paint(Graphics g) {
		//双缓冲技术防止屏幕闪烁
		BufferedImage bi = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
		Graphics g2 = bi.createGraphics();
		//绘制背景
		g2.drawImage(image, 0, 30, this);
		//绘制棋盘(x:188 -> 833, y:64 -> 709)
		for(int i = 0; i < 16; i++) {
			g2.drawLine(188, 64 + i * 43, 833, 64 + i * 43);
			g2.drawLine(188 + i * 43, 64, 188 + i * 43, 709);
		}
		//绘制当前棋子颜色
		if(step == 1) {
			g2.setColor(Color.black);
			g2.fillOval(40, 50, 36, 36);
		} else {
			g2.setColor(Color.white);
			g2.fillOval(40, 50, 36, 36);
		}
		//绘制全部棋子
		for(int i = 0; i < 16; i++) {
			for(int j = 0; j < 16; j ++) {
				if(allChess[i][j] == 1) {
					//黑子
					int tempX = i * 43 + 188;
					int tempY = j * 43 + 64;
					g2.setColor(Color.black);
					g2.fillOval(tempX - 18, tempY - 18, 36, 36);
				}
				if(allChess[i][j] == -1) {
					//白子
					int tempX = i * 43 + 188;
					int tempY = j * 43 + 64;
					g2.setColor(Color.white);
					g2.fillOval(tempX - 18, tempY - 18, 36, 36);
				}
			}
		}
		g.drawImage(bi, 0, 0, this);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(canPlay == true) {
			x = e.getX();
			y = e.getY();
			if(x >= 180 && x <= 840 && y >= 60 && y <= 715) {
				x = (x - 188 + 20)/ 43;
				y = (y - 64  + 20)/ 43;
				if(allChess[x][y] == 0) {
					if(step == 1) {
						allChess[x][y] = 1;
						step = -1;
					} else {
						allChess[x][y] = -1;
						step = 1;
					}
					this.repaint();
					boolean CheckWin = this.checkWin();
					if(CheckWin == true && allChess[x][y] == 1) {
						JOptionPane.showMessageDialog(null, " 黑方胜！ ");
						canPlay = false;
					}
					if(CheckWin == true && allChess[x][y] == -1) {
						JOptionPane.showMessageDialog(null, " 白方胜！ ");
						canPlay = false;
					}
				} 
			}
		}
	}
	
	public boolean checkWin() {
		int color = allChess[x][y];
		//判断横向
		int i = 1;
		int count = 1;
		while(x+i < 16 && allChess[x+i][y] == color ) {
			count++;
			i++;
		}
		while(x-i >= 0 && allChess[x-i][y] == color) {
			count++;
			i++;
		}
		if(count >= 5) {
			return true;
		}
		//判断纵向
		i = 1;
		count = 1;
		while(y+i < 16 && allChess[x][y+i] == color ) {
			count++;
			i++;
		}
		while(y-i >= 0 && allChess[x][y-i] == color) {
			count++;
			i++;
		}
		if(count >= 5) {
			return true;
		}
		//左上右下
		i = 1;
		count = 1;
		while(y+i < 16 && x+i < 16 && allChess[x+i][y+i] == color ) {
			count++;
			i++;
		}
		while(y-i >= 0 && x-i >= 0 && allChess[x-i][y-i] == color) {
			count++;
			i++;
		}
		if(count >= 5) {
			return true;
		}
		//左下右上
		i = 1;
		count = 1;
		while(y-i < 16 && x+i < 16 && allChess[x+i][y-i] == color ) {
			count++;
			i++;
		}
		while(y+i >= 0 && x-i >= 0 && allChess[x-i][y+i] == color) {
			count++;
			i++;
		}
		if(count >= 5) {
			return true;
		}
		return false;
	}
}