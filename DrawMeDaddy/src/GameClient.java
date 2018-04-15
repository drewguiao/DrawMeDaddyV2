import java.awt.BasicStroke;

public class GameClient implements Runnable{

	private DaddyGUI gui;
	
	public GameClient() {
		this.run();
	}
	
	public void run() {
		gui = new DaddyGUI();
	}
}
