
public class ServerRunner {
	public static void main(String[] args) {
		checkArguments(args);
		ChatServer chatServer = new ChatServer(Integer.parseInt(args[0]));
		chatServer.setUp();
		chatServer.start();
	}
	
	private static void checkArguments(String[] args) {
		if (args.length != 1){
			System.out.println("Use <portno>");
			System.exit(1);
		}
	}
}
