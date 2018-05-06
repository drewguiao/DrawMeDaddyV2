import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

class GameServerThread extends Thread implements Constants{
	
	private GameServer gameServer;
	private DatagramSocket serverSocket;
	
	private List<GamePlayer> players = Collections.synchronizedList(new ArrayList<>());
	private List<GamePlayer> playersCache = Collections.synchronizedList(new ArrayList<>());

	private BagOfWords bag = new BagOfWords();
	private int gameStatus = WAITING_FOR_PLAYERS;

	private static final int ROUNDS = 3;
	private static final int STAGE_TIMER = 60; // 60 seconds
	private static final int BEFORE_GUESSING_TIMER = 3; //3 seconds
	private int numberOfRounds = 1;
	private int numberOfStages = 0;

	public GameServerThread(GameServer gameServer){
		this.gameServer = gameServer;
		this.setUpServer();
		new Thread(this);
		this.start();
	}

	private void setUpServer(){
		int portNumber = gameServer.getPortNumber();
		try{
			serverSocket = new DatagramSocket(portNumber);
			serverSocket.setSoTimeout(SOCKET_TIME_OUT);
		}catch(IOException ioe){
			System.out.println("GameServerThread.java.setUpServer(): Could not listen to port: "+portNumber);
		}
	}

	@Override
	public void run(){
		while(true){
			byte[] buffer = new byte[BYTE_MAX_SIZE];
			DatagramPacket packet = new DatagramPacket(buffer,buffer.length);
			try{
				serverSocket.receive(packet);
			}catch(IOException ioe){}
			
			String receivedData = new String(buffer);
			receivedData = receivedData.trim();

			switch(gameStatus){
				case WAITING_FOR_PLAYERS:
					if(receivedData.startsWith(COORDINATE_SIGNAL)) this.broadCastCoordinateData(receivedData);
					else if(receivedData.startsWith(CONNECT_SIGNAL)) this.processRequest(receivedData, packet);
					else if(receivedData.startsWith(WANTS_TO_START_SIGNAL)) this.broadcastRequestToStartGame(receivedData);
				break;
				case PRE_ROUND:

					if(this.numberOfRounds == ROUNDS && this.numberOfStages == this.players.size()){
						this.gameStatus = END_GAME;
					}else{
						if(this.numberOfStages == this.players.size()){
							this.numberOfStages = 0;
							this.numberOfRounds++;
						}else{
							this.numberOfStages++;
							GamePlayer artist = retrieveAPlayer();
							String word = bag.getRandomWord();
							broadcast(ARTIST_SIGNAL + SPACE + artist.getName());
							//Timer timer = new Timer(BEFORE_GUESSING_TIMER);
							//while(timer.getTimeLeft() != 0) broadcast(TIME_SIGNAL+timer.getTimeLeft());
							

							this.gameStatus = ONGOING_STAGE;
							broadcast(ONGOING_STAGE_SIGNAL+ SPACE + this.numberOfRounds + SPACE + this.numberOfStages + SPACE + this.players.size());
							broadcast(WORD_UPDATE_SIGNAL + SPACE + word);
							// Timer timer = new Timer(STAGE_TIMER);
						}
					}

				break;
				case ONGOING_STAGE:
					//while(timer.getTimeLeft() != 0){
					//// int timeLeft = timer.getTimeLeft();
					// broadcast(TIME_SIGNAL+SPACE+timeLeft);
					if(receivedData.startsWith(COORDINATE_SIGNAL)) this.broadCastCoordinateData(receivedData);
					else if(receivedData.startsWith(WORD_CORRECT_SIGNAL)) this.broadcastCorrectPlayer(receivedData);
					//}
					//this.gameStatus = PRE_ROUND;
				break;
				case POST_ROUND:
					// get next player who will be artist
					// set gameStatus to ongoing
					// add a 3 second timer shown to everyone
				break;
				case END_GAME:
					// after 3 rounds
					// get summary of score 
				break;
			}


			// if(receivedData.startsWith(COORDINATE_SIGNAL)) broadCastCoordinateData(receivedData);
			// }else if(receivedData.startsWith(CONNECT_SIGNAL)) processRequest(receivedData);	
			// else if(receivedData.startsWith(WORD_CORRECT_SIGNAL)){
			// 	String[] tokens = receivedData.split(SPACE);
			// 	String playerName = tokens[1];
			// 	broadcast(GOT_THE_WORD_SIGNAL+SPACE+playerName);
			// 	for(GamePlayer player:players){
			// 		if(player.getName().equals(playerName)){
			// 			player.updateScore(10);
			// 			break;
			// 		}
			// 	}
			// 	String scoreList = "";
			// 	for(GamePlayer player:players) scoreList += player + NEW_LINE;
			// 	broadcast(SCORE_LIST_SIGNAL+SPACE+scoreList);
			// 	String word = bag.getRandomWord();
			// 	broadcast(WORD_UPDATE_SIGNAL+SPACE+word);
			// }
		}
	}

	private void broadCastCoordinateData(String receivedData){
		String[] coordinateData = receivedData.split(SPACE);
		String identifier = coordinateData[0];
		int oldX = Integer.parseInt(coordinateData[1]);
		int oldY = Integer.parseInt(coordinateData[2]);
		int newX = Integer.parseInt(coordinateData[3]);
		int newY = Integer.parseInt(coordinateData[4]);
		float brushSize = Float.parseFloat(coordinateData[5]);
		String message = new String(identifier+SPACE+oldX+SPACE+oldY+SPACE+newX+SPACE+newY+SPACE+brushSize);
		this.broadcast(message);
	}

	private void processRequest(String receivedData, DatagramPacket packet){
		String[] tokens = receivedData.split(SPACE);
		String name = tokens[1];
		GamePlayer player = new GamePlayer(name, packet.getAddress(),packet.getPort());
		this.players.add(player);
		this.broadcast(ACKNOWLEDGEMENT_SIGNAL+SPACE+name);
		// String word = bag.getRandomWord();
		// this.broadcast(WORD_UPDATE_SIGNAL+SPACE+word);
	}

	private void broadcastRequestToStartGame(String receivedData){
		String[] tokens = receivedData.split(SPACE);
		String playerName = tokens[1];
		GamePlayer player = findPlayerByName(playerName);
		player.setReadiness(true);
		broadcast(WANTS_TO_START_SIGNAL+SPACE+playerName);
		if(areReady(players)){
			 this.gameStatus = PRE_ROUND;
			 broadcast(PRE_ROUND_SIGNAL);
		}
	}

	//To do: add Timer timer as parameter
	private void broadcastCorrectPlayer(String receivedData){
		String[] tokens = receivedData.split(SPACE);
		String playerName = tokens[1];
		int score = Integer.parseInt(tokens[2]);
		GamePlayer player = findPlayerByName(playerName);
		player.updateScore(score);
		//timer.reduceTime(score);
		broadcast(GOT_THE_WORD_SIGNAL + SPACE + playerName);
	}

	private GamePlayer findPlayerByName(String playerName){
		for(GamePlayer player: players){
			if(player.getName().equals(playerName)) return player;
		}
		return null;
	}

	private boolean areReady(List<GamePlayer> players){
		for(GamePlayer player: players){
			if(!player.isReady()) return false;
		}
		return true;
	}

	private GamePlayer retrieveAPlayer(){
		for(GamePlayer players: players){
			if(!playersCache.contains(players)){
				return players;
			}else{
				playersCache.add(players);
			}
		}
		return null;
	}

	public void send(GamePlayer player, String message){
		byte[] buffer = message.getBytes();
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length, player.getAddress(),player.getPortNumber());
		try{
			serverSocket.send(packet);
		}catch(IOException ioe){
			System.out.println("GameServerThread.java.send(): "+ioe.getMessage());
		}
	}


	public void broadcast(String message){
		for(GamePlayer player: players) this.send(player,message);
	}

}