import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Container;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.AdjustmentEvent;

class GameGUI implements Constants{
	private GameClient gameClient;
	private JFrame gameFrame;
	private Container contentContainer;
	private JPanel chatPanel = new JPanel();
	private JTextArea chatArea = new JTextArea(TEXT_AREA_ROWS,TEXT_AREA_COLS);
	private JScrollPane chatScrollPane;
	private JTextField chatField = new JTextField(TEXT_FIELD_COLS);
	private JButton sendButton = new JButton(SEND_STRING);

	public GameGUI(GameClient gameClient){
		this.gameClient = gameClient;
		this.setUpUI();
		this.render();
	}

	private void setUpUI(){
		//this.buildDrawingPanel();
		this.buildChatPanel();
		this.buildFrame();
	}

	private void buildFrame(){
		//this.gameFrame.add(this.drawingPanel);
		this.gameFrame = new JFrame(GAME_TITLE+this.gameClient.getPlayerName());
		this.contentContainer = gameFrame.getContentPane();
		this.contentContainer.add(chatPanel);
	}

	private void buildChatPanel(){

		this.chatArea.setEditable(false);
		this.chatArea.setLineWrap(true);

		this.chatScrollPane = new JScrollPane(this.chatArea);
		this.chatScrollPane.getVerticalScrollBar().addAdjustmentListener(initializeAdjustmentListener());

		this.chatField.addKeyListener(initializeSendViaEnterListener());
		this.sendButton.addActionListener(initializeSendViaMouseClickListener());

		this.chatPanel.add(this.chatScrollPane);
		this.chatPanel.add(this.chatField);
		this.chatPanel.add(this.sendButton);
	}

	private void render(){
		this.gameFrame.setSize(1000,300);
		this.gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.gameFrame.setVisible(true);
	}


	public void sendMessage(){
		String message = chatField.getText();
		this.gameClient.send(message);
		this.chatField.setText(EMPTY_STRING);
	}

	public void showMessage(String message){
		this.chatArea.append(message+NEW_LINE);
	}

	private KeyListener initializeSendViaEnterListener(){
		KeyListener sendViaEnterListener = new KeyListener(){
			@Override
			public void keyPressed(KeyEvent ke){
				if(ke.getKeyCode() == KeyEvent.VK_ENTER){
					sendMessage();
				}
			}			
			@Override
			public void keyTyped(KeyEvent ke){}
			@Override
			public void keyReleased(KeyEvent ke){}

		};
		return sendViaEnterListener;
	}

	private ActionListener initializeSendViaMouseClickListener(){
		ActionListener sendViaMouseClickListener = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae){
				sendMessage();
			}
		};

		return sendViaMouseClickListener;
	}

	private AdjustmentListener initializeAdjustmentListener(){
		AdjustmentListener adjustmentListener = new AdjustmentListener(){
			public void adjustmentValueChanged(AdjustmentEvent e) {  
           		e.getAdjustable().setValue(e.getAdjustable().getMaximum());  
        	}
		};
		return adjustmentListener;
	}

}
