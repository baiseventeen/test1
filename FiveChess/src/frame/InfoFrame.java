package frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import elements.Composition;
import elements.Player;

public class InfoFrame extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	private JLabel jlabname1 = new JLabel("棋手1 姓名:");
	private JLabel jlabname2 = new JLabel("棋手2 姓名:");
	private JTextField jtextname1 = new JTextField();
	private JTextField jtextname2 = new JTextField();
	private JLabel jlabgamename = new JLabel("比赛名称:");
	private JTextField jtextgamename = new JTextField();
	private JButton jbutconfirm = new JButton("确认");
	private Font font1 = new Font("楷体", Font.BOLD, 30);
	
	public InfoFrame(Player player) {
		this.setTitle("比赛信息录入");
		this.setSize(620, 430);
		this.setVisible(true);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		init();
		
		InfoPanel panel = new InfoPanel();
		panel.setLayout(null);
		panel.add(jlabname1);
		panel.add(jlabname2);
		panel.add(jtextname1);
		panel.add(jtextname2);
		panel.add(jlabgamename);
		panel.add(jtextgamename);
		panel.add(jbutconfirm);
//		panel.add(jbut_return);
		this.getContentPane().add(panel);
		
		jbutconfirm.addActionListener((e) -> {
			if(jtextname1.getText() != null && !jtextname1.getText().equals("") &&
			   jtextname2.getText() != null && !jtextname2.getText().equals("") &&
			   jtextgamename.getText() != null && !jtextgamename.getText().equals("")) {
				Composition composition = new Composition();
				composition.setName1(jtextname1.getText());
				composition.setName2(jtextname2.getText());
				composition.setGamename(jtextgamename.getText());
				composition.setUsername(player.getUsername());
				ArrayList<Composition> list = player.getCompositionList();
				list.add(composition);
				player.setCompositionList(list);
				new ChessFrame(player, composition);
				this.dispose();
			} else {
				JOptionPane.showMessageDialog(null, "请将信息填写完整！");

			}
		});
	}
	
	private void init() {
		int x = 20;
		jlabname1.setBounds(65+x, 10, 180, 70);
		jlabname1.setFont(font1);
		jlabname1.setForeground(Color.white);
		jlabname2.setBounds(65+x, 60, 180, 70);
		jlabname2.setFont(font1);
		jlabname2.setForeground(Color.white);
		jlabgamename.setBounds(65+x, 110, 180, 70);
		jlabgamename.setFont(font1);
		jlabgamename.setForeground(Color.white);
		jtextname1.setBounds(260+x, 27, 200, 35);
		jtextname2.setBounds(260+x, 27+50, 200, 35);
		jtextgamename.setBounds(260+x, 27+100, 200, 35);
		jbutconfirm.setBounds(220+x, 27+160, 140, 40);
		jbutconfirm.setFont(font1);
	}
}

class InfoPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		String path = "img/Gobang.png";
		ImageIcon icon = new ImageIcon(path);
		Image image = icon.getImage();
		g.drawImage(image, 0 ,0,this);
	}
}
