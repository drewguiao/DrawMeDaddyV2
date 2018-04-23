import java.net.InetAddress;

class GamePlayer{
	private String name;
	private InetAddress address;
	private int portNumber;
	public GamePlayer(String name, InetAddress address, int portNumber){
		this.name = name;
		this.address = address;
		this.portNumber = portNumber;
	}

	public String getName(){
		return this.name;
	}

	public InetAddress getAddress(){
		return this.address;
	}

	public int getPortNumber(){
		return this.portNumber;
	}
}