package primaryPackage;

// A field in the board.
public class Field {
	
	// Init four different fields - neighbours.
	public Field leftField;
	public Field upField;
	public Field rightField;
	public Field downField;
	public Robot currentBot;
	
	// The number of squares this field is from the goal, assuming active robot can stop anytime. Here for prune search algorithm.
	public int minimum = -1;

	public int fieldId;
	public String goal;
	
	// Printing out the field.
	public String toString() {
		String output = "";
		if (goal != null) {
			output += goal.toUpperCase();
		} else if (currentBot != null) {
			output += currentBot.colour;
		} else {
			output += minimum;
		}
		output = "[" + output + "]";
		/* Shitty attempt at visualising walls.
		if (leftField == null) {
			output = "|" + output;
		}
		if (rightField == null) {
			output = output + "|";
		} */
		return output + "\t";
	}

}
