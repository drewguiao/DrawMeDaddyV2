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
import javax.swing.JTextField;



public class SettingUI{

    private JFrame frame;
    private Container container;
    private JButton clientEnterButton;
    private JButton serverEnterButton;

    private JTextField nameField;
    private JTextField ipField;
    private JTextField clientPortField;
    private JTextField serverPortField;

    private String label;
    
    public SettingUI(String label){
        this.label = label;
        if(this.label == "client"){
            this.promptClientUI();
        }else{
            this.promptServerUI();
        }
        
    }


    private void promptClientUI(){
        this.frame = new JFrame("New Game");
		this.frame.setSize(400, 200);
		this.frame.setResizable(false);
		this.frame.setLocationRelativeTo(null);
        this.frame.setUndecorated(true);

        this.container = this.frame.getContentPane();
        this.container.setLayout(null);
    
        this.clientEnterButton = new JButton();
        this.clientEnterButton.setIcon(new ImageIcon("images/enter.png"));
		this.clientEnterButton.setBounds(115,120,173,56);
		this.clientEnterButton.setBorderPainted(false); 
		this.clientEnterButton.setContentAreaFilled(false); 
		this.clientEnterButton.setFocusPainted(false); 
        this.clientEnterButton.setOpaque(false);
        
        this.nameField = new JTextField();
        this.nameField.setText("Enter name here (e.g Paul)");
        this.nameField.setBounds(100,20,200,20);

        this.ipField = new JTextField();
        this.ipField.setText("Enter IP here (e.g localhost)");
        this.ipField.setBounds(100,47,200,20);

        this.clientPortField = new JTextField();
        this.clientPortField.setText("Enter port here (e.g 1111)");
        this.clientPortField.setBounds(100,74,200,20);

        this.clientEnterButton.addMouseListener(new MouseListener(){
        
            @Override
            public void mouseReleased(MouseEvent e) {
                
            }
        
            @Override
            public void mousePressed(MouseEvent e) {
                
            }
        
            @Override
            public void mouseExited(MouseEvent e) {
                clientEnterButton.setIcon(new ImageIcon("images/enter.png"));
            }
        
            @Override
            public void mouseEntered(MouseEvent e) {
                clientEnterButton.setIcon(new ImageIcon("images/enter_shadow.png"));
            }
        
            @Override
            public void mouseClicked(MouseEvent e) {
                // GameClient gameClient = new GameClient(getName(),getIP(),getPort());
                // gameClient.setUpChat();
                // gameClient.setUpDrawing();
                // gameClient.start();
                DaddyGUI daddyGUI = new DaddyGUI(getName(),  getIP(),  getClientPort());
                frame.setVisible(false);
            }
        });

        this.frame.add(nameField);
        this.frame.add(ipField);
        this.frame.add(clientPortField);
        this.frame.add(clientEnterButton);
        this.frame.setVisible(true);
    }

    private void promptServerUI(){
        JFrame serverFrame = new JFrame();
        

		serverFrame.setSize(400, 200);
		serverFrame.setResizable(false);
		serverFrame.setLocationRelativeTo(null);
        serverFrame.setUndecorated(true);

        Container serverContainer = serverFrame.getContentPane();
        serverContainer.setLayout(null);
    
        this.serverEnterButton = new JButton();
        this.serverEnterButton.setIcon(new ImageIcon("images/enter.png"));
		this.serverEnterButton.setBounds(115,120,173,56);
		this.serverEnterButton.setBorderPainted(false); 
		this.serverEnterButton.setContentAreaFilled(false); 
		this.serverEnterButton.setFocusPainted(false); 
        this.serverEnterButton.setOpaque(false);

        this.serverPortField = new JTextField();
        this.serverPortField.setText("Enter port here (e.g 1111)");
        this.serverPortField.setBounds(100,20,200,20);

        this.serverEnterButton.addMouseListener(new MouseListener(){
            
                @Override
                public void mouseReleased(MouseEvent e) {
                    
                }
            
                @Override
                public void mousePressed(MouseEvent e) {
                    
                }
            
                @Override
                public void mouseExited(MouseEvent e) {
                    serverEnterButton.setIcon(new ImageIcon("images/enter.png"));
                }
            
                @Override
                public void mouseEntered(MouseEvent e) {
                    serverEnterButton.setIcon(new ImageIcon("images/enter_shadow.png"));
                }
            
                @Override
                public void mouseClicked(MouseEvent e) {
                    GameServer gameServer = new GameServer(getServerPort());
                    gameServer.setUpChat();
                    gameServer.start();
                    serverFrame.setVisible(false);
                }
            });

        serverFrame.add(serverEnterButton);
        serverFrame.add(serverPortField);
        serverFrame.setVisible(true);
    }

    private String getName(){
        return this.nameField.getText();
    }

    private String getIP(){
        return this.ipField.getText();
    }

    private int getClientPort(){
        return Integer.parseInt(this.clientPortField.getText());
    }

    private int getServerPort(){
        return Integer.parseInt(this.serverPortField.getText());
    }
}