
public class ClientRunner {
	public static void main(String[] args) {
		checkArguments(args);
		ChatClient chatClient = new ChatClient(args[0], Integer.parseInt(args[1]));
		chatClient.setUp();
		chatClient.start();
	}
	
	private static void checkArguments(String[] args) {
		if (args.length != 2){
			System.out.println("Use <server IP address> <portno>");
			System.exit(1);
		}
	}
}
