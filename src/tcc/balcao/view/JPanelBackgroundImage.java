package tcc.balcao.view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;

import javax.swing.JPanel;

public class JPanelBackgroundImage extends JPanel {

	private static final long serialVersionUID = -4794351490165151503L;
	public Image img;
	public int x;
	public int y;
	public JPanelBackgroundImage(Image img, LayoutManager layout, int x, int y) {
		this.img = img;
		this.x = x;
		this.y = y;
		setLayout(layout);
	}
	
	
	public void paintComponent(Graphics g){
		 super.paintComponent(g);
         g.drawImage(img, x, y, this);
	}
}
