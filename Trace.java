package Assignment2_360C;

public class Trace {

	private Device devI;
	private Device devJ;
	private int time;
	
	public Trace(Device I, Device J, int T){
		devI = I;
		devJ = J;
		time = T;
	}
	
	
	public Device getTo(){
		return devI;
	}
	
	public Device getFrom(){
		return devJ;
	}
	
	public int getTime(){
		return time;
	}
	
}
