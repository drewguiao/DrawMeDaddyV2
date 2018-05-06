import java.util.Timer;
import java.util.TimerTask;

public class TimerClass implements Constants{
	int seconds;
	int delay = 1000;
	int period = 1000;

	public TimerClass(int secondsParam){
		this.seconds = ++secondsParam;
		Timer time = new Timer();
		time.scheduleAtFixedRate(
			new  TimerTask(){
				
				public void run(){
					if(seconds==10){
						System.out.println("Divide by N");
						divideTimeByN(2);
					}
					if(seconds==1){
						System.out.println("Time's Up!");
						time.cancel();
						time.purge();
					} else{
						decreaseSeconds();
						getCurrentTime();
					}
				}
				
			}, delay, period);
	}
	
	public void divideTimeByN(int N){
		if(seconds>0)
			seconds = seconds/N;
	}

	private int decreaseSeconds(){
		--seconds;
		return seconds;
	}

	public int getCurrentTime(){
		System.out.println(seconds);
		return seconds;
	}


}