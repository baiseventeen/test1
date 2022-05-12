package frame;
import javax.swing.JFrame;

public class MainFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private static MainFrame mainFrame;
	
	private MainFrame() {
		this.setTitle("欢乐五子棋");
		this.setSize(800, 800);
		this.setVisible(true);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
	}
	
	public static MainFrame getMainFrame() {
		if(mainFrame == null) {
			mainFrame = new MainFrame();
		}
		return mainFrame;
	}
}
