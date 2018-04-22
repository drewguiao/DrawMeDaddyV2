import java.util.Scanner;

public class ClientRunner {
	public static void main(String[] args) {
		Scanner console = new Scanner(System.in);
		System.out.print("Name: ");
		String name = console.nextLine();
		System.out.print("Server(eg.localhost, 192.168.1.1): ");
		// String serverAddress = console.nextLine();
		String serverAddress = "localhost";
		System.out.print("Port(eg.4444): ");
		// int portNumber = console.nextInt();
		int portNumber = 1111;

		GameClient gameClient = new GameClient(name,serverAddress,portNumber);
		gameClient.setUpChat();
		gameClient.start();
	}
}
