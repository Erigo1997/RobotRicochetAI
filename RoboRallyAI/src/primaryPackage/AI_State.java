package primaryPackage;

import java.util.ArrayList;

public class AI_State {
	ArrayList<Robot> robots = new ArrayList<Robot> ();
	String commandline = ""; // The commands on robots made to reach this state.
	
	@Override
	public boolean equals(Object o) {
		
		// If other is the same instance
		if (o == this) {
			System.out.println("o = this");
			return true;
		}
		
		// If other is not an AI_State
		if (!(o instanceof AI_State)) {
			System.out.println("Instanceof");
			return false;
		}
		
		// Create output bool array
		AI_State other = (AI_State) o;
		boolean[] output = new boolean[robots.size()];
		
		// Check if all robots here exist in other AI_State, and that all their coordinates are the same.
		int count = 0;
		for (Robot robot : robots) {
			output[count] = false;
			for (Robot otherRobot : other.robots) {
				if (robot.equals(otherRobot)) {
					output[count] = true;
				}
			}
			count++;
		}
		
		// Ensure that none of the output array is false
		for (int i = 0; i < output.length; i++) {
			if (output[i] == false)
				return false;
		}
		return true;
	}
}
