package frame;

import java.awt.Container;

import javax.swing.JFrame;

import elements.Player;

public class ReviewFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	
	static ReviewFrame reviewFrame;
	
	private ReviewFrame(Player player){
		this.setTitle("对局回看");
		this.setSize(1087, 753);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		ChoosePanel choosePanel = new ChoosePanel(player);
		this.getContentPane().add(choosePanel);
		
		this.setVisible(true);
	}
	
	//切换面板
	public void changeContentPane(Container contentPane) {
		this.setContentPane(contentPane);
	}
	
	//单实例
	public static ReviewFrame getReviewFrame(Player player) {
		if(reviewFrame == null) {
			reviewFrame = new ReviewFrame(player) ;
		}
		return reviewFrame;
	}
	
	public static void main(String[] args) {
    	getReviewFrame(null);
    }
}
