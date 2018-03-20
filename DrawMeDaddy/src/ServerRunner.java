
public class ServerRunner {
	public static void main(String[] args) {
		ChatServer chatServer = new ChatServer();
		chatServer.setUp();
		chatServer.start();
	}
}
