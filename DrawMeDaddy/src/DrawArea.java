import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JComponent;

public class DrawArea extends JComponent{

	private Image image;
	private Graphics2D graphicsObject;
	private float currBrush = 3.0f;
	
	private float brushSize = 15.0f;
	private Color brushColor = Color.black;
	private Boolean playing = true;
	
	private ArrayList<Integer> oldX;
	private ArrayList<Integer> oldY;
	private ArrayList<Integer> newX;
	private ArrayList<Integer> newY;
	
	public DrawArea() {
		System.out.println("Creating DrawArea");
		setDoubleBuffered(false);
		this.setFocusable(true);
		
		this.oldX = new ArrayList<Integer>();
		this.oldY = new ArrayList<Integer>();
		this.newX = new ArrayList<Integer>();
		this.newY = new ArrayList<Integer>();

		
	
		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
			
				
				oldX.add(e.getX());
				oldY.add(e.getY());
				newX.add(e.getX());
				newY.add(e.getY());
				
				repaint();
				
			}
			
		});
		
		this.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				
				//System.out.println("X: " +oldX.get(oldX.size()-1)+ " Y: " +oldY.get(oldY.size()-1));
				
				newX.add(e.getX()); 
				newY.add(e.getY());
				oldX.add(e.getX());
				oldY.add(e.getY());
				
				//repaint();
				
				//Kunin mo yung nasa pagitan ng dalawang points
				for(int i=oldX.get(oldX.size()-1); i<=e.getX(); i++) {
					System.out.println("Adding X: " +i);
					oldX.add(i);
					newX.add(i);
				}
				
				
				
				for(int i=oldY.get(oldY.size()-1); i<=e.getY(); i++) {
					System.out.println("Adding Y: ");
					oldY.add(i);
					newY.add(i);
				}
				
				
				repaint();
				
				
				
				
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			
		});
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setStroke(new BasicStroke(brushSize,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
		for(int i=0; i<oldX.size(); i++) {
			
			g2.drawLine(oldX.get(i), oldY.get(i), newX.get(i), newY.get(i));
		}
		
//		for(int i=0; i<50; i++) {
//			g2.drawLine(i, i, i, i);
//		}
	}
	
	public void clear() {
		
	}
	
	public Graphics2D getGraphicsObject(){
		return this.graphicsObject;
	}
}
