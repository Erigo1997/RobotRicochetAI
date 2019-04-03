package primaryPackage;

import java.util.ArrayList;

public class AI_State {
	ArrayList<Robot> robots = new ArrayList<Robot> ();
	String commandline = ""; // The commands on robots made to reach this state.
	int activeActions = 0; // Number of steps active robot has taken to get to current state.
	String hash = "";
	
	public void generateHash() {
		for (Robot robot: robots) {
			if (robot.colour.equals("gren")) {
				hash += "gren";
			}
			hash += robot.x;
			hash += robot.y;
		}
	}
	
	@Override
	public boolean equals(Object o) {
		
		// If other is the same instance
		if (o == this) {
			return true;
		}
		
		// If other is not an AI_State
		if (!(o instanceof AI_State)) {
			return false;
		}
		
		AI_State other = (AI_State) o;
		if (hash.equals(other.hash)) {
			return true;
		} else {
			return false;
		}
	}
}
