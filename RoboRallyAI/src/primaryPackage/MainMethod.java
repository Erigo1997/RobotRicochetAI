package primaryPackage;

public class MainMethod {

	public static void main(String[] args) {
		Board gameBoard = new Board();
		
		// Creates the default set of walls.
		gameBoard.createWalls();
		
		// Initialize robots with the default location. NOTICE: ACTIVE ROBOT MUST BE INITIALIZED FIRST.
		gameBoard.createRobot("gren", 11, 13);
		gameBoard.createRobot("red", 2, 14);
		gameBoard.createRobot("yel", 3, 1);
		gameBoard.createRobot("blue", 7, 3);
		
		// Create a first goal. This must be selected from the default board state.
		// gameBoard.createGoal("gren", 14, 1); // Very Quickly Attainable
		gameBoard.createGoal("gren", 1, 2); // Very Quickly Attainable
		// gameBoard.createGoal("gren", 11, 2); // No solution is ever reached.
		// gameBoard.createGoal("gren", 13, 9); // No solution seems attainable.
		// gameBoard.createGoal("gren", 5, 13); // Very Quickly Attainable
		// gameBoard.createGoal("gren", 10, 14); // No solution seems attainable.
		// gameBoard.createGoal("gren", 14, 1); // Very quick solution.
		// gameBoard.createGoal("gren", 4, 1); // No solution.
		// gameBoard.createGoal("gren", 0, 11); // No solution.
		
		
		
		AI_Interface ai = new AI_BFS(gameBoard);
		
		// Prints board here so we can see attached layers.
		System.out.println(gameBoard.toString());		
		System.out.println("----------------------------------------------------------------------");
		
		System.out.println(ai.Search());

	}

}
