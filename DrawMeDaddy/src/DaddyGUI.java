import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
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
	private JButton exitButton;
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
		this.frame.setUndecorated(true);
		
		this.container = this.frame.getContentPane();
		this.container.setLayout(null);
		
		this.screensaver = new JLabel(new ImageIcon("images/screensaver.gif"));
		this.screensaver.setBounds(0,0,800,700);
		
		this.playButton = new JButton();
		this.playButton.setIcon(new ImageIcon("images/playgame.png"));
		this.playButton.setBounds(250,500,300,70);
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
		
		
		this.frame.add(playButton);
		this.frame.add(exitButton);
		this.frame.add(screensaver);
		this.frame.setVisible(true);
		
	}
}
