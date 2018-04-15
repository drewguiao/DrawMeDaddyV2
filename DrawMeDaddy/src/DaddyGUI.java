import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JFrame;

public class DaddyGUI {
	private JFrame frame;
	private Container container;
	
	public DaddyGUI() {
		//Frame properties
		this.frame = new JFrame("New Game");
		this.frame.setSize(800, 700);
		this.frame.setResizable(false);
		this.frame.setLocationRelativeTo(null);
		
		this.container = frame.getContentPane();
		this.container.setLayout(new GridLayout(0,3));
		
	}
	
	public void render() {
		this.frame.setVisible(true);
	}
}
