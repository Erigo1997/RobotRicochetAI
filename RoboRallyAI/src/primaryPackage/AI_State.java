package primaryPackage;

import java.util.ArrayList;

public class AI_State implements Comparable<AI_State> {
	ArrayList<Robot> robots = new ArrayList<Robot> ();
	String commandline = ""; // The commands on robots made to reach this state.
	
	
	@Override
	public int compareTo(AI_State o) {
		if (robots.equals(o.robots)) {
			return 1;
		} else {
			return 0;
		}
	}
	

}
