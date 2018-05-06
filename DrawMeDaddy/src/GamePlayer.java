import java.net.InetAddress;

class GamePlayer implements Comparable<GamePlayer>{
	private String name;
	private InetAddress address;
	private int portNumber;
	private int score = 0;
	private boolean isReady = false;

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

	public void updateScore(int score){
		this.score += score;
	}

	public int getScore(){
		return this.score;
	}

	public boolean isReady(){
		return this.isReady;
	}

	public void setReadiness(boolean status){
		this.isReady = status;
	}

	@Override
	public String toString(){
		String retVal = "";
		retVal += this.name + " ";
		retVal += this.score + " ";
		return retVal.trim();
	}

	@Override
	public int compareTo(GamePlayer playerTwo){
		return Integer.compare(this.score, playerTwo.getScore());
	}
	
}