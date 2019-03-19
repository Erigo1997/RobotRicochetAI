package primaryPackage;

import java.util.ArrayDeque;
import java.util.Queue;

public class AI_BFS implements AI_Interface {
	
	Queue<AI_State> queue = new ArrayDeque<AI_State> ();
	Board board;
	
	// Create this algorithm class.
	public AI_BFS(Board board) {
		this.board = board;
		createNewState();
	}
	
	// Pulls out the top of the state queue and initializes it into our board.
	private void retrieveState() {
		board.clearRobots();
		AI_State state = queue.poll();
		for (Robot robot : state.robots) {
			board.createRobot(robot.colour, robot.x, robot.y);
		}
	}
	
	// Save state by saving all current robots on the board.
	private void createNewState() {
		AI_State state = new AI_State();
		for (Robot robot : board.robots) {
			Robot newRobot = new Robot();
			newRobot.colour = robot.colour;
			newRobot.x = robot.x;
			newRobot.y = robot.y;
			state.robots.add(newRobot);
		}
		queue.add(state);
	}

	// The great algorithm itself.
	@Override
	public String Search() {
		
		return null;
	}

}
