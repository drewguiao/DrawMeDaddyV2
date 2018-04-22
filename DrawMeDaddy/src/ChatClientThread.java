import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ChatClientThread extends Thread{
	private Socket socket;
	private GameClient gameClient;
	private DataInputStream console;
	
	
	public ChatClientThread(GameClient gameClient, Socket socket) {
		this.gameClient = gameClient;
		this.socket = socket;
		open();
		start();
	}


	private void open() {
		try {
			console = new DataInputStream(socket.getInputStream());
		}catch(IOException ioe) {
			System.out.println("Error getting input stream: "+ioe.getMessage());
		}
	}
	
	public void run() {
		while(true) {
			try {				
				gameClient.handle(console.readUTF());
			}catch(IOException ioe) {
				System.out.println("Listening error: " + ioe.getMessage());
			}
		}
	}

}
