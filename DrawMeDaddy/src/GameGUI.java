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
import java.awt.Color;
import java.awt.Font;

class GameGUI implements Constants{

	private GameClient gameClient;
	private JFrame gameFrame;
	private Container contentContainer;
	private JPanel chatPanel,scorePanel, drawingPanel, controlPanel;
	private DrawingArea drawingArea;
	private JTextArea chatArea,scoreArea,timerArea,wordArea;
	private JScrollPane chatScrollPane;
	private JTextField chatField,wordField,timerField;
	private JButton sendButton,clearButton;
	private BrushSettings brushSettings;
	

	private static final int SCORE_AREA_ROWS = 10;
	private static final int SCORE_AREA_COLS = 25;
	private static final int CHAT_AREA_ROWS = 25;
	private static final int CHAT_AREA_COLS = 25;
	private static final int TEXT_FIELD_COLS = 25;
	private static final int GRID_LAYOUT_ROWS = 0;
	private static final int GRID_LAYOUT_COLS = 3;
	private static final int FRAME_X_SIZE = 1000;
	private static final int FRAME_Y_SIZE = 500;
	private static final int FONT_SIZE = 50;
	
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

		Font wordFont = new Font("Sans Serif", Font.BOLD, FONT_SIZE/4);
		this.wordField = new JTextField(TEXT_FIELD_COLS);
//		this.wordField.setSize(300,TEXT_FIELD_COLS);
		this.wordField.setEditable(false);
		this.wordField.setFont(wordFont);
		this.wordField.setHorizontalAlignment(JTextField.CENTER);
		this.wordField.setText("Word Field");

		Font timerFont = new Font("Sans Serif",Font.BOLD, FONT_SIZE);

		this.timerField = new JTextField();
		this.timerField.setSize(300,TEXT_FIELD_COLS);
		this.timerField.setEditable(false);
		this.timerField.setFont(timerFont);
		this.timerField.setHorizontalAlignment(JTextField.CENTER);
		this.timerField.setText("Timer Field");

		this.scorePanel.add(scoreArea);
		this.scorePanel.add(wordField);
		this.scorePanel.add(timerField);

	}

	private void buildDrawingPanel(){
		this.brushSettings = new BrushSettings();
		this.drawingPanel = new JPanel();
		this.drawingPanel.setLayout(new BorderLayout());

		this.controlPanel = new JPanel();

		JButton smallBrush = new JButton("S");
		JButton mediumBrush = new JButton("M");
		JButton largeBrush = new JButton("L");

		JButton blackBrush = new JButton(" ");
		blackBrush.setBackground(Color.BLACK);
		JButton redBrush = new JButton(" ");
		redBrush.setBackground(Color.RED);
		JButton blueBrush = new JButton(" ");
		blueBrush.setBackground(Color.BLUE);
		JButton clearBrush = new JButton(" ");
		clearBrush.setBackground(Color.WHITE);
		
		clearButton = new JButton("Clear canvas");
		
		smallBrush.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				brushSettings.setSize(5.0f);
			}
		});
		
		mediumBrush.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				brushSettings.setSize(10.0f);
			}
		});
		
		largeBrush.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				brushSettings.setSize(20.0f);
			}
		});
		
		blackBrush.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				brushSettings.setColor(Color.BLACK);
			}
		});
		
		redBrush.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				brushSettings.setColor(Color.RED);
			}
		});
		
		blueBrush.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				brushSettings.setColor(Color.BLUE);
			}
		});
		
		clearBrush.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				brushSettings.setColor(Color.WHITE);
			}
		});
		
		clearButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				gameClient.sendGameData(CLEAR_CANVAS_SIGNAL);
				drawingArea.clear();
			}
			
		});
		
		this.controlPanel.add(smallBrush);
		this.controlPanel.add(mediumBrush);
		this.controlPanel.add(largeBrush);
		this.controlPanel.add(blackBrush);
		this.controlPanel.add(redBrush);
		this.controlPanel.add(blueBrush);
		this.controlPanel.add(clearBrush);

		this.drawingArea = new DrawingArea(this.gameClient, this.brushSettings);
		
		this.drawingPanel.add(this.controlPanel, BorderLayout.NORTH);
		this.drawingPanel.add(this.drawingArea, BorderLayout.CENTER);
		this.drawingPanel.add(clearButton, BorderLayout.SOUTH);
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
		this.gameFrame.setResizable(false);
		//this.gameFrame.setUndecorated(true);
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

	public void paintOnComponent(int oldX, int oldY, int newX, int newY, float brushSize, Color brushColor){
		this.drawingArea.draw(oldX,oldY,newX,newY,brushSize,brushColor);
	}

	public void showInScoreList(String list){
		this.scoreArea.setText(list);
	}

	public void showTimeInTimerField(String time){
		this.timerField.setText(time);
	}

	public void disableDrawingArea(){
		this.drawingArea.disableDrawing();
	}

	public void enableDrawingArea(){
		this.drawingArea.enableDrawing();
	}

	public void showInWordField(String word) {
		this.wordField.setText(word);
		
	}

	public void clearCanvas() {
		this.drawingArea.clear();
		
	}

	public void enableClearing() {
		clearButton.setEnabled(true);
	}
	
	public void disableClearing() {
		clearButton.setEnabled(false);
	}

}
