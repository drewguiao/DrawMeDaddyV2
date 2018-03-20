import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ChatClientThread extends Thread{
	private Socket socket;
	private ChatClient chatClient;
	private DataInputStream console;
	
	
	public ChatClientThread(ChatClient chatClient, Socket socket) {
		this.chatClient = chatClient;
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
				chatClient.handle(console.readUTF());
			}catch(IOException ioe) {
				System.out.println("Listening error: " + ioe.getMessage());
			}
		}
	}

}
