package Main;

public class WallFollower {

	private Direction currentDirection;
	int rowEnd2 = 0;
	int colEnd2 = 0;
	enum Direction{
		Up,
		Down,
		Right,
		Left
	}
	
public void move(int row, int col, int[][] maze) {
	if(row==rowEnd2 && col==colEnd2)
	{
		maze[row][col]=3;
	}else {
		if(getCurrentDirection() == Direction.Right){
			if(maze[row-1][col]==1 || maze[row-1][col]==3) {
				setCurrentDirection(Direction.Up);
				maze[row][col]=3;
				move(row-1,col,maze);
			}else if (maze[row][col+1]==1 || maze[row][col+1]==3){
				maze[row][col]=3;
				move(row,col+1,maze);
			}else {
				setCurrentDirection(Direction.Left);
				move(row,col,maze);
			}
		}
			
			if(getCurrentDirection() == Direction.Up) {
				if(maze[row][col-1]==1 ||maze[row][col-1]==3) {
					setCurrentDirection(Direction.Left);
					maze[row][col]=3;
					move(row,col-1,maze);
				}else if (maze[row-1][col]==1 || maze[row-1][col]==3){
					maze[row][col]=3;
					move(row-1,col,maze);
				}else {
					setCurrentDirection(Direction.Down);
					move(row,col,maze);
				}
			}if(getCurrentDirection() == Direction.Left) {
				if(maze[row+1][col]==1 || maze[row+1][col]==3) {
					setCurrentDirection(Direction.Down);
					maze[row][col]=3;
					move(row+1,col,maze);
				}else if (maze[row][col-1]==1 || maze[row][col-1]==3){
					maze[row][col]=3;
					move(row,col-1,maze);
				}else {
					setCurrentDirection(Direction.Right);
					move(row,col,maze);
				}
			}if(getCurrentDirection() == Direction.Down) {
				if(maze[row][col+1]==1 || maze[row][col+1]==3) {
					setCurrentDirection(Direction.Right);
					maze[row][col]=3;
					move(row,col+1,maze);
				}else if (maze[row+1][col]==1 || maze[row+1][col]==3){
					maze[row][col]=3;
					move(row+1,col,maze);
				}else {
					setCurrentDirection(Direction.Up);
					move(row,col,maze);
				}
			}
		}
	}
    
    void run(int row, int col, int rowEnd, int colEnd, int[][] maze)
	{
    	rowEnd2 = rowEnd;
    	colEnd2 = colEnd;
    	setCurrentDirection(Direction.Right);
		move(row,col,maze);
	}

	public Direction getCurrentDirection() {
		return currentDirection;
	}
	public void setCurrentDirection(Direction currentDirection) {
		this.currentDirection = currentDirection;
	}  
}
