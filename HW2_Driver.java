package Assignment2_360C;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

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
		boolean destReached = false;
		ArrayList<ArrayList<Device>> levels = new ArrayList<ArrayList<Device>>();		// Array to hold arrays for each level of graph
		ArrayList<Device> primary = new ArrayList<Device>();
		primary.add(devs[src]);
		levels.add(primary);
		devs[src].setDiscovered();		// need to set src device as discovered
		int layerCount = 0;
		devs[src].setLevel(layerCount);
		ArrayList<Trace> tree = new ArrayList<Trace>();
		while(!levels.get(layerCount).isEmpty() && !destReached){
			ArrayList<Device> tempLayer = new ArrayList<Device>();
			for(int k = 0; k < levels.get(layerCount).size(); k++){
				ArrayList<Trace> tmpTrace = levels.get(layerCount).get(k).getConnections();
				Iterator<Trace> it = tmpTrace.iterator();
				while(it.hasNext()){
					Trace tmp = it.next();
					if(tmp.getFrom().equals(levels.get(layerCount).get(k))){		// Trace incident from current device
						if(!tmp.getTo().isDiscovered() && tmp.getTime() >= tSent && tmp.getTime() <= tRec){
							if(tmp.getFrom().getId() == dest || tmp.getTo().getId() == dest){
								destReached = true;
							}
							tree.add(tmp);
							tmp.getFrom().notLeaf();
							tmp.getTo().setLeaf();
							tmp.getTo().setDiscovered();
							tmp.getTo().setLevel(layerCount + 1);
							tempLayer.add(tmp.getTo());
						}else{
							tmp.getFrom().destroyConnect(tmp);			// Get rid of unused connection
						}
					}else if(tmp.getTo().equals(levels.get(layerCount).get(k))){	// Trace incident to current device
						if(!tmp.getFrom().isDiscovered() && tmp.getTime() >= tSent && tmp.getTime() <= tRec){
							if(tmp.getFrom().getId() == dest || tmp.getTo().getId() == dest){
								destReached = true;
							}
							tree.add(tmp);
							tmp.getTo().notLeaf();
							tmp.getFrom().setLeaf();
							tmp.getFrom().setDiscovered();
							tmp.getFrom().setLevel(layerCount + 1);
							tempLayer.add(tmp.getFrom());
						}else{
							tmp.getTo().destroyConnect(tmp);			// Get rid of unused connection
						}
					}
				}
			}
			levels.add(tempLayer);
			layerCount++;
		}
		if(!destReached){
			ArrayList<String> out = new ArrayList<String>();
			out.add(0+"");
			return out;
		}else{
			ArrayList<Trace> path = cleanTree(tree, src, dest, layerCount - 1);
			return generateOutput(path);
		}
	}
	
	
	public static ArrayList<Trace> cleanTree(ArrayList<Trace> tree, int src, int dest, int numLayers){
		//ArrayList<Trace> clean = new ArrayList<Trace>();
		ListIterator<Trace> it = tree.listIterator(tree.size());
		while(it.hasPrevious()){
			Trace tmp = it.previous();
			if((tmp.getFrom().isLeaf() && tmp.getFrom().getLevel() < numLayers)||(tmp.getTo().isLeaf() && tmp.getTo().getLevel() < numLayers)){
				tmp.getFrom().setLeaf();
				tmp.getTo().setLeaf();
				tree.remove(tmp);
			}else if((tmp.getFrom().getLevel() == numLayers && tmp.getFrom().getId() != dest)&&(tmp.getTo().getLevel() == numLayers && tmp.getTo().getId() != dest)){
				tree.remove(tmp);
			}
		}
		return tree;
	}
	
	
	
	public static ArrayList<String> generateOutput(ArrayList<Trace> path){					// Output must be sorted in non-decreasing order of time. May do that here
		ArrayList<String> out = new ArrayList<String>();
		if(path.isEmpty()){
			out.add(0+"");
			return out;
		}
		out.add(path.size() + "");
		Iterator<Trace> it = path.iterator();
		while(it.hasNext()){
			Trace tmp = it.next();
			out.add(tmp.getFrom().getId() + " "+ tmp.getTo().getId() + " " + tmp.getTime());
		}
		return out;
	}

}
