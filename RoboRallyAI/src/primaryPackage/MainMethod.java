package primaryPackage;

public class MainMethod {

	public static void main(String[] args) {
		Board gameBoard = new Board();
		
		gameBoard.createRobot("gren", 8, 15);
		
		gameBoard.createGoal("gren", 15, 0);
		
		System.out.println(gameBoard.toString());
		
		System.out.println("----------------------------------------------------------------------");
		
		AI_BFS ai = new AI_BFS(gameBoard);

	}

}
