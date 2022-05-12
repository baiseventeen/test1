package frame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import elements.Composition;

public class ReviewPanel extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
	Composition composition;
	int[][] allChess1 = new int[16][16];
	long[] times1 = new long[256];
	BufferedImage image = null;
	Timer timer;
	int num = 0;
	int now = 0;

	public ReviewPanel(Composition composition){
		this.composition = composition;
		
		this.setLayout(null);
		this.setBounds(0, 0, 1086, 753);
		
		JOptionPane.showMessageDialog(null, "点击任意处开始回放");
		
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				myTimer();
			}
		});
	}
	
	private void myTimer() {
		num = returnStep(composition).length;
		times1 = composition.getTimes();
		
		timer = new Timer(1000, this);
		timer.start();
	}
	
	protected void showComposition(int i) {
		String[] step = returnStep(composition);
			String[] arr_str = step[i].split(",");
			int x = Integer.parseInt(arr_str[0]);
			int y = Integer.parseInt(arr_str[1]);
			if(i % 2 == 1) {
				allChess1[x][y] = -1;
			} else {
				allChess1[x][y] = 1;
			}
			repaint();
	}
	
	private String[] returnStep(Composition composition) {
		int[][] allChess = composition.getAllChess();
		int num = 0;
		for(int i = 0; i < 16; i++) {
			for(int j = 0; j < 16; j++) {
				if(allChess[i][j] != 0) {
					num++;
				}
			}
		}
		this.num = num;
		String[] step = new String[num];
		for(int i = 0; i < 16; i++) {
			for(int j = 0; j < 16; j++) {
				if(allChess[i][j] != 0) {
					if(allChess[i][j] > 0) {
						step[allChess[i][j] - 1] = i + "," + j;
					} else {
						step[-1 * allChess[i][j] - 1] = i + "," + j;
					}
				}
			}
		}
		return step;
	}
	
	public void paint(Graphics g) {
		BufferedImage bi = new BufferedImage(1087, 753, BufferedImage.TYPE_INT_RGB);
		Graphics g2 = bi.createGraphics();
		//绘制背景
		try {
			image = ImageIO.read(new File("img/五子棋盘3.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		g2.setColor(Color.black);
		g2.drawImage(image, 0, 0, this);
		for(int i = 0; i < 16; i++) {
			g2.drawLine(188, 40 + i * 43, 833, 40 + i * 43);
			g2.drawLine(188 + i * 43, 40, 188 + i * 43, 685);
		}
		//绘制全部棋子
		for(int i = 0; i < 16; i++) {
			for(int j = 0; j < 16; j ++) {
				if(allChess1[i][j] > 0) {
					//黑子
					int tempX = i * 43 + 188;
					int tempY = j * 43 + 40;
					g2.setColor(Color.black);
					g2.fillOval(tempX - 18, tempY - 18, 36, 36);
				}
				if(allChess1[i][j] < 0) {
					//白子
					int tempX = i * 43 + 188;
					int tempY = j * 43 + 40;
					g2.setColor(Color.white);
					g2.fillOval(tempX - 18, tempY - 18, 36, 36);
				}
			}
		}
		g.drawImage(bi,  0,  0,  this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(now < num) {
			if(now == 0) {
				this.repaint();
				this.timer.setDelay((int) times1[now]);
				showComposition(now);
				now++;
			} else {
				this.repaint();
				this.timer.setDelay((int) (times1[now] - times1[now - 1]));
				showComposition(now);
				now++;
			}
		} else {
			this.timer.stop();
		}
	}
}
