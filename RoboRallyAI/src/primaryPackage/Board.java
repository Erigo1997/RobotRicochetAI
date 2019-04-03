package primaryPackage;

import java.util.ArrayList;

// Contains the board of the entire game.
public class Board {

	Field[][] board;
	ArrayList<Robot> robots;
	int goaly, goalx;
	String activeRobot;

	public Board() {
		board = new Field[16][16];
		robots = new ArrayList<Robot>();
		constructNewBoard();

		// TODO: Walls inside map? Perhaps we set linked fields to null to accomodate
		// walls.
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

				// Do the same for checking if there is a field to the left. We check left and
				// up because we
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
		for (Robot robot : robots) {
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
		goaly = y;
		goalx = x;
		activeRobot = string;

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

	// Creates a set of walls. For now, it simply creates a default board
	// configuration of walls.
	public void createWalls() {

		// X-row 0
		buildAWall(5, 0, "down");
		buildAWall(11, 0, "down");

		// X-row 1
		buildAWall(0, 1, "right");
		cornerWrap(2, 1, "NE");
		cornerWrap(9, 1, "SE");

		// X-row 3
		cornerWrap(6, 3, "SW");
		cornerWrap(14, 3, "NW");

		// X-row 4
		cornerWrap(1, 4, "NW");

		// X-row 5
		cornerWrap(8, 5, "NE");
		cornerWrap(13, 5, "NE");

		// X-row 6
		cornerWrap(3, 6, "SE");
		buildAWall(15, 6, "right");

		// X-row 9
		buildAWall(0, 9, "right");
		cornerWrap(11, 9, "SE");

		// X-row 10
		buildAWall(6, 10, "down");
		cornerWrap(14, 10, "NW");

		// X-row 11
		cornerWrap(2, 11, "SW");
		buildAWall(15, 11, "right");

		// X-row 13
		cornerWrap(6, 13, "SE");
		cornerWrap(9, 13, "SW");

		// X-row 14
		cornerWrap(1, 14, "NW");
		cornerWrap(13, 14, "NE");

		// X-row 15
		buildAWall(3, 15, "down");
		buildAWall(11, 15, "down");

		// Finally we create the middle locked section.
		cornerWrap(7, 7, "NW");
		cornerWrap(7, 8, "NE");
		cornerWrap(8, 8, "SE");
		cornerWrap(8, 7, "SW");
	}

	// Either creates a wall between a field and down or field and right. We don't
	// need one for left or up - that would be repeating code.
	private void buildAWall(int i, int j, String direction) {
		switch (direction) {
		case "right":
			board[i][j].rightField = null;
			board[i][j + 1].leftField = null;
			break;
		case "down":
			board[i][j].downField = null;
			board[i + 1][j].upField = null;
			break;
		}
	}

	// Wraps two walls of a field so it lies snugly in a corner.
	private void cornerWrap(int i, int j, String corner) {
		switch (corner) {
		case "NE":
			board[i][j].rightField = null;
			board[i][j].upField = null;
			board[i][j + 1].leftField = null;
			board[i - 1][j].downField = null;
			break;
		case "NW":
			board[i][j].leftField = null;
			board[i][j].upField = null;
			board[i][j - 1].rightField = null;
			board[i - 1][j].downField = null;
			break;
		case "SE":
			board[i][j].rightField = null;
			board[i][j].downField = null;
			board[i][j + 1].leftField = null;
			board[i + 1][j].upField = null;
			break;
		case "SW":
			board[i][j].leftField = null;
			board[i][j].downField = null;
			board[i][j - 1].rightField = null;
			board[i + 1][j].upField = null;
			break;
		}
	}

	// Give all fields a precompute value out of the goal.
	public void precompute(int y, int x) {
		int layer = board[y][x].minimum + 1;
		attachLayers(y, x, layer);
		computeNextLayer(y, x, layer);
	}

	// Goes left, right, up and down until it meets a wall or a field that already has a value attached, attaching layer values.
	private void attachLayers(int y, int x, int layer) {
		int y2;
		int x2;
		y2 = y;
		x2 = x;
		while (board[y2][x2].leftField != null && (board[y2][x2].leftField.minimum == -1  || board[y2][x2].leftField.minimum > layer )) {
			board[y2][x2].leftField.minimum = layer;
			x2--;
		}
		y2 = y;
		x2 = x;
		while (board[y2][x2].rightField != null && (board[y2][x2].rightField.minimum == -1  || board[y2][x2].rightField.minimum > layer )) {
			board[y2][x2].rightField.minimum = layer;
			x2++;
		}
		y2 = y;
		x2 = x;
		while (board[y2][x2].upField != null && (board[y2][x2].upField.minimum == -1  || board[y2][x2].upField.minimum > layer )) {
			board[y2][x2].upField.minimum = layer;
			y2--;
		}
		y2 = y;
		x2 = x;
		while (board[y2][x2].downField != null && (board[y2][x2].downField.minimum == -1  || board[y2][x2].downField.minimum > layer )) {
			board[y2][x2].downField.minimum = layer;
			y2++;
		}
	}
	
	// Goes left, right, up and down until it meets a wall or a field that is on a different layer, computing layers from them.
	private void computeNextLayer(int y, int x, int layer) {
		int y2;
		int x2;
		y2 = y;
		x2 = x;
		while (board[y2][x2].leftField != null && board[y2][x2].leftField.minimum == layer) {
			x2--;
			precompute(y2, x2);
		}
		y2 = y;
		x2 = x;
		while (board[y2][x2].rightField != null && board[y2][x2].rightField.minimum == layer) {
			x2++;
			precompute(y2, x2);
		}
		y2 = y;
		x2 = x;
		while (board[y2][x2].upField != null && board[y2][x2].upField.minimum == layer) {
			y2--;
			precompute(y2, x2);
		}
		y2 = y;
		x2 = x;
		while (board[y2][x2].downField != null && board[y2][x2].downField.minimum == layer) {
			y2++;
			precompute(y2, x2);
		}
	}
	
	public int getMinimum(String colour) {
		int x, y;
		for (Robot robot: robots) {
			if (robot.colour.equals(colour)) {
				x = robot.x;
				y = robot.y;
				return board[y][x].minimum;
			}
		}
		return -1;
	}
}
