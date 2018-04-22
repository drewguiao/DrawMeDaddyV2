import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class GameClient implements Runnable,Constants{
	private Socket socket;
	private Thread thread;
	private DataInputStream console;
	private DataOutputStream streamOut;
	private ChatClientThread client;
	
	private String playerName;
	private String serverAddress;
	
	private int portNumber;
	
	private GameGUI gui;
	
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

	public String getPlayerName(){
		return this.playerName;
	}

	
}
