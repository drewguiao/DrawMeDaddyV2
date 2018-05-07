import java.util.Timer;
import java.util.TimerTask;

public class TimerClass implements Runnable,Constants{
	private int seconds;
	private int delay = 1000;
	private int period = 1000;
	private GameServerThread server;


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
					}
				}
				
			}, delay, period);
	}

	@Override
	public void run(){
		Timer time = new Timer();
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

	
	public void divideTime(int N){
		if(seconds>0)
			seconds = seconds/N;
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


}