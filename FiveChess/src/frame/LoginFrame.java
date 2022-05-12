package frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import elements.Player;
import util.FileUtil;

public class LoginFrame extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private JLabel jlabusername = new JLabel("账号");
	private JLabel jlabpassword = new JLabel("密码");
	private JTextField jtextusername = new JTextField();
	private JPasswordField jtextpassword = new JPasswordField();
	private JButton jbutlogin = new JButton("登录");
	private JButton jbutvisitor = new JButton("游客登录");
	private JButton jbutregister = new JButton("注册账号");
	private Font font1 = new Font("楷体", 0, 30);
	private Font font2 = new Font("宋体", 0, 20);
	
    public LoginFrame() {
    	this.setTitle("欢乐五子棋");
		this.setSize(620, 430);
		this.setVisible(true);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setForeground(new Color(240, 246, 45));
		
		init();
		
		LoginPanel panel = new LoginPanel();
		panel.setLayout(null);
		panel.add(jlabusername);
		panel.add(jlabpassword);
		panel.add(jtextusername);
		panel.add(jtextpassword);
		panel.add(jbutlogin);
		panel.add(jbutvisitor);
		panel.add(jbutregister);
		this.getContentPane().add(panel);
		//登录按钮
		jbutlogin.addActionListener((e) -> {
			int flag = 0;
			String username = jtextusername.getText();
			String password = new String(jtextpassword.getPassword());
			if(username == null || username.equals("") && flag == 0){
				JOptionPane.showMessageDialog(null, "用户名不能为空");
				jtextusername.requestFocus();
				flag = 1;
			}
			if(password == null || password.equals("") && flag == 0){
				JOptionPane.showMessageDialog(null, "密码不能为空");
				jtextpassword.requestFocus();
				flag = 1;
			}
			if(flag == 1) {
				return;
			}
			Player player = null;
			try {
				player = checkPlayer(username, password);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(player != null) {
				new InfoFrame(player);
				this.dispose();
			} else {
				JOptionPane.showMessageDialog(null, "账号或密码错误");
			}
		});
		//游客登陆按钮
		jbutvisitor.addActionListener((e) -> {
			new ChessFrame(null, null);
			this.dispose();
		});
		//注册按钮
		jbutregister.addActionListener((e) -> {
			int flag = 0;
			String username = jtextusername.getText();
			String password = new String(jtextpassword.getPassword());
			if(username == null || username.equals("")){
				JOptionPane.showMessageDialog(null, "用户名不能为空");
				jtextusername.requestFocus();
				flag = 1;
			}
			if(username.indexOf(" ") >= 0 && flag == 0) {
				JOptionPane.showMessageDialog(null, "用户名不能包含空格");
				jtextusername.requestFocus();
				flag = 1;
			}
			if(password == null || password.equals("") && flag == 0){
				JOptionPane.showMessageDialog(null, "密码不能为空");
				jtextpassword.requestFocus();
				flag = 1;
			}
			if(password.indexOf(" ") >= 0 && flag == 0) {
				JOptionPane.showMessageDialog(null, "密码不能包含空格");
				jtextusername.requestFocus();
				flag = 1;
			}
			if(flag == 1) {
				return;
			}
			String str = "";
			boolean haveExisted = false;
			try {
				haveExisted = haveExisted(username);
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			if(!haveExisted) {
				str = "username:" + username + ":" + "password:" + password;
				try {
					FileUtil.writeData(str, "Player.txt", true);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, "注册成功！");
			} else {
				JOptionPane.showMessageDialog(null, "该账号已存在！");
			}
		});
    }
    
    private int x1 = 165;
    private int y1 = 70;
    private int x2 = 235;
    private int y2 = 120;
    //界面初始化
    private void init() {
    	jlabusername.setBounds(x1, y1, 70, 70);
    	jlabusername.setFont(font1);
    	jlabpassword.setBounds(x1, y2, 70, 70);
    	jlabpassword.setFont(font1);
    	jtextusername.setBounds(x2, y1+14, 200, 40);
    	jtextpassword.setBounds(x2, y2+14, 200, 40);
    	jbutlogin.setBounds(x1, 195, 270, 40);
    	jbutlogin.setFont(font1);
    	jbutvisitor.setBounds(x1+280, 195, 158, 40);
    	jbutvisitor.setFont(font1);
    	jbutregister.setBounds(x1+300, 20, 130, 50);
    	jbutregister.setFont(font2);
    	jbutregister.setForeground(Color.white);
    	jbutregister.setContentAreaFilled(false);
    }
    //判断账号是否已存在
    private boolean haveExisted(String username) throws IOException {
    	List<Player> list = FileUtil.readPlayer("Player.txt");
    	Player player;
    	if(list.size() > 0) {
    		for(int i = 0; i < list.size(); i++) {
    			player = (Player) list.get(i);
    			if(username.equals(player.getUsername())) {
    				return true;
    			}
    		}
    	}
    	return false;
    }
    //登录核对
    private Player checkPlayer(String username, String password) throws IOException {
    	List<Player> list = FileUtil.readPlayer("Player.txt");
    	Player player;                             
    	if(list.size() > 0) {
    		for(int i = 0; i < list.size(); i++) {
    			player = (Player) list.get(i);
    			if(username.equals(player.getUsername()) && password.equals(player.getPassword())) {
    				return player;
    			}
    		}
    	}
    	return null;
    }
    public static void main(String[] args) {
    	try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }catch(Exception e) {}
    	new LoginFrame();
    }
}

class LoginPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		String path = "img/Gobang.png";
		ImageIcon icon = new ImageIcon(path);
		Image image = icon.getImage();
		g.drawImage(image, 0 ,0,this);
	}
}
