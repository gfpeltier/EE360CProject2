package Assignment2_360C;

import java.util.ArrayList;

public class Device {
	
	private int id;
	private boolean discovered;
	private ArrayList<Trace> connections;
	
	
	public Device(int ID){
		this.id = ID;
		this.discovered = false;
	}
	
	
	public int getId(){
		return id;
	}
	
	public void setConnections(ArrayList<Trace> conn){
		connections = new ArrayList<Trace>();
		connections.addAll(conn);
	}
	
	public void pushConnect(Trace conn){
		connections.add(conn);
	}
	
	public ArrayList<Trace> getConnections(){
		return connections;
	}
	
	public void setDiscovered(){
		discovered = true;
	}
	
	public boolean isDiscovered(){
		return discovered;
	}

}
