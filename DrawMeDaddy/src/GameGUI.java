import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.awt.Container;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.AdjustmentEvent;
import java.awt.GridLayout;
import java.awt.BorderLayout;
class GameGUI implements Constants{

	private GameClient gameClient;
	private JFrame gameFrame;
	private Container contentContainer;
	private JPanel chatPanel,scorePanel, drawingPanel;
	private DrawingArea drawingArea;
	private JTextArea chatArea,scoreArea;
	private JScrollPane chatScrollPane;
	private JTextField chatField,wordField,timerField;
	private JButton sendButton;

	private static final int SCORE_AREA_ROWS = 10;
	private static final int SCORE_AREA_COLS = 25;
	private static final int CHAT_AREA_ROWS = 25;
	private static final int CHAT_AREA_COLS = 25;
	private static final int TEXT_FIELD_COLS = 25;
	private static final int GRID_LAYOUT_ROWS = 0;
	private static final int GRID_LAYOUT_COLS = 3;
	private static final int FRAME_X_SIZE = 1000;
	private static final int FRAME_Y_SIZE = 500;
	
	private static final String SEND_STRING = "SEND";
	private static final String GAME_TITLE = "Draw Me Daddy: ";

	public GameGUI(GameClient gameClient){
		this.gameClient = gameClient;
		this.setUpUI();
		this.render();
	}

	private void setUpUI(){
		this.buildScorePanel();
		this.buildDrawingPanel();
		this.buildChatPanel();
		this.buildFrame();
	}

	private void buildScorePanel(){
		this.scorePanel = new JPanel();
		// this.scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.Y_AXIS));
		this.scoreArea = new JTextArea(SCORE_AREA_ROWS,SCORE_AREA_COLS);
		this.scoreArea.setEditable(false);
		this.scoreArea.setText("SCORE AREA");

		this.wordField = new JTextField(TEXT_FIELD_COLS);
		this.wordField.setEnabled(false);
		this.wordField.setText("WORD TO GUESS AREA");

		this.timerField = new JTextField(TEXT_FIELD_COLS);
		this.timerField.setEnabled(false);
		this.timerField.setText("TimerField");

		this.scorePanel.add(scoreArea);
		this.scorePanel.add(wordField);
		this.scorePanel.add(timerField);

	}

	private void buildDrawingPanel(){
		this.drawingPanel = new JPanel();
		this.drawingPanel.setLayout(new BorderLayout());

		// this.controlPanel = new JPanel();
		this.drawingArea = new DrawingArea(this.gameClient);
		
		// this.drawingPanel.add(this.controlPanel, BorderLayout.NORTH);
		this.drawingPanel.add(this.drawingArea, BorderLayout.CENTER);
	}


	private void buildChatPanel(){
		this.chatPanel = new JPanel();
		this.chatArea = new JTextArea(CHAT_AREA_ROWS,CHAT_AREA_COLS);
		this.chatField  = new JTextField(TEXT_FIELD_COLS);
		this.sendButton = new JButton(SEND_STRING);

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

	private void buildFrame(){
		this.gameFrame = new JFrame(GAME_TITLE+this.gameClient.getPlayerName());
		this.contentContainer = gameFrame.getContentPane();
		this.contentContainer.setLayout(new GridLayout(GRID_LAYOUT_ROWS,GRID_LAYOUT_COLS));

		this.contentContainer.add(scorePanel);
		this.contentContainer.add(drawingPanel);
		this.contentContainer.add(chatPanel);
	}

	private void render(){
		this.gameFrame.setSize(FRAME_X_SIZE,FRAME_Y_SIZE);
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

	public void paintOnComponent(int oldX, int oldY, int newX, int newY, float brushSize){
		this.drawingArea.draw(oldX,oldY,newX,newY,brushSize);
	}

	public void showInWordField(String word){
		this.wordField.setText(word);
	}

}
