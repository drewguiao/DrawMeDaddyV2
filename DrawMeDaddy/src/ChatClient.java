import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ChatClient implements Runnable,Constants{
	private Socket socket;
	private Thread thread;
	private DataInputStream console;
	private DataOutputStream streamOut;
	private ChatClientThread client;
	private String serverAddress;
	private String clientName;
	private int portNumber;
	
	
	
	public ChatClient(String serverAddress, int portNumber) {
		this.serverAddress = serverAddress;
		this.portNumber = portNumber;
		this.clientName = askClientName();
	}
	
	public void setUp() {
		System.out.println("Establishing connection...");
		try {
			socket = new Socket(serverAddress, portNumber);
			System.out.println("Connected!" +socket);
		}catch(UnknownHostException  uhe) {
			System.out.println("Host unknown: "+uhe.getMessage());
		} catch (IOException ioe) {
			System.out.println("Unexpected exception: "+ioe.getMessage());
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
		}catch(IOException ioe) {}
		
	}

	public void handle(String message) {
		// TODO Auto-generated method stub
		System.out.println(message);
		
	}

	@Override
	public void run() {
		Scanner console = new Scanner(System.in);
		while(true) {
			String message = console.nextLine();
			try {
				if(message.equals("bye")) {
					//socket.close();
					System.exit(1);
				}
				streamOut.writeUTF(clientName+": "+message);
				streamOut.flush();
			}catch(IOException ioe) {
				System.out.println(ioe.getMessage());
			}
		}
		
	}
	
	public String askClientName() {
		System.out.print("Enter name: ");
		Scanner scanner = new Scanner(System.in);
		
		return scanner.nextLine();
	}
	
}
