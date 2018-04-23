import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.io.IOException;
import java.util.Iterator;

class GameServerThread extends Thread implements Constants{
	private GameServer gameServer;
	private DatagramSocket serverSocket;

	private static final int SOCKET_TIME_OUT = 100;
	private static final int BYTE_MAX_SIZE = 256;

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
			}catch(IOException ioe){
				System.out.println("GameServerThread.java.run(): "+ioe.getMessage());
			}
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
			}
		}
	}

	public void broadcast(String message){
		System.out.println(message);
	}

}