import java.util.Timer;
import java.util.TimerTask;

public class TimerClass implements Constants{
	int seconds;
	int delay = 1000;
	int period = 1000;

	public TimerClass(int N){
		this.seconds = ++N;
		Timer time = new Timer();
		time.scheduleAtFixedRate(
			new  TimerTask(){
				
				public void run(){
					if(seconds==1){
						System.out.println("Time's Up!");
						time.cancel();
						time.purge();
					} else{
						System.out.println(decreaseN());
					}
				}
				
			}, delay, period);
	}
	
	public int decreaseN(){
		return --seconds;
	}


}