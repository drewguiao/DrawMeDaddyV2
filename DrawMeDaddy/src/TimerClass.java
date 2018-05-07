import java.util.Timer;
import java.util.TimerTask;

public class TimerClass implements Runnable,Constants{
	private int seconds;
	private int delay = 1000;
	private int period = 1000;
	private static final int MAX_TIME = 60;
	private GameServerThread server;
	Timer time = new Timer();
	private String word;
	

	public TimerClass(int seconds, GameServerThread server){
		this.seconds = ++seconds;
		this.server = server;
	}

	public void start(){
		this.startTimer();
	}

	
	public void startTimer(){
		Timer time = new Timer();
		time.scheduleAtFixedRate(
			new  TimerTask(){
				public void run(){
					if(seconds==0){
						time.cancel();
						time.purge();
					} else{
						decreaseSeconds();
						server.broadcast(TIME_SIGNAL+SPACE+getCurrentTime());
						//broadcast word here + mixed word
					}
				}
				
			}, delay, period);
	}

	@Override
	public void run(){
		time.scheduleAtFixedRate(
			new  TimerTask(){
				public void run(){
					if(seconds==0){
						time.cancel();
						time.purge();
					} else{
						decreaseSeconds();
						server.broadcast(STAGE_TIME_SIGNAL+SPACE+getCurrentTime());
						
					}
				}
				
			}, delay, period);
	}

	
	public void divideTime(int numberOfPlayers){
		int diminishingValue = seconds/numberOfPlayers;
		if(seconds>0)
			seconds = seconds - diminishingValue;
	}

	private int decreaseSeconds(){
		return --seconds;
	}

	public int getCurrentTime(){
		return seconds;
	}

	public void startViaThread(){
		this.run();
	}



	public void forceTimer(){
		this.seconds = 1;
	}

}