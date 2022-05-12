package frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import elements.Composition;
import elements.Player;
import util.FileUtil;

public class ChessFrame extends JFrame implements MouseListener{
	private static final long serialVersionUID = 1L;
	
	int step = 1;
	int x = 0;
	int y = 0;
	BufferedImage image = null;
	boolean canPlay = false;
	int[][] allChess = new int[16][16];
	long[] times = new long[256];
	Date before = new Date();
	Date now = new Date();
	Composition composition;
	Player player;
	
	public ChessFrame(Player player, Composition composition) {
		this.setSize(1087, 753);
		this.setTitle("五子棋");
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.addMouseListener(this);
		
		this.setVisible(true);
		this.composition = composition;
		this.player = player;
	}

	public void paint(Graphics g) {
		BufferedImage bi = new BufferedImage(1087, 753, BufferedImage.TYPE_INT_RGB);
		Graphics g2 = bi.createGraphics();
		//绘制背景
		try {
			image = ImageIO.read(new File("img/五子棋盘7.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		g2.drawImage(image, 0, 30, this);
		g2.setColor(Color.black);
		//绘制棋盘(x:188 -> 833, y:64 -> 709)
		for(int i = 0; i < 16; i++) {
			g2.drawLine(188, 64 + i * 43, 833, 64 + i * 43);
			g2.drawLine(188 + i * 43, 64, 188 + i * 43, 709);
		}
		//绘制当前棋子颜色
		if(step > 0) {
			g2.setColor(Color.black);
			g2.fillOval(100, 50, 36, 36);
		} else {
			g2.setColor(Color.white);
			g2.fillOval(100, 50, 36, 36);
		}
		//绘制全部棋子
		for(int i = 0; i < 16; i++) {
			for(int j = 0; j < 16; j ++) {
				if(allChess[i][j] > 0) {
					//黑子
					int tempX = i * 43 + 188;
					int tempY = j * 43 + 64;
					g2.setColor(Color.black);
					g2.fillOval(tempX - 18, tempY - 18, 36, 36);
				}
				if(allChess[i][j] < 0) {
					//白子
					int tempX = i * 43 + 188;
					int tempY = j * 43 + 64;
					g2.setColor(Color.white);
					g2.fillOval(tempX - 18, tempY - 18, 36, 36);
				}
			}
		}
		g2.setFont(new Font("黑体", Font.BOLD, 30));
		g2.setColor(Color.black);
		g2.drawString("当前", 20, 80);
		g.drawImage(bi,  0,  0,  this);
	}
	
	
	@Override
	public void mousePressed(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		if(canPlay) {
			now = new Date();
			if(step > 0) {
				times[step - 1] = now.getTime() - before.getTime();
			} else {
				times[step * -1 - 1] = now.getTime() - before.getTime();
			}
			if(x >= 180 && x <= 840 && y >= 60 && y <= 715) {
				x = (x - 188 + 20)/ 43;
				y = (y - 64  + 20)/ 43;
				if(allChess[x][y] == 0) {
					allChess[x][y] = step;
					if(step > 0) {
						step++;
						step = -1 * step;
					} else {
						step--;
						step = -1 * step;
					}
				}
				this.repaint();
				isWin();
			} 
		}
		//认输按键
		if(x >= 925 && x <= 1040 && y >= 360 && y <= 410) {
			if(canPlay) {
				if(step > 0) {
					JOptionPane.showMessageDialog(null, "黑方认输，白方胜！");
					canPlay = false;
				}
				if(step <= 0) {
					JOptionPane.showMessageDialog(null, "白方认输，黑方胜！");
					canPlay = false;
				}
			}
		}
		//退出按钮
		if(x >= 922 && x <= 1040 && y >= 650 && y <= 700) {
			int option = JOptionPane.showConfirmDialog(null, "您要退出登录吗", "注意", 2);
			if(option == 2) {
				return;
			}
			this.dispose();
			new LoginFrame();
		}
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
		//开始按键
		if(x >= 920 && x <= 1030 && y >= 110 && y <= 160) {
			canPlay = true;
			for(int i = 0; i < 256 && times[i] != 0; i++) {
				times[i] = 0;
			}
			before = new Date();
			for(int i = 0; i < 16; i++) {
				for(int j = 0; j < 16; j ++) {
					allChess[i][j] = 0;
				}
			}
			this.repaint();
			step = 1;
			JOptionPane.showMessageDialog(this, "游戏开始");
		} else if(!(x >= 922 && x <= 1040 && y >= 650 && y <= 700 || 
				  x >= 20 && x <= 130 && y >= 415 && y <= 459)){
			if(!canPlay) {
				JOptionPane.showMessageDialog(this, "请点击开始按钮进行游戏！");
			}
		}
		//悔棋按键
		if(x >= 925 && x <= 1055 && y >= 240 && y <= 285) {
			if(canPlay) {
				canTakeBack();
				repaint();
			}
		}
		//对局回看按钮
		if(x >= 20 && x <= 130 && y >= 415 && y <= 459) {
			if(player != null) {
				ReviewFrame.getReviewFrame(player);
			} else {
				JOptionPane.showMessageDialog(null, "未登录");
			}
		}
	}
	
	public void canTakeBack() {
		loop:for(int i = 0; i < 16; i++) {
			for(int j = 0; j < 16; j++) {
				if(step == 1) {
					JOptionPane.showMessageDialog(this, "当前局面无法悔棋");
					return;
				}
				if(step > 0 && allChess[i][j] == -1 * (step - 1)) {
					allChess[i][j] = 0;
					step = -1 * (step - 1);
					break loop;
				} else if(step < 0 && allChess[i][j] == -1 * (step + 1)) {
					allChess[i][j] = 0;
					step = -1 * (step + 1);
					break loop;
				}      
			}
		}
	}
	
	public void isWin() {
		boolean CheckWin = this.checkWin();
		if(CheckWin == true && allChess[x][y] > 0) {
			JOptionPane.showMessageDialog(null, " 黑方胜！ ");
			canPlay = false;
			if(player != null) {
				composition.setAllChess(allChess);
				composition.setTimes(times);
				ArrayList<Composition> list = player.getCompositionList();
				list.add(composition);
				player.setCompositionList(list);
				try {
					saveComposition(composition);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		if(CheckWin == true && allChess[x][y] < 0) {
			JOptionPane.showMessageDialog(null, " 白方胜！ ");
			canPlay = false;
			if(player != null) {
				composition.setAllChess(allChess);
				composition.setTimes(times);
				ArrayList<Composition> list = player.getCompositionList();
				list.add(composition);
				player.setCompositionList(list);
				try {
					saveComposition(composition);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	public int checkWin1() {
		int color = allChess[x][y] % 2;
		int i = 1;
		int count = 1;
		while(x+i < 16 && allChess[x+i][y] % 2 == color && allChess[x+i][y] != 0) {
			count++;
			i++;
		}
		i = 1;
		while(x-i >= 0 && allChess[x-i][y] % 2 == color && allChess[x-i][y] != 0) {
			count++;
			i++;
		}
		return count;
	}
	
	public int checkWin2() {
		int color = allChess[x][y] % 2;
		int i = 1;
		int count = 1;
		while(y+i < 16 && allChess[x][y+i] % 2 == color && allChess[x][y+i] != 0) {
			count++;
			i++;
		}
		i = 1;
		while(y-i >= 0 && allChess[x][y-i] % 2 == color && allChess[x][y-i] != 0) {
			count++;
			i++;
		}
		return count;
	}
	
	public int checkWin3() {
		int color = allChess[x][y] % 2;
		int i = 1;
		int count = 1;
		while(y+i < 16 && x+i < 16 && allChess[x+i][y+i] % 2 == color && allChess[x+i][y+i] != 0) {
			count++;
			i++;
		}
		i = 1;
		while(y-i >= 0 && x-i >= 0 && allChess[x-i][y-i] % 2 == color && allChess[x-i][y-i] != 0) {
			count++;
			i++;
		}
		return count;
	}
	
	public int checkWin4() {
		int color = allChess[x][y] % 2;
		int i = 1;
		int count = 1;
		while(y-i >= 0 && x+i < 16 && allChess[x+i][y-i] % 2 == color && allChess[x+i][y-i] != 0) {
			count++;
			i++;
		}
		i = 1;
		while(y+i < 16 && x-i >= 0 && allChess[x-i][y+i] % 2 == color && allChess[x-i][y+i] != 0) {
			count++;
			i++;
		}
		return count;
	}
	
	public boolean checkWin() {
		int flag = 0;
		//判断横向
		int count = checkWin1();
		if(count >= 5) {
			flag = 1;
		}
		//判断纵向
		count = checkWin2();
		if(count >= 5) {
			flag = 1;
		}
		//左上右下
		count = checkWin3();
		if(count >= 5) {
			flag = 1;
		}
		//左下右上
		count = checkWin4();
		if(count >= 5) {
			flag = 1;
		}
		if(flag == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	public void saveComposition(Composition composition) throws IOException {
		String str1 = "";
		for(int i = 0; i < 256 && composition.getTimes()[i] != 0; i++) {
			str1 += String.valueOf(composition.getTimes()[i]) + ",";
		}
		String str2 = "";
		for(int i = 0; i < 16; i++) {
			for(int j = 0; j < 16; j++) {
				str2 += String.valueOf(composition.getAllChess()[i][j]) + ",";
			}
		}
		String str3 = "";
		str3 = "username:" + composition.getUsername() + ":" + 
		      "name1:" + composition.getName1() + ":" +
		      "name2:" + composition.getName2() + ":" +
		      "gamename:" + composition.getGamename() + ":" +
		      "times:" + str1 + ":"+
		      "allChess:" + str2;
		FileUtil.writeData(str3, "Composition.txt", true);
	}
	
//	public int checkCount(int xChange, int yChange, int color) {
//	int count = 1;
//	int tempX = xChange;
//	int tempY = yChange;
//	while(x + xChange < 16 && y + yChange < 16 && y + yChange >= 0 
//	      && color == allChess[x + xChange][y + yChange]) {
//		count++;
//		if(x != 0) {
//			xChange++;
//		}
//		if(y != 0) {
//			if(y > 0) {
//				yChange++;
//			} else {
//				yChange--;
//			}
//		}
//	}
//	xChange = tempX;
//	yChange = tempY;
//	while(x - xChange >= 0 && y - yChange < 16 && y - yChange >= 0 
//	     && color == allChess[x - xChange][y - yChange]) {
//		count++;
//		if(x != 0) {
//			xChange++;
//		}
//		if(y != 0) {
//			if(y > 0) {
//				yChange++;
//			} else {
//				yChange--;
//			}
//		}
//	}
//	return count;
//}
}
