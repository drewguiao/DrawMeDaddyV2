import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;


public class DaddyGUI {
	private JFrame frame;
	private JLabel screensaver;
	private JButton playButton;
	private JButton howToButton;
	private JButton exitButton;
	private JButton backButton;
	private Container container;
	
	private JFrame mainGameFrame;
	private JPanel chatPanel;
	private JPanel toolbar;
    private JPanel drawPanel;
    
    private String name;
    private String address;
    private int port;
	
	public DaddyGUI(String name, String address, int port) {
        //Frame properties
        this.name = name;
        this.address = address;
        this.port = port;
		this.showMenu();
		
	}
	
	public void showMenu() {
		this.frame = new JFrame("New Game");
		this.frame.setSize(800, 700);
		this.frame.setResizable(false);
		this.frame.setLocationRelativeTo(null);
		this.frame.setUndecorated(false);
		
		this.container = this.frame.getContentPane();
		this.container.setLayout(null);
		
		this.screensaver = new JLabel(new ImageIcon("images/screensaver.gif"));
		this.screensaver.setBounds(0,0,800,700);
		
		this.backButton = new JButton();
		this.backButton.setIcon(new ImageIcon("images/back.png"));
		this.backButton.setBounds(250,500,300,70);
		this.backButton.setBorderPainted(false); 
		this.backButton.setContentAreaFilled(false); 
		this.backButton.setFocusPainted(false); 
		this.backButton.setOpaque(false);
		this.backButton.setVisible(false);
		
		this.backButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				backButton.setIcon(new ImageIcon("images/back_shadow.png"));
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				backButton.setIcon(new ImageIcon("images/back.png"));
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				screensaver.setIcon(new ImageIcon("images/screensaver.gif"));
				backButton.setVisible(false);
				playButton.setVisible(true);
				howToButton.setVisible(true);
				exitButton.setVisible(true);
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		this.playButton = new JButton();
		this.playButton.setIcon(new ImageIcon("images/playgame.png"));
		this.playButton.setBounds(250,420,300,70);
		this.playButton.setBorderPainted(false); 
		this.playButton.setContentAreaFilled(false); 
		this.playButton.setFocusPainted(false); 
		this.playButton.setOpaque(false);
	
		
		this.playButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub
				playButton.setIcon(new ImageIcon("images/playgame_shadow.png"));
				
			}

			@Override
			public void mouseExited(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub
				playButton.setIcon(new ImageIcon("images/playgame.png"));
				
			}

			@Override
			public void mousePressed(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub
				GameClient gameClient = new GameClient(name, address, port);
                gameClient.setUpChat();
                gameClient.setUpDrawing();
                gameClient.start();
                frame.setVisible(false);				
			}

			@Override
			public void mouseReleased(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		this.howToButton = new JButton();
		this.howToButton.setIcon(new ImageIcon("images/howTo.png"));
		this.howToButton.setBounds(250,500,300,70);
		this.howToButton.setBorderPainted(false); 
		this.howToButton.setContentAreaFilled(false); 
		this.howToButton.setFocusPainted(false); 
		this.howToButton.setOpaque(false);
		
		this.howToButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				howToButton.setIcon(new ImageIcon("images/howto_shadow.png"));
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				howToButton.setIcon(new ImageIcon("images/howto.png"));
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				screensaver.setIcon(new ImageIcon("images/instructions.png"));
				exitButton.setVisible(false);
				playButton.setVisible(false);
				howToButton.setVisible(false);
				backButton.setVisible(true);
				
				
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		
		
		this.exitButton = new JButton();
		this.exitButton.setIcon(new ImageIcon("images/exit.png"));
		this.exitButton.setBounds(250,580,300,70);
		this.exitButton.setBorderPainted(false); 
		this.exitButton.setContentAreaFilled(false); 
		this.exitButton.setFocusPainted(false); 
		this.exitButton.setOpaque(false);
		
		this.exitButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub
				exitButton.setIcon(new ImageIcon("images/exit_shadow.png"));
				
			}

			@Override
			public void mouseExited(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub
				exitButton.setIcon(new ImageIcon("images/exit.png"));
				
			}

			@Override
			public void mousePressed(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub
				System.exit(1);
				
			}

			@Override
			public void mouseReleased(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		this.frame.add(backButton);
		this.frame.add(playButton);
		this.frame.add(howToButton);
		this.frame.add(exitButton);
		this.frame.add(screensaver);
		
		this.frame.setVisible(true);
		
	}

}
