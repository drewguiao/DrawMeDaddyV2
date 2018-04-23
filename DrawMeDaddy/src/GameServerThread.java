import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

class GameServerThread extends Thread implements Constants{
	private GameServer gameServer;
	private DatagramSocket serverSocket;
	private List<GamePlayer> players = Collections.synchronizedList(new ArrayList<>());

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

			if(receivedData.startsWith(COORDINATE_SIGNAL)){
				String[] coordinateData = receivedData.split(SPACE);
				String identifier = coordinateData[0];
				int oldX = Integer.parseInt(coordinateData[1]);
				int oldY = Integer.parseInt(coordinateData[2]);
				int newX = Integer.parseInt(coordinateData[3]);
				int newY = Integer.parseInt(coordinateData[4]);
				float brushSize = Float.parseFloat(coordinateData[5]);
				String message = new String(identifier+SPACE+oldX+SPACE+oldY+SPACE+newX+SPACE+newY+SPACE+brushSize);
				this.broadcast(message);
			}else if(receivedData.startsWith(CONNECT_SIGNAL)){
				String[] tokens = receivedData.split(SPACE);
				String name = tokens[1];
				GamePlayer player = new GamePlayer(name, packet.getAddress(),packet.getPort());
				players.add(player);
				broadcast(ACKNOWLEDGEMENT_SIGNAL+SPACE+name);
			}
		}
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