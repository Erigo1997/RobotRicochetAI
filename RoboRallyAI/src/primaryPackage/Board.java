package primaryPackage;

import java.util.ArrayList;

// Contains the board of the entire game.
public class Board {
	
	Field[][] board;
	ArrayList<Robot> robots;
	
	public Board() {
		board = new Field[16][16];
		robots = new ArrayList<Robot>();
		constructNewBoard();
		
		// TODO: Walls inside map? Perhaps we set linked fields to null to accomodate walls.
	}
	

	private void constructNewBoard() {
		// Make notice from here - j denotes horizontal coordinates, i denoted vertical.
		// If in doubt, think of OPPOSITE of x and y coordinates.
		int count = 0;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				board[i][j] = new Field();
				
				// If the field above the current field exists, make this one its neighbour.
				if (i > 0 && i < 16 && board[i - 1][j] != null) {
					board[i][j].upField = board[i - 1][j];
					// Repeat for other field. Otherwise it may not pick up that it has a neighbour.
					board[i - 1][j].downField = board[i][j];
				} else {
					board[i][j].upField = null;
				}
				
				// Do the same for checking if there is a field to the left. We check left and up because we
				// create fields in the order of right and down.
				if (j > 0 && j < 16 && board[i][j - 1] != null) {
					board[i][j].leftField = board[i][j - 1];
					// Repeat for other field. Otherwise it may not pick up that it has a neighbour.
					board[i][j - 1].rightField = board[i][j];
				} else {
					board[i][j].leftField = null;
				}
				
				// Finally we set the rightfield and downfield to null for now.
				board[i][j].downField = null;
				board[i][j].rightField = null;
				
				// Add an ID to the field.
				board[i][j].fieldId = count;
				count++;
			}
		} // Two for loops end here.
	}
	
	// Create a new robot and place it in a field.
	public void createRobot(String colour, int x, int y) {
		Robot newRobot = new Robot();
		newRobot.colour = colour;
		board[y][x].currentBot = newRobot;
		newRobot.x = x;
		newRobot.y = y;
		robots.add(newRobot);
	}
	
	public void moveRobot(String colour, String command) {
		int x, y;
		for (Robot robot: robots) {
			if (robot.colour.equals(colour)) {
				x = robot.x;
				y = robot.y;
				
				switch (command) {
				case "up":
					moveUp(x, y, robot);
					break;
				case "down":
					moveDown(x, y, robot);
					break;
				case "left":
					moveLeft(x, y, robot);
					break;
				case "right":
					moveRight(x, y, robot);
					break;
					default:
						System.err.println("Wrong command to MoveRobot input. Give a proper command. Gave: " + command);
				}
			}
		}
	}

	private void moveUp(int x, int y, Robot robot) {
		while (board[y][x].upField != null && board[y][x].upField.currentBot == null) {
			board[y][x].currentBot = null;
			board[y][x].upField.currentBot = robot;
			robot.y--;
			y--;
		}
	}
	
	private void moveDown(int x, int y, Robot robot) {
		while (board[y][x].downField != null && board[y][x].downField.currentBot == null) {
			board[y][x].currentBot = null;
			board[y][x].downField.currentBot = robot;
			robot.y++;
			y++;
		}
	}
	
	private void moveLeft(int x, int y, Robot robot) {
		while (board[y][x].leftField != null && board[y][x].leftField.currentBot == null) {
			board[y][x].currentBot = null;
			board[y][x].leftField.currentBot = robot;
			robot.x--;
			x--;
		}
	}
	
	private void moveRight(int x, int y, Robot robot) {
		while (board[y][x].rightField != null && board[y][x].rightField.currentBot == null) {
			board[y][x].currentBot = null;
			board[y][x].rightField.currentBot = robot;
			robot.x++;
			x++;
		}
	}
	
	
	public String toString() {
		String output = "";
		// Print the entire field out as a matrix.
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				output += board[i][j].toString();
			}
			output += "\n";
		}
		return output;
	}

	// Create a goal at set field.
	public void createGoal(String string, int x, int y) {
		board[y][x].goal = string;
		
	}
	
	// Check if all robots are perhaps on a goal space.
	public boolean checkGoal() {
		int x, y;
		for (Robot robot : robots) {
			x = robot.x;
			y = robot.y;
			String goal = board[y][x].goal;
			if (goal != null && goal.equals(robot.colour)) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
	
	// Destroys all robots on the board right now.
	public void clearRobots() {
		for (Robot robot : robots) {
			board[robot.y][robot.x].currentBot = null;
		}
		robots.clear();
	}
}
