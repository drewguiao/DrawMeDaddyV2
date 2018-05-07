import javax.swing.JComponent;
import javax.swing.UIManager;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

class DrawingArea extends JComponent implements Constants{
	private Image image;
	private Graphics2D graphicsObject;
	private GameClient gameClient;
	private BrushSettings brushSettings;

	private int oldX, oldY, newX, newY;

	private boolean allowedToDraw = true;

	private static final float DEFAULT_BRUSH_SIZE  = 5.0f;
	private static final int START_X = 0;
	private static final int START_Y = 0;

	public DrawingArea(GameClient gameClient, BrushSettings brushSettings){
		this.gameClient = gameClient;
		this.brushSettings = brushSettings;
		this.setDoubleBuffered(false);
		this.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent me){
				
				oldX = me.getX();
				oldY = me.getY();
				newX = oldX;
				newY = oldY;
				if(allowedToDraw) {
					String message = COORDINATE_SIGNAL_A+SPACE+oldX+SPACE+oldY+SPACE+newX+SPACE+newY+SPACE+brushSettings.getSize()+SPACE+brushSettings.getColor().toString();
					gameClient.sendGameData(message);
					graphicsObject.setPaint(brushSettings.getColor());
					graphicsObject.setStroke(new BasicStroke(brushSettings.getSize(),BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
					graphicsObject.drawLine(oldX,oldY,oldX,oldY);
					repaint();
				}
				
			}
		});
		this.addMouseMotionListener(new MouseMotionListener(){
			@Override
			public void mouseDragged(MouseEvent me){
				newX = me.getX();
				newY = me.getY();
				if(graphicsObject != null){
					if(allowedToDraw) {
						String message = COORDINATE_SIGNAL_B+SPACE+oldX+SPACE+oldY+SPACE+newX+SPACE+newY+SPACE+brushSettings.getSize()+SPACE+brushSettings.getColor().toString();
						gameClient.sendGameData(message);
						graphicsObject.setPaint(brushSettings.getColor());
						graphicsObject.setStroke(new BasicStroke(brushSettings.getSize(),BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
						graphicsObject.drawLine(oldX,oldY,newX,newY);	
					}
					
				}
				repaint();
				oldX = newX;
				oldY = newY;				
			}
			@Override
			public void mouseMoved(MouseEvent me){}

		});
		// this.setBackground(Color.WHITE);
	}


	protected void paintComponent(Graphics g){
		if(image == null){
			image = createImage(getSize().width,getSize().height);
			graphicsObject = (Graphics2D) image.getGraphics();
			graphicsObject.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			this.clear();
		}
		g.drawImage(image, START_X, START_Y, null);
	}

	public void clear(){
		graphicsObject.setPaint(Color.WHITE);
		graphicsObject.fillRect(START_X,START_Y,this.getSize().width,this.getSize().height);
		graphicsObject.setPaint(brushSettings.getColor());
		newX = oldX;
		newY = oldY;
		repaint();
	}

	public void draw(int oldX, int oldY, int newX, int newY, float brushSize, Color brushColor){
		graphicsObject.setPaint(brushColor);
		graphicsObject.setStroke(new BasicStroke(brushSize,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
		this.graphicsObject.drawLine(oldX,oldY,newX,newY);
		this.repaint();
	}

	public void disableDrawing(){
		this.allowedToDraw = false;
	}

	public void enableDrawing(){
		this.allowedToDraw = true;
	}

}