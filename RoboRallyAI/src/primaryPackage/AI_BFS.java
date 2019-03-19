package primaryPackage;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class AI_BFS implements AI_Interface {
	
	Queue<AI_State> frontier = new ArrayDeque<AI_State> ();
	ArrayList<AI_State> visited = new ArrayList<AI_State> ();
	Board board;
	
	// Create this algorithm class.
	public AI_BFS(Board board) {
		this.board = board;
		// Initialize the first state.
		createNewState("Start.");
	}
	
	// Loads all robots into the board from a state.
	private void loadState(AI_State state) {
		board.clearRobots();
		for (Robot robot : state.robots) {
			board.createRobot(robot.colour, robot.x, robot.y);
		}
	}
	
	// Save state by saving all current robots on the board.
	private void createNewState(String command) {
		AI_State state = new AI_State();
		for (Robot robot : board.robots) {
			Robot newRobot = new Robot();
			newRobot.colour = robot.colour;
			newRobot.x = robot.x;
			newRobot.y = robot.y;
			state.robots.add(newRobot);
		}
		state.commandline
		state.commandline += command + "\n";
		frontier.add(state);
	}

	// The great algorithm itself.
	@Override
	public String Search() {
		AI_State currentState = new AI_State();
		// While we are not in a goal state, we keep searching.
		while (frontier.peek() != null) {
			if (!visited.contains(frontier.peek())) { // Make sure we haven't already visited this state.
				currentState = frontier.poll(); // Poll top of queue.
				loadState(currentState); // Load current state's robots into the board.
				visited.add(currentState); // Visit current state.
				if (board.checkGoal()) { // Let's see if we've found our goal.
					return "Goal found!\n" + currentState.commandline;
				} else {
					checkFrontier(currentState); // Visit all states from current state. This is a larger method.
				}
			}
		}
		return "No goal could be found.";
	}

	// Check all children states and add them to frontier.
	private void checkFrontier(AI_State currentState) {
		// For each robot, perform all movement
		for (Robot robot : board.robots) {
			for (int i = 0; i < 4; i++) { // Four iterations - one per movement available.
				loadState(currentState);
				switch (i) {
				case 0:
					board.moveRobot(robot.colour, "up");
					createNewState(currentState.commandline + robot.colour + "up");
					break;
				case 1:
					board.moveRobot(robot.colour, "down");
					createNewState(currentState.commandline + robot.colour + "down");
					break;
				case 2:
					board.moveRobot(robot.colour, "left");
					createNewState(currentState.commandline + robot.colour + "left");
					break;
				case 3:
					board.moveRobot(robot.colour, "right");
					createNewState(currentState.commandline + robot.colour + "right");
					break;
				}
			}
		}
	}
}
