package primaryPackage;

import java.util.ArrayDeque;
import java.util.Queue;

public class AI_BFS implements AI_Interface {
	
	Queue<AI_State> queue = new ArrayDeque<AI_State> ();
	
	public AI_BFS(Board board) {
		createNewState(board);
	}
	
	private void createNewState(Board board) {
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

	@Override
	public String Search() {
		
		return null;
	}

}
