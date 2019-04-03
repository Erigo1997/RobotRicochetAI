package primaryPackage;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class AI_BFS implements AI_Interface {
	
	Queue<AI_State> frontier = new ArrayDeque<AI_State> ();
	ArrayList<AI_State> visited = new ArrayList<AI_State> ();
	Board board;
	String activeRobot;
	int activeLayer;
	
	// Create this algorithm class.
	public AI_BFS(Board board) {
		this.board = board;
		
		// Pre-compute for purpose of prune-searching, attaching 0 to goal to start.
		board.board[board.goaly][board.goalx].minimum = 0;
		board.precompute(board.goaly, board.goalx);
		
		// We set active robot so we know what to look for.
		activeRobot = board.activeRobot;
		// Set the first active layer - how many steps at most active robot must make. Any states beyond this are culled.
		if ((activeLayer = board.getMinimum(activeRobot)) == -1) {
			System.out.println("Robot is not in a valid field.");
			return;
		}
		
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
		state.activeActions = activeLayer;
		state.commandline += command + "\n";
		state.generateHash();
		if (!visitedContains(state)) {
			frontier.add(state);
		}
	}

	// The great algorithm itself.
	@Override
	public String Search() {
		int count = 0;
		AI_State currentState = new AI_State();
		// While we are not in a goal state, we keep searching.
		while (frontier.peek() != null) {
			currentState = frontier.poll(); // Poll top of queue.
			System.out.println("Checking new state. Number: " + ++count + " frontier size: " + frontier.size() + " visited size: " + visited.size() + " ActiveLayer: " + activeLayer);
			if (!visitedContains(currentState)) { // Make sure we haven't already visited this state and robot isn't in worse state.
				loadState(currentState); // Load current state's robots into the board.
				visited.add(currentState); // Visit current state.
				if (board.checkGoal()) { // Let's see if we've found our goal.
					return "Goal found!\n" + currentState.commandline;
				} else {
					checkFrontier(currentState); // Visit all states from current state. This is a larger method.
				}
				// System.out.println("Checking new state "  + ++count); // TODO: Remove.
			}
		}
		return "No goal could be found.";
	}

	// Not only checks containment but also culls states that have worse active action layers..
	private boolean visitedContains(AI_State currentState) {
		if (currentState.activeActions > activeLayer) {
			return true;
		}
		for (AI_State state : visited) {
			if (state.equals(currentState)) {
				return true;
			}
		}
		return false;
	}

	// Check all children states and add them to frontier.
	private void checkFrontier(AI_State currentState) {
		// For each robot, perform all movement
		for (Robot robot : currentState.robots) {
			for (int i = 0; i < 4; i++) { // Four iterations - one per movement available.
				loadState(currentState);
				/* Now we will make use of our precomputation heuristics. If the current robot is the active robot, we will ONLY move it to the suggest spot
				 * if we see that the moves it has made to get there are less than the layer of that spot. This culls a lot of states.
				 */
				if (robot.colour.equals(activeRobot)) {
					moveActiveRobot(currentState, robot, i);
				} else {
					moveInactiveRobot(currentState, robot, i);
				}
			}
		}
	}

	private void moveActiveRobot(AI_State currentState, Robot robot, int i) {
		switch (i) {
		case 0:
			board.moveRobot(robot.colour, "up");
			if (board.getMinimum(robot.colour) < activeLayer) {
				activeLayer = board.getMinimum(robot.colour);
				createNewState(currentState.commandline + robot.colour + "up");
			}
			break;
		case 1:
			board.moveRobot(robot.colour, "down");
			if (board.getMinimum(robot.colour) < activeLayer) {
				activeLayer = board.getMinimum(robot.colour);
				createNewState(currentState.commandline + robot.colour + "down");
			}
			break;
		case 2:
			board.moveRobot(robot.colour, "left");
			if (board.getMinimum(robot.colour) < activeLayer) {
				activeLayer = board.getMinimum(robot.colour);
				createNewState(currentState.commandline + robot.colour + "left");
			}
			break;
		case 3:
			board.moveRobot(robot.colour, "right");
			if (board.getMinimum(robot.colour) < activeLayer) {
				activeLayer = board.getMinimum(robot.colour);
				createNewState(currentState.commandline + robot.colour + "right");
			}
			break;
		}
	}

	private void moveInactiveRobot(AI_State currentState, Robot robot, int i) {
		switch (i) {
		/* Now we will make use of our precomputation heuristics. If the current robot is the active robot, we will ONLY move it to the suggest spot
		 * if we see that the moves it has made to get there are less than the layer of that spot. This culls a lot of states.
		 */
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
