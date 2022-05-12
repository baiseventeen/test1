package frame;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import elements.Composition;
import elements.Player;
import util.FileUtil;

public class ChoosePanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private JTable table = null;
	private JScrollPane scroll = new JScrollPane();
	private TableColumn column;
	private JButton jbutconfirm = new JButton("确定");
	private Player player;
	
	public ChoosePanel(Player player) {
		this.setLayout(null);
		this.setBounds(0, 0, 1086, 753);
		this.player = player;
		try {
			showComposition(player);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		jbutconfirm.setBounds(423, 569, 100, 50);
		this.add(jbutconfirm);
		
		jbutconfirm.addActionListener((e) -> {
			judge(table);
		});
	}
	
	private void judge(JTable table) {
		int flag = 0;
		int chosen = 0;
		int row = table.getRowCount();//获得行数
		TableModel model = table.getModel();//为获取格子中的信息而创建
		for(int i = 0; i < row; i++) {
			if(table.isRowSelected(i)) {
				chosen++;
			}
		}
		if(chosen > 1) {
			JOptionPane.showMessageDialog(null, "一次只可选择一个棋局进行回看");
			flag = 1;
		}
		if(chosen == 0 && flag == 0) {
			JOptionPane.showMessageDialog(null, "至少选择一个棋局进行回看");
			flag = 1;
		}
		//确认提醒
		if(flag == 0) {
			int y = JOptionPane.showConfirmDialog(null, "确定要看本场比赛回放吗", "确定", 2);
			if(y == 2) {
				flag = 1;
			}
		}
		if(flag == 1) {
		   return;
		}
		for(int i = 0; i < row; i++) {
			if(table.isRowSelected(i)) {
				Composition composition = (Composition) model.getValueAt(i, 3);
				ReviewFrame.getReviewFrame(player).changeContentPane(new ReviewPanel(composition));
			}
		}
	}
	
	private void showComposition(Player player) throws IOException {
		String[] colnames = {"比赛名称", "棋手1", "棋手2", ""};
		Object[][] data = null;
		List<Composition> list = FileUtil.readComposition("Composition.txt");
		List<Composition> list2 = new ArrayList<Composition>();
		if(list.size() > 0) {
			for(int i = 0; i < list.size(); i++) {
				if(list.get(i).getUsername().equals(player.getUsername())) {
					list2.add(list.get(i));
				}
			}
			if(list2.size() > 0) {
				data = new Object[list2.size()][4];
				for(int i = 0; i < list2.size(); i++) {
					Composition composition= list2.get(i);
					data[i][0] = composition.getGamename();
					data[i][1] = composition.getName1();
					data[i][2] = composition.getName2();
					data[i][3] = composition;
				}
			} else {
				data = new Object[1][4];
			}
		} else {
			data = new Object[1][4];
		}
		table = new JTable(data, colnames);
		column = table.getColumnModel().getColumn(0);		
		column.setPreferredWidth(250);
		column = table.getColumnModel().getColumn(3);	
		column.setPreferredWidth(0);
		column.setMaxWidth(0); 
		column.setMinWidth(0); 
		column.setWidth(0);
		table.getTableHeader().getColumnModel().getColumn(3).setMaxWidth(0); 
	    table.getTableHeader().getColumnModel().getColumn(3).setMinWidth(0);
		table.setRowHeight(25);
		JTableHeader head = table.getTableHeader();
		head.setPreferredSize(new Dimension(head.getWidth(), 35));
		head.setFont(new Font("宋体", 0, 20));
		table.setFont(new Font("楷体", 0, 20));
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
	    tcr.setHorizontalAlignment(JLabel.CENTER);
	    table.setDefaultRenderer(Object.class, tcr);
	    table.setOpaque(false);
	    scroll = new JScrollPane(table);
		scroll.setSize(43*16+6, 500);
		scroll.setLocation(166, 40);
		this.add(scroll);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//绘制一张背景图片
		String path = "img/五子棋盘3.png";
		ImageIcon icon = new ImageIcon(path);
		Image image = icon.getImage();
		g.drawImage(image, 0 ,0,this);
	}
}
