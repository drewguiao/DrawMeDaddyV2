import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ToolbarButton extends JButton{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String start;
	public String shadowed;
	public String selected;
	public int mark;
	private ToolbarUpdater updater;
	
	public ToolbarButton(String start, String shadowed, String selected, int mark, ToolbarUpdater updater) {
		this.start = start;
		this.shadowed = shadowed;
		this.selected = selected;
		this.mark = mark;
		this.updater = updater;
		
		if(this.mark == 0) {
			this.setIcon(new ImageIcon(selected));
		}else {
			this.setIcon(new ImageIcon(start));
		}
		
		this.setBorderPainted(false); 
		this.setContentAreaFilled(false); 
		this.setFocusPainted(false); 
		this.setOpaque(false);
	}
	
	public void setState(int a[]) {
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub
				
				if(a[mark] == 1) {
					//Do nothing
				}else {
					for(int i=0; i<3; i++) a[i] = 0;
					a[mark] = 1;
					updater.updateButtons();
					
				}
			}

			@Override
			public void mouseEntered(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub
				if(a[mark] == 1) {
					//Do nothing
				}else {
					setIcon(new ImageIcon(shadowed));
				}
				
			}

			@Override
			public void mouseExited(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub
				if(a[mark] == 1) {
					//Do nothing
				}else {
					setIcon(new ImageIcon(start));
				}
				
			}

			@Override
			public void mousePressed(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	
}
