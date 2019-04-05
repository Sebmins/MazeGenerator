package Main;

import java.util.LinkedList;
import java.util.Queue;

import Main.WallFollower.Direction;

public class Navigation extends PrintTimes{
	int startrow = 1;
	int startcol = 0;
	static int rowPos = 1;
	static int colPos = 0;
	private static Direction currentDirection;
	static Queue<String> q = new LinkedList();
	static Queue<Integer> que = new LinkedList();
	static Object myFinch;
	
	public static void start(int[][] maze) {
		q.clear();
		j = 0;
		count = 0;
		int[][] solveMaze = mazePath(maze);
		setCurrentDirection(Direction.Right);
		move2(1,0,solveMaze);
		printQueue(q);
//		finchMovement(que);
	}
	
	enum Direction{
		Up,
		Down,
		Left,
		Right,
	}
	static int j = 0;
	static int count = 0;
	
//	static void finchMovement(Queue<Integer> q) {
//		for(Integer s : q) {
//			if(q.equals(0)) {
//				// Move Forward 3 seconds
//				myFinch.setWheelVelocities(255,255,3000);
//			}else if(q.equals(1)) {
//				// Turn Right 1 seconds
//				myFinch.setWheelVelocities(0,255,1000);
//			}else if(q.equals(2)) {
//				// Turn Left 1 seconds
//				myFinch.setWheelVelocities(255,0,1000);
//			}
//		}
//	}
	
	static void printQueue(Queue<String> q) {
		for(String s : q) { 
			if(s.contains("Forward")) {
				 count++;
			}else{
				  System.out.println("Forward"+"x"+count); 
				  count=0;
				  System.out.println(s.toString()); 
			}
			if(j++==q.size()-1) {
				  System.out.println("Forward"+"x"+count); 
			}
		}
	}
	
	static int[][] mazePath(int[][] maze)
	{
		for (int i = 0; i<rows; i++)
	    {
	        for (int j = 0; j < columns; j++)
	        {
	        	if (maze[i][j] == pathCode)
	        	{
	        		maze[i][j] = pathCode;
	        	}else {
	        		maze[i][j] = wallCode;
	        	}
	        }
	    }
		return maze;	
	}
static void move2(int row, int col, int[][] maze)
{
	if(col==columns-1 && row==rows-2) {
	}
	else {
		if(maze[row][col+1]==2){
			if(currentDirection!=Direction.Right)
				changeDirectionRight();
			q.add("Forward");
			que.add(0);
			maze[row][col]=3;
			move2(row,col+1,maze);
		}
			else if(maze[row][col-1]==2){
				if(currentDirection!=Direction.Left)
					changeDirectionLeft();
				q.add("Forward");
				que.add(0);
				maze[row][col]=3;
				move2(row,col-1,maze);
			}
			else if(maze[row-1][col]==2){
				if(currentDirection!=Direction.Up)
					changeDirectionUp();
				q.add("Forward");
				que.add(0);
				maze[row][col]=3;
				move2(row-1,col,maze);
			}
			else if(maze[row+1][col]==2){
				if(currentDirection!=Direction.Down)
					changeDirectionDown();
				q.add("Forward");
				que.add(0);
				maze[row][col]=3;
				move2(row+1,col,maze);
			}
		}
	}

private static void changeDirectionRight() {
	if(currentDirection==Direction.Up) {
		q.add("Turn Right");
		que.add(1);
	}
	if(currentDirection==Direction.Down) {
		q.add("Turn Left");
		que.add(2);
	}
	setCurrentDirection(Direction.Right);
}
	
	private static void changeDirectionLeft() {
		if(currentDirection==Direction.Down) {
			q.add("Turn Right");
			que.add(1);
		}
		if(currentDirection==Direction.Up) {
			q.add("Turn Left");
			que.add(2);
		}
		setCurrentDirection(Direction.Left);
	}
	
	private static void changeDirectionUp() {
		if(currentDirection==Direction.Left) {
			q.add("Turn Right");
			que.add(1);
		}
		if(currentDirection==Direction.Right) {
			q.add("Turn Left");
			que.add(2);
		}
		setCurrentDirection(Direction.Up);
	}
	
	private static void changeDirectionDown() {
		if(currentDirection==Direction.Right) {
			q.add("Turn Right");
			que.add(1);
		}
		if(currentDirection==Direction.Left) {
			q.add("Turn Left");
			que.add(2);
		}
		setCurrentDirection(Direction.Down);
	}
	
	public static Direction getCurrentDirection() {
		return currentDirection;
	}
	public static void setCurrentDirection(Direction changedDirection) {
		currentDirection = changedDirection;
	}  
	
}
