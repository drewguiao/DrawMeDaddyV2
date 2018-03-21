import java.util.List;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer implements Runnable,Constants{
	
	private ServerSocket server;
	private List<ChatServerThread> clientList = new ArrayList<>();
	private Thread thread;
	private int portNumber;
	
	
	public ChatServer(int portNumber) {
		this.portNumber = portNumber;
	}

	public void setUp() {
		try {
			System.out.println("Binding to port: "+portNumber+". Please wait.");
			server = new ServerSocket(portNumber);
			System.out.println("Server started: "+server);
		}catch(Exception e) {
			System.out.println("Cannot bind to port"+portNumber+": "+e.getMessage());
		}
	}
	
	public void start() {
		if(thread == null) {
			thread = new Thread(this);
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
				System.out.println("Server accept error: "+e.getMessage());
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
			System.out.println("Error opening client thread: "+e.getMessage());
		}
	}
	
	private void removeClient(String ipAddress, int ID) {
		for(ChatServerThread client : clientList) 
			if(ipAddress.equals(client.getIPAddress()) && ID==client.getID())
				clientList.remove(client);
		System.out.println("clientList: "+clientList);
			
	}

	public void handle(String ipAddress, int ID, String message) {
		System.out.println(message);
		if(message.equals("bye"))
			removeClient(ipAddress, ID);
		for(ChatServerThread client: clientList) {
			client.send(message);
		}
	}
}
