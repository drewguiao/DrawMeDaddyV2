import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

class GameGUI implements Constants{
	private GameClient gameClient;
	private JFrame gameFrame  = new JFrame(GAME_TITLE);
	private JPanel chatPanel = new JPanel();
	private JTextArea chatArea = new JTextArea(TEXT_AREA_ROWS,TEXT_AREA_COLS);
	private JScrollPane scrollPane = new JScrollPane(this.chatArea);
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
		this.gameFrame.add(this.chatPanel);
	}

	private void buildChatPanel(){
		this.chatArea.setEditable(false);
		this.chatArea.setLineWrap(true);
		// this.chatScrollPane.getVerticalScrollBar().addAdjustmentListener();

		this.chatField.addKeyListener(initializeSendViaEnterListener());
		this.sendButton.addActionListener(initializeSendViaMouseClickListener());

		this.chatPanel.add(this.chatArea);
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

}
