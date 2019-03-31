package primaryPackage;

public class MainMethod {

	public static void main(String[] args) {
		Board gameBoard = new Board();
		
		gameBoard.createRobot("gren", 8, 1); // The goal robot must be initialized first. Can't for the life of me
		// figure out why.
		gameBoard.createRobot("red", 7, 15);
		gameBoard.createRobot("yel", 6, 15);
		gameBoard.createRobot("blue", 5, 6);
		
		gameBoard.createGoal("gren", 14, 14);
		
		System.out.println(gameBoard.toString());
		
		System.out.println("----------------------------------------------------------------------");
		
		AI_Interface ai = new AI_BFS(gameBoard);
		System.out.println(ai.Search());

	}

}
