import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer implements Runnable,Constants{
	
	private Thread thread;
	private ServerSocket server;
	private List<ChatServerThread> clientList = Collections.synchronizedList(new ArrayList<>());
	private GameServerThread motherServer;

	private int portNumber;

	public GameServer(int portNumber) {
		this.portNumber = portNumber;	
	}

	public void setUpChat() {
		try {
			System.out.println("Binding to port: "+PORT_NUMBER+". Please wait.");
			server = new ServerSocket(PORT_NUMBER);
			System.out.println("Server started: "+server);
		}catch(Exception e) {
			System.out.println("GameServer.java.setUp(): Cannot bind to port"+PORT_NUMBER+": "+e.getMessage());
		}
	}
	
	public void start() {
		if(thread == null) {
			thread = new Thread(this);
			this.motherServer = new GameServerThread(this);
			thread.start();
		}
	}

	@Override
	public void run() {
		while(thread != null) {
			try {
				System.out.println("Waiting for a client ... ");
				addClient(server.accept());
				System.out.println("Client Accepted!");
			}catch(Exception e) {
				System.out.println("GameServer.java.run(): Server accept error: "+e.getMessage());
			}
		}
	}

	private void addClient(Socket socket) {
		ChatServerThread client = new ChatServerThread(this,socket);
		clientList.add(client);
		try {
			client.open();
			client.start();
		}catch(Exception e) {
			System.out.println("GameServer.java.addClient(): Error opening client thread: "+e.getMessage());
		}
	}

	public void handle(String message) {
		System.out.println(message);
		for(ChatServerThread client: clientList) {
			client.send(message);
		}
	}

	public int getPortNumber(){
		return this.portNumber;
	}
}
