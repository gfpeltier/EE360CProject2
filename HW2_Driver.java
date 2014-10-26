package Assignment2_360C;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class HW2_Driver {

	
	public static Device[] devs;
	public static Trace[] traces;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length != 1) 
		{
			System.err.println ("Error: Incorrect number of command line arguments");
			System.exit(-1);
		}
		File input = new File(args[0]);
		
		try{
			BufferedReader plain = new BufferedReader(new FileReader(input));
			String s = plain.readLine();
			int n = Integer.parseInt(s.substring(0, s.indexOf(' ')));
			int m = Integer.parseInt(s.substring(s.indexOf(' ')+1));
			devs = new Device[n];
			traces = new Trace[m];
			int devIndex = 0;
			for(int traceIndex = 0; traceIndex < m; traceIndex++){		// Parse input traces
				s = plain.readLine();
				int indexOfFirstSpace = s.indexOf(' ');
				int indexOfSecondSpace = s.indexOf(' ', indexOfFirstSpace+1);
				int nodeI = Integer.parseInt(s.substring(0, indexOfFirstSpace));
				int nodeJ = Integer.parseInt(s.substring(indexOfFirstSpace+1, indexOfSecondSpace));
				int time = Integer.parseInt(s.substring(indexOfSecondSpace+1));
				if(devs[nodeI] == null){
					devs[nodeI] = new Device(nodeI);
				}
				if(devs[nodeJ] == null){
					devs[nodeJ] = new Device(nodeJ);
				}
				traces[traceIndex] = new Trace(devs[nodeI], devs[nodeJ], time);
				devs[nodeI].pushConnect(traces[traceIndex]);
				devs[nodeJ].pushConnect(traces[traceIndex]);
			}
			s = plain.readLine();	// s now has query
			int spaceIndex = s.indexOf(' ');
			int source = Integer.parseInt(s.substring(0, spaceIndex));
			int spaceIndex2 = s.indexOf(' ', spaceIndex+1);
			int destination = Integer.parseInt(s.substring(spaceIndex+1, spaceIndex2));
			int spaceIndex3 = s.indexOf(' ', spaceIndex2+1);
			int timeSent = Integer.parseInt(s.substring(spaceIndex2+1, spaceIndex3));
			int timeRecieved = Integer.parseInt(s.substring(spaceIndex3+1));
			//System.out.println("QUERY:");
			//System.out.println("From: " + source + ";  To: " + destination + ";  SentTime: " + timeSent + ";  RecTime: " + timeRecieved);
			ArrayList<String> output = runQuery(source, destination, timeSent, timeRecieved);
			Iterator<String> it = output.iterator();
			while(it.hasNext()){
				System.out.println(it.next());
			}
		}catch (FileNotFoundException e) {
            // File not found
            e.printStackTrace();

		}catch (IOException e) {
            // Not able to read line
            e.printStackTrace();
		}

	}
	
	
	public static ArrayList<String> runQuery(int src, int dest, int tSent, int tRec){
		ArrayList<String> out = new ArrayList<String>();
		ArrayList<ArrayList<Device>> levels = new ArrayList<ArrayList<Device>>();		// Array to hold arrays for each level of graph
		ArrayList<Device> primary = new ArrayList<Device>();
		primary.add(devs[src]);
		levels.add(primary);
		return out;
	}
	
	
	public static ArrayList<String> makeGraph(){
		ArrayList<String> out = new ArrayList<String>();
		for(int i = 0; i < devs.length; i++){
			
		}
		return out;
	}

}
