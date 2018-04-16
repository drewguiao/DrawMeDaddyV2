import java.util.ArrayList;

import javax.swing.ImageIcon;

public class ToolbarUpdater {

	private ArrayList<ToolbarButton> buttons;
	private int a[];
	
	public ToolbarUpdater(ArrayList<ToolbarButton> buttons, int a[]) {
		this.buttons = buttons;
		this.a = a;
	}
	
	public void updateButtons() {
		for(int i=0; i<buttons.size(); i++) {
			if(this.a[i] == 1) {
				this.buttons.get(i).setIcon(new ImageIcon(buttons.get(i).selected));
			}else {
				this.buttons.get(i).setIcon(new ImageIcon(buttons.get(i).start));
			}
		}
	}
}
