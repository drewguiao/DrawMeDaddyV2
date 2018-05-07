import java.util.Scanner;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

public class GameClient implements Runnable,Constants{
	private Socket socket;
	private Thread thread;
	private DataInputStream console;
	private DataOutputStream streamOut;
	private DatagramSocket datagramSocket;
	private ChatClientThread client;
	private GameGUI gui;
	
	private String playerName;
	private String serverAddress;
	private String wordToGuess;
	private int portNumber;
	private boolean gameConnected = false;

	private int gameStatus = WAITING_FOR_PLAYERS;
	private boolean isArtist = true;
	private boolean hasTheAnswer = false;

	private static final int TIME_LEFT_PLACEHOLDER = 10;
	public GameClient(String name, String serverAddress, int portNumber) {
		this.playerName = name;
		this.serverAddress = serverAddress;
		this.portNumber = portNumber;
	}
	
	public void setUpChat() {
		System.out.println("Establishing connection...");
		try {
			socket = new Socket(serverAddress,portNumber);
			System.out.println("Connected!" +socket);
		}catch(UnknownHostException  uhe) {
			System.out.println("GameClient.java.setUpChat(): Host unknown: "+uhe.getMessage());
		} catch (IOException ioe) {
			System.out.println("GameClient.java.setUpChat(): Unexpected exception: "+ioe.getMessage());
		}
	}
	
	public void setUpDrawing(){
		System.out.println("Establishing drawing connection...");
		try{
			this.datagramSocket = new DatagramSocket();
			this.datagramSocket.setSoTimeout(SOCKET_TIME_OUT);
			System.out.println("Drawing Connection establised!");
		}catch(SocketException se){
			System.out.println("GameClient.java.setUpDrawing(): "+se.getMessage());
		}
	}

	
	public void start() {
		try {
			streamOut = new DataOutputStream(socket.getOutputStream());
			if(thread == null) {
				client = new ChatClientThread(this,socket);
				thread = new Thread(this);
				thread.start();
			}
		}catch(IOException ioe) {
			System.out.println("GameClient.java.start(): "+ioe.getMessage());
		}
		
	}

	//GAME RUNNABLE
	@Override
	public void run(){
		this.gui = new GameGUI(this);
		while(true){
			byte[] buffer = new byte[BYTE_MAX_SIZE];
			DatagramPacket packet = new DatagramPacket(buffer,buffer.length);
			try{
				datagramSocket.receive(packet);
			}catch(IOException ioe){}

			String receivedData = new String(buffer);
			receivedData = receivedData.trim();

			if(receivedData.startsWith(ACKNOWLEDGEMENT_SIGNAL)){
				String[] tokens = receivedData.split(SPACE);
				String name = tokens[1];
				this.gameConnected = true;
				this.handle(SERVER_PREFIX + name + " has connected!");
			}else if(!gameConnected){
				this.sendGameData(CONNECT_SIGNAL+SPACE+this.playerName);
				// this.gameConnected = true;
			}else if(gameConnected){
				if(receivedData.startsWith(TIME_SIGNAL)) translateTimeData(receivedData);
				else if(receivedData.startsWith(START_GUESSING_SIGNAL)) this.handle(SERVER_PREFIX+" start guessing!");
				else if(receivedData.startsWith(STAGE_TIME_SIGNAL)) translateTimeStatgeData(receivedData);
				else if(receivedData.startsWith(COORDINATE_SIGNAL_A)) translateCoordinateData(receivedData);
				else if(receivedData.startsWith(COORDINATE_SIGNAL_B)) translateCoordinateData(receivedData);
				else if(receivedData.startsWith(WORD_UPDATE_SIGNAL)){
					String[] tokens = receivedData.split(SPACE);
					this.wordToGuess = tokens[1];
					this.updateWordInGUI(wordToGuess);
				}
				else if(receivedData.startsWith(SCORE_LIST_SIGNAL)){
					receivedData = receivedData.replace(SCORE_LIST_SIGNAL,EMPTY_STRING);
					this.updateScoreList(receivedData);
				}else if(receivedData.startsWith(GOT_THE_WORD_SIGNAL)){
					this.notifyEveryoneOfPlayerWhoGotTheWord(receivedData);
				}else if(receivedData.startsWith(WANTS_TO_START_SIGNAL)){
					this.notifyEveryoneOfPlayerWhoWantsToStart(receivedData);
				}else if(receivedData.startsWith(PRE_ROUND_SIGNAL)){
					this.notifyEveryoneThatGameIsStarting();
				}else if(receivedData.startsWith(ONGOING_STAGE_SIGNAL)){
					this.notifyEveryoneThatGameHasStarted(receivedData);
				}else if(receivedData.startsWith(ARTIST_SIGNAL)){
					this.translateArtistData(receivedData);
				}
			}

		}
	}

	private void updateScoreList(String scoreList){
		this.gui.showInScoreList(scoreList);
	}
	
	private void updateWordInGUI(String word){
		this.gui.showInWordField(word);
	}

	private void notifyEveryoneOfPlayerWhoGotTheWord(String receivedData){
		String[] tokens = receivedData.split(SPACE);
		String playerName = tokens[1];
		this.handle(SERVER_PREFIX+playerName+" got the correct answer!");
	}

	private void notifyEveryoneOfPlayerWhoWantsToStart(String receivedData){
		String[] tokens = receivedData.split(SPACE);
		String playerName = tokens[1];
		this.handle(SERVER_PREFIX+playerName+" wants to start!");
	}

	private void notifyEveryoneThatGameIsStarting(){
		this.handle(SERVER_PREFIX+" Game is starting!");
		this.gameStatus = PRE_ROUND;
	}

	private void notifyEveryoneThatGameHasStarted(String receivedData){
		String[] tokens = receivedData.split(SPACE);
		int currentRound = Integer.parseInt(tokens[1]);
		int currentStage = Integer.parseInt(tokens[2]);
		int totalStages = Integer.parseInt(tokens[3]);
		this.handle(SERVER_PREFIX+" Round: "+currentRound+"/3 Stage: "+currentStage+"/"+totalStages);
		this.hasTheAnswer = false;
		this.gameStatus = ONGOING_STAGE;
	}

	private void translateArtistData(String receivedData){
		String[] tokens = receivedData.split(SPACE);
		String artistName = tokens[1];
		this.handle(SERVER_PREFIX+artistName+" is the artist!");
		System.out.println("PLAYER: "+this.playerName);
		System.out.println("ARTIST: "+artistName);
		if(this.playerName.equals(artistName)){
			this.gui.enableDrawingArea();
			this.isArtist = true;
		}else{
			this.gui.disableDrawingArea();
			this.isArtist = false;
		}
	}


	private void translateTimeData(String receivedData){
		String[] tokens = receivedData.split(SPACE);
		String remainingTime = tokens[1];
		this.gui.showTimeInTimerField(remainingTime);
		this.handle(SERVER_PREFIX+" stage starting in "+remainingTime+"...");
	}

	private void translateTimeStatgeData(String receivedData){
		String[] tokens = receivedData.split(SPACE);
		String remainingTime = tokens[1];
		this.gui.showTimeInTimerField(remainingTime);
	}

	private void translateCoordinateData(String receivedData){
		String[] coordinateInfo = receivedData.split(SPACE);
		int oldX = Integer.parseInt(coordinateInfo[1]);
		int oldY = Integer.parseInt(coordinateInfo[2]);
		int newX = Integer.parseInt(coordinateInfo[3]);
		int newY = Integer.parseInt(coordinateInfo[4]);
		float brushSize = Float.parseFloat(coordinateInfo[5]);
		this.gui.paintOnComponent(oldX,oldY,newX,newY,brushSize);
	}


	//show message
	public void handle(String message) {
		this.gui.showMessage(message);
	}

	public void send(String message){
		try{
			if(this.gameStatus == ONGOING_STAGE && isMessageTheWord(message) && !isArtist && !hasTheAnswer){
				sendGameData(WORD_CORRECT_SIGNAL + SPACE + this.playerName + SPACE  + TIME_LEFT_PLACEHOLDER);
				this.hasTheAnswer = true;
			}else if(this.gameStatus == ONGOING_STAGE && isMessageTheWord(message) && !isArtist && hasTheAnswer){
				//does nothing
			}else if(this.gameStatus == ONGOING_STAGE && isMessageTheWord(message) && isArtist){
				//does nothing
			}else if(this.gameStatus == WAITING_FOR_PLAYERS && isMessageRequestToStart(message)){
				sendGameData(WANTS_TO_START_SIGNAL + SPACE + this.playerName);
			}else{
				streamOut.writeUTF(this.playerName+": "+message);
				streamOut.flush();
			}
		}catch(IOException ioe){
			System.out.println("GameClient.java.send():"+ioe.getMessage());
		}
	}

	private boolean isMessageTheWord(String message){
		return message.toUpperCase().equals(this.wordToGuess);
	}

	private boolean isMessageRequestToStart(String message){
		return message.toUpperCase().equals(START_SIGNAL);
	}

	public void sendGameData(String message){
		try{
			byte[] buffer = message.getBytes();
			InetAddress address = InetAddress.getByName(this.serverAddress);
			DatagramPacket packet = packet = new DatagramPacket(buffer,buffer.length,address,this.portNumber);
			datagramSocket.send(packet);
		}catch(IOException ioe){
			System.out.println("GameClient.java.sendGameData(): "+ioe.getMessage());
		}
	}

	public String getPlayerName(){
		return this.playerName;
	}

	
}
