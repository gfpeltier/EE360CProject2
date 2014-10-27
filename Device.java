package Assignment2_360C;

import java.util.ArrayList;

public class Device {
	
	private int id;
	private boolean discovered;
	private boolean leaf;
	private int message;
	int treeLevel;
	private ArrayList<Trace> connections;
	
	
	
	public Device(int ID){
		this.id = ID;
		this.discovered = false;
		this.leaf = false;
		this.message = 1000000;
		this.treeLevel = -1;
		this.connections = new ArrayList<Trace>();
	}
	
	
	public int getId(){
		return id;
	}
	
	public void setLevel(int level){
		treeLevel = level;
	}
	
	public int getLevel(){
		return treeLevel;
	}
	
	public void setMessage(int time){
		if(time < message){message = time;}
	}
	
	public int getMessage(){
		return message;
	}
	
	public void setConnections(ArrayList<Trace> conn){
		connections.addAll(conn);
	}
	
	public void pushConnect(Trace conn){
		connections.add(conn);
	}
	
	public void destroyConnect(Trace conn){
		connections.remove(conn);
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
	
	public void notLeaf(){
		leaf = false;
	}
	
	public void setLeaf(){
		leaf = true;
	}
	
	public boolean isLeaf(){
		return leaf;
	}
	
	public boolean equals(Device other){
		if(other.getId() == id){
			return true;
		}else{return false;}
	}

}
