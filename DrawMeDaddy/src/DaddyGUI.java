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
	
	public DaddyGUI() {
		//Frame properties
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
				showMainGame();
				
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
	
	public void showMainGame() {
		this.frame.setVisible(false);
		this.mainGameFrame = new JFrame();
		this.mainGameFrame.setSize(800, 700);
		this.mainGameFrame.setResizable(false);
		this.mainGameFrame.setLocationRelativeTo(null);
		this.mainGameFrame.setUndecorated(true);
		
		this.container = this.mainGameFrame.getContentPane();
		this.container.setLayout(null);
		
		this.chatPanel = new JPanel();
		this.chatPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		this.chatPanel.setBounds(600,0,200,800);
		
		this.constructChatPanel(this.chatPanel);
		
		this.drawPanel = new JPanel();
		this.drawPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		this.drawPanel.setBounds(0,0,600,800);
		
		this.constructDrawPanel(this.drawPanel);
		
		this.toolbar = new JPanel();
		this.toolbar.setBorder(BorderFactory.createLineBorder(Color.black));
		this.toolbar.setBounds(50,550,500,100);
		
		this.constructToolbar(this.toolbar);
		
		
		this.mainGameFrame.add(chatPanel);
		this.mainGameFrame.add(toolbar);
		this.mainGameFrame.add(drawPanel);
		
		this.mainGameFrame.setVisible(true);
		
		
	}
	
	public void constructChatPanel(JPanel panel) {
		panel.setLayout(null);
		JTextArea chatArea = new JTextArea();
		chatArea.setRows(150);
		chatArea.setBounds(0,650,150,50);
		chatArea.setLineWrap(true);
		chatArea.setBorder(BorderFactory.createLineBorder(Color.black));
		
		JButton sendChat = new JButton("Send");
		sendChat.setBounds(150,650,50,50);
		panel.add(sendChat);
		panel.add(chatArea);
	}
	
	public void constructDrawPanel(JPanel panel) {
		panel.setLayout(null);
		JLabel drawLabel = new JLabel(new ImageIcon("images/paper.png"));
		drawLabel.setBounds(0,0,600,800);
		
		
		panel.add(drawLabel);
	}
	
	public void constructToolbar(JPanel panel) {
		
		ArrayList<ToolbarButton> colorButtons = new ArrayList<ToolbarButton>();
		ArrayList<ToolbarButton> sizeButtons = new ArrayList<ToolbarButton>();
		
		panel.setLayout(new GridLayout());
		panel.setFocusable(true);
		
		int colorFlag[] = new int[3];
		
		for(int i=0; i<2; i++) colorFlag[i] = 0;
		colorFlag[0] = 1; colorFlag[2] = 1;
		
		int sizeFlag[] = new int[3];
		for(int i=0; i<2; i++) sizeFlag[i] = 0;
		sizeFlag[0] = 1; sizeFlag[2] = 1;
		
		ToolbarUpdater colorUpdater = new ToolbarUpdater(colorButtons, colorFlag);
		ToolbarUpdater sizeUpdater = new ToolbarUpdater(sizeButtons, colorFlag);
		
		ToolbarButton black = new ToolbarButton("images/toolbarblack.png", "images/toolbarblack_shadow.png", "images/toolbarblack_selected.png", 0, colorUpdater);
		ToolbarButton red = new ToolbarButton("images/toolbarred.png", "images/toolbarred_shadow.png", "images/toolbarred_selected.png", 1, colorUpdater);
		ToolbarButton green = new ToolbarButton("images/toolbargreen.png", "images/toolbargreen_shadow.png", "images/toolbargreen_selected.png", 2, colorUpdater);
		
		colorButtons.add(black);
		colorButtons.add(red);
		colorButtons.add(green);
		
		
		ToolbarButton small = new ToolbarButton("images/toolbarsmall.png", "images/toolbarsmall_shadow.png", "images/toolbarsmall_selected.png", 0, sizeUpdater);
		ToolbarButton medium = new ToolbarButton("images/toolbarmedium.png", "images/toolbarmedium_shadow.png", "images/toolbarmedium_selected.png", 1, sizeUpdater);
		ToolbarButton large = new ToolbarButton("images/toolbarlarge.png", "images/toolbarlarge_shadow.png", "images/toolbarlarge_selected.png", 2, sizeUpdater);
		ToolbarButton delete = new ToolbarButton("images/toolbareraser.png", "images/toolbareraser_shadow.png", "images/toolbareraser_selected.png", 3, null);
		
		sizeButtons.add(small);
		sizeButtons.add(medium);
		sizeButtons.add(large);
		
		black.setState(colorFlag);
		red.setState(colorFlag);
		green.setState(colorFlag);
		
		small.setState(colorFlag);
		medium.setState(colorFlag);
		large.setState(colorFlag);
		
		panel.add(black);
		panel.add(red);
		panel.add(green);
		panel.add(small);
		panel.add(medium);
		panel.add(large);
		panel.add(delete);
	}
	
	
	
	
	
	
}
