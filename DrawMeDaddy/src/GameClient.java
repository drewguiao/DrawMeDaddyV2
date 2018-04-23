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
	private ChatClientThread client;
	private GameGUI gui;
	
	private String playerName;
	private String serverAddress;
	
	private int portNumber;
	
	private DatagramSocket datagramSocket;

	
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
			// sendGameData(CONNECT_SIGNAL+SPACE+this.playerName);
			sendGameData(EMPTY_STRING);
		}
	}
	

	//show message
	public void handle(String message) {
		this.gui.showMessage(message);
	}

	public void send(String message){
		try{
			streamOut.writeUTF(this.playerName+": "+message);
			streamOut.flush();
		}catch(IOException ioe){
			System.out.println("GameClient.java.send():"+ioe.getMessage());
		}
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
