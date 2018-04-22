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

class DrawingArea extends JComponent{
	private Image image;
	private Graphics2D graphicsObject;
	private GameClient gameClient;
	private int oldX, oldY, newX, newY;
	private static final float DEFAULT_BRUSH_SIZE  = 3.0f;
	private static final int START_X = 0;
	private static final int START_Y = 0;

	public DrawingArea(GameClient gameClient){
		this.gameClient = gameClient;
		this.setDoubleBuffered(false);
		this.addMouseListener(initializeMouseClickListener());
		this.addMouseMotionListener(initializeMouseMotionListener());
		this.setBackground(Color.WHITE);
	}

	private MouseAdapter initializeMouseClickListener(){
		MouseAdapter mouseClickListener = new MouseAdapter(){
			public void mousePressed(MouseEvent me){
				oldX = me.getX();
				oldY = me.getY();
				newX = oldX;
				newY = oldY;
				graphicsObject.setStroke(new BasicStroke(DEFAULT_BRUSH_SIZE,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
				graphicsObject.drawLine(oldX,oldY,oldX,oldY);
				repaint();
			}
		};
		return mouseClickListener;
	}

	private MouseMotionListener initializeMouseMotionListener(){
		MouseMotionListener mouseMotionListener = new MouseMotionListener(){
			@Override
			public void mouseDragged(MouseEvent me){
				newX = me.getX();
				newY = me.getY();
				if(graphicsObject != null){
					graphicsObject.setStroke(new BasicStroke(DEFAULT_BRUSH_SIZE,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
					graphicsObject.drawLine(oldX,oldY,newX,newY);
				}
				repaint();
				oldX = newX;
				oldY = newY;
			}
			@Override
			public void mouseMoved(MouseEvent me){}
		};
		return mouseMotionListener;
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

	private void clear(){
		graphicsObject.setPaint(Color.WHITE);
		graphicsObject.fillRect(START_X,START_Y,this.getSize().width,this.getSize().height);
		graphicsObject.setPaint(Color.BLACK);
		newX = oldX;
		newY = oldY;
		repaint();
	}

}