package primaryPackage;

// A field in the board.
public class Field {
	
	// Init four different fields - neighbours.
	public Field leftField;
	public Field upField;
	public Field rightField;
	public Field downField;
	public Robot currentBot;

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
			output += "E";
		}
		return " [" + output + "]\t";
	}

}
