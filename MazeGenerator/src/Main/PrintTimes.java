package Main;
import java.util.ArrayDeque;
import java.util.Queue;

public class PrintTimes extends MazeArea {
	static int count = 0;
	static int[][] mazeBFS;
	static int[][] mazeDFS;
	static int[][] mazeFollow;
	static double begTime = 0;
	static double endTime = 0;
	static double duration = 0;
	static double timedDFS = 0;
	static double timedBFS = 0;
	static double timedWallF = 0;
	
	
	public void run() {
	    mazeBFS = new int[rows][columns];
	    mazeDFS = new int[rows][columns];
	    mazeFollow = new int[rows][columns];
		copyMaze(maze,mazeDFS);
		copyMaze(maze,mazeBFS);
		copyMaze(maze,mazeFollow);
		
		timedDFS = timeDFS();
		timedBFS = timeBFS();
		timedWallF = timeWallF();
		
		print();
		Navigation.start(mazeDFS);
	}
	
	public static void print() {
		
		String path = Integer.toString(countMaze(2, mazeDFS));
		String visited = Integer.toString(countMaze(3, mazeDFS));
		int coverage = (countMaze(2, mazeDFS)+countMaze(3, mazeDFS)+countMaze(1,mazeDFS));
		String percentage = Integer.toString(((countMaze(2, mazeDFS)+countMaze(3, mazeDFS))*100/ coverage));
		String timeString = Double.toString(timedDFS);
		
		String path2 = Integer.toString(countMaze(2, mazeBFS));
		String visited2 = Integer.toString(countMaze(3, mazeBFS));
		int coverage2 = (countMaze(2, mazeBFS)+countMaze(3, mazeBFS)+countMaze(1,mazeBFS));
		String percentage2 = Integer.toString(((countMaze(2, mazeBFS)+countMaze(3, mazeBFS))*100/ coverage2));
		String timeString2 = Double.toString(timedBFS);
		
		String path3 = Integer.toString(countMaze(3, mazeFollow));
		String visited3 = Integer.toString(countMaze(2, mazeFollow));
		int coverage3 = (countMaze(3, mazeFollow)+countMaze(1,mazeFollow));
		String percentage3 = Integer.toString((countMaze(3, mazeFollow)*100/ coverage3));
		String timeString3 = Double.toString(timedWallF);
		
		final Object[][] table = new String[5][];
		table[0] = new String[] { "Name", "Visited", "Path", "Coverage", "Time (MS)" };
		table[1] = new String[] { "Depth First", visited, path, percentage+"%",timeString};
		table[3] = new String[] { "Breadth First", visited2, path2, percentage2+"%",timeString2};
		table[2] = new String[] { "Wall Follower", visited3, path3, percentage3+"%",timeString3};
		table[4] = new String[] { "N/A", "-", "-", "-", "-"};
		String line = new String("-------------------------------------------------");
			
		System.out.println(line);
		for (final Object[] row : table) {
			if(row.equals(table[1]))
			{
				System.out.println(line);					
			}
			System.out.format("%-14s%-10s%-7s%-10s%-10s\n", row);
		}
		System.out.println(line);
			
	}
	
	private static int countMaze(int find, int[][]maze) {
		
	    int countFind = 0;
		for (int i = 0; i<rows; i++)
	    {
	        for (int j = 0; j < columns; j++)
	        {
	        	if (maze[i][j] == find)
	        	{
	        		countFind++;
	        	}
	        }
	    }
		return countFind;
	}
	void copyMaze(int[][] maze, int[][]mazeCopy) {
	    
		for (int i = 0; i<rows; i++)
	    {
	        for (int j = 0; j < columns; j++)
	        {
	        	if (maze[i][j] == wallCode)
	        	{
	        		mazeCopy[i][j] = 0;
	        	}else {
	        		mazeCopy[i][j] = 1;
	        	}
	        }
	    }
	}
	
	public static void printMaze(int[][] maze){
		  for(int i = 0; i < rows; i++)
	      {
	      	for(int j = 0; j < columns; j++) {
	      		
	      		if(maze[i][j]<0 || j==0)
	      			System.out.print(maze[i][j]);
	      		else
	      			System.out.print(" "+maze[i][j]);
	      	}
	      	System.out.println();
	      }
		  System.out.println();  
	}
    
	public static double timeDFS()
	{
		begTime = System.nanoTime();
		
		recursiveCount(1,0,rows-2,columns-1,mazeDFS);
		
		endTime = System.nanoTime();
		duration = (endTime - begTime);  //divide by 1000000 to get milliseconds.
		return duration/1000000;
	}

	public static double timeBFS()
	{
		
		begTime = System.nanoTime();
		
		bfsCount(mazeBFS, 1, 0, rows-2, columns-1);
		
		endTime = System.nanoTime();
		duration = (endTime - begTime);  //divide by 1000000 to get milliseconds.
		return duration/1000000;
	}
	
	public static double timeWallF()
	{
		try {
			WallFollower wf = new WallFollower();
			
			begTime = System.nanoTime();
			wf.run(1, 0, rows-2, columns-1, mazeFollow);
			endTime = System.nanoTime();
			duration = (endTime - begTime);  //divide by 1000000 to get milliseconds.
			return duration/1000000;
		}
		catch ( StackOverflowError err ) {
//			System.out.println(err);
		}
		return 0;
	}
	  
	
    static boolean recursiveCount(int row, int col, int rowEnd, int colEnd, int[][] maze) {
	    if (maze[row][col] == 1) { // if block is not a wall then try that route
	        maze[row][col] = 2; // add this block to solving path
	        if (row == rowEnd && col == colEnd) // if in this block then found the exit
	        {
	            return true; // finish 
	        }
	      
	        if (recursiveCount(row+1,col,rowEnd, colEnd,maze) == true  || // checks left to see if it is correct
	        	recursiveCount(row,col+1,rowEnd, colEnd,maze) == true  || // checks below to see if it is correct
	        	recursiveCount(row-1,col,rowEnd, colEnd,maze) == true  || // checks right to see if it is correct
	        	recursiveCount(row,col-1,rowEnd, colEnd,maze) == true )   // checks up to see if it is correct
	        	return true;    
	        

	        maze[row][col] = 3; // add this block to visited
	    }
	    return false;
    }   

    
    // Below arrays details all 4 possible movements from a cell
	private final static int row[] = { -1, 0, 0, 1 };
	private final static int col[] = { 0, -1, 1, 0 };
	private static boolean isValid(int maze[][], int visited2[][],int path,int row, int col)
	{
		return (row >= 0) && (row < rows) && (col >= 0) && (col < columns)
					   && (maze[row][col] == emptyCode || maze[row][col] == visitedCode) && visited2[row][col]<path;
	}


	private static void bfsCount(int maze[][], int i, int j, int x, int y)
	{
		int[][] visited2 = new int[rows][columns];
		Queue<Node> queue = new ArrayDeque<>();
		maze[i][j]=3;
		int path = 2;
		visited2[i][j] = path;
		queue.add(new Node(i, j));
		while (!queue.isEmpty())
		{
			Node node = queue.poll();
			i = node.x;
			j = node.y;
			for (int k = 0; k < 4; k++)
			{
				if (isValid(maze, visited2, 1, i + row[k], j + col[k]))
				{
					int r = i+row[k];
					int c = j+col[k];
					queue.add(new Node(r, c));
					path=path+1;
					visited2[r][c] = path;
					maze[r][c]=3;
					if (r == x && c == y)
	    			{
	    				int pathN = visited2[r][c];
	    				maze[r][c] = 2;
	    				while(r!=1 && c!=0) {
		    				for (int k1 = 0; k1 < 4; k1++)
		        			{
		        				if (isValid(maze, visited2, pathN, r + row[k1], c + col[k1]))
		        				{
		        					r = r+row[k1];
		        					c = c+col[k1];
		        					pathN = visited2[r][c];
		        					maze[r][c] = 2;
		        					k1=0;
		        				}
		        			}
	    				}queue.removeAll(queue);
	    			}
				}
			}
		}
	}

    
}
