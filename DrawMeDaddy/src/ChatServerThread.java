import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ChatServerThread extends Thread{
	private Socket socket;
	private int ID;
	private String ipAddress;
	private DataInputStream streamIn;
	private DataOutputStream streamOut;
	private ChatServer server;
	public ChatServerThread(ChatServer chatServer, Socket socket) {
		this.server = chatServer;
		this.socket = socket;
		this.ID = socket.getPort();
		this.ipAddress = socket.getInetAddress().getHostAddress();
	}

	public void open() {
		try {
			streamIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			streamOut = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
		}catch(Exception e) {
			System.out.println("ChatServerThread.java.open():"+e.getMessage());
		}
		
	}

	@Override
	public void run() {
		System.out.println("Server Thread: "+ID+" running.");
		while(true) {
			try {
				server.handle(streamIn.readUTF());
			}catch(Exception e) {
				System.out.println("ChatServerThread.java.run(): Error reading: "+ID+" "+e.getMessage());
			}
		}
	}

	public void send(String message) {
		try {
			streamOut.writeUTF(message);
			streamOut.flush();
		}catch(Exception e) {
			System.out.println("ChatServerThread.java.send():"+e.getMessage());
		}
	}
}
