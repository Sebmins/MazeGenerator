import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;

public class MazeArea extends JComponent implements Runnable {
	
	int[][] maze; // matrix of the maze

	final static int wallCode = 0; // walls between maze
	final static int emptyCode = 1; 
	final static int pathCode = 2; // correct solving path 
	final static int visitedCode = 3; // incorrect solving path
	final static int backgroundCode = 4; 
	final static int startFinish = 5;
	
	Color[] colour; // array that stores colours
	int rows = (MainGenerator.sizeY); 
	int columns = (MainGenerator.sizeX); 
	int blockSize = MainGenerator.cellSize; 
	
	boolean mazeExists = false;  	
	public boolean create = true;
	
	public MazeArea() {

		colour = new Color[] {
		Color.black, // Maze walls
		Color.white, // Maze path
		Color.green, // Solving path
		Color.gray, // Visited cells
		Color.white, // Background / border
		Color.white, // startFinish
		};
		new Thread(this).start(); // starts thread here
	}
	
	synchronized protected void paintComponent(Graphics g) { // graphics method that allows painting of frame
		super.paintComponent(g); // method called when repaint(); called
		redrawMaze(g);
	}
	
	void redrawMaze(Graphics g) {
		if (mazeExists) {
		int w = getWidth() / columns; // width of each cell
		int h = getHeight() / rows; // height of each cell
		for (int j=0; j<columns; j++) // Goes through whole of the maze[][] matrix 
			for (int i=0; i<rows; i++) {
				if (maze[i][j] < 0) // if less than one than empty code
					g.setColor(colour[emptyCode]); // sets colour to empty code
				else
					g.setColor(colour[maze[i][j]]); // sets colour to colour of value in matrix
				g.fillRect( (j * w), (i * h), w, h); // fills square given width and height
			}
		}
	}
	
	public void run() {
		while(create=true)
		{
			wait(100);
			makeMaze(); // generate initial squares
			generateWalls(); // generates walls between squares
			solveMaze(1,1); // solves maze 
			wait(500); // waits so you can see maze solution 
			clear(); // clears maze and loops again
		}
	}
	
	void clear() {
		if (maze == null)
	        maze = new int[rows][columns];
		for (int i = 0; i<rows; i++)
	    {
	        for (int j = 0; j < columns; j++)
	        {
	            maze[i][j] = wallCode; // puts matrix back to all wall code
	            repaint();
	            wait(2); // wait so can see the maze clearing
	        }
	    }
		mazeExists = true;
	}
	
    void makeMaze() {
	    if (maze == null)
	        maze = new int[rows][columns];
	    mazeExists=true;
	    int pathCount = 0;
	    for (int i = 0; i<rows; i++)
	    {
	        for (int j = 0; j < columns; j++)
	        {
	            maze[i][j] = wallCode; // maze starts off with all wall code
	            
	        }
	    }
	    repaint();

	    maze[1][0] = startFinish; // beginning of maze not covered in loop
	    for (int i = 1; i<rows-1; i += 2)
	        for (int j = 1; j<columns-1; j += 2) { // loop created to place empty code on all odd rows and columns
	        	pathCount++; // counts how many initial paths are needed
	            maze[i][j] = -pathCount; //
	            wait(10);
	            repaint();	            
	        }
	    maze[rows-2][columns-1] = startFinish; // end of maze not covered in loop
	    
    }
    
    void generateWalls() {
        int wallCount = 0; 
        int[] wallrow = new int[(rows*columns)/2];  
        int[] wallcol = new int[(rows*columns)/2];
        for (int i = 1; i<rows-1; i += 2)
            for (int j = 1; j<columns-1; j += 2) {
                if (i < rows-2) { 
                    wallrow[wallCount] = i+1; // loop to show where any possible empty path could be
                    wallcol[wallCount] = j; // can't be any diagonally only horizontally or vertically 
                    wallCount++;
                }
                if (j < columns-2) { 
                    wallrow[wallCount] = i;
                    wallcol[wallCount] = j+1;
                    wallCount++;
                }
            }
        
        mazeExists = true;
        repaint();
        int r;
        for (int i=wallCount-1; i>0; i--) {
            r = (int)(Math.random() * i); // Randomly chooses out of the possible choices
            tearDown(wallrow[r],wallcol[r]);
            repaint();
            wallrow[r] = wallrow[i];
            wallcol[r] = wallcol[i];
        }
        
        for (int i=1; i<rows-1; i++) 
            for (int j=1; j<columns-1; j++)
                if (maze[i][j] < 0)
                    maze[i][j] = emptyCode; // replaces negative with empty code
    }
	
    synchronized void tearDown(int row, int col) {
	    if (row % 2 == 1 && maze[row][col-1] != maze[row][col+1]) { // if row is odd checks one left and right to see if they are not equal (negative -pathcount) then that can be torn down to create a corridor
	        fill(row, col-1, maze[row][col-1], maze[row][col+1]);
	        maze[row][col] = maze[row][col+1];
	        repaint();
	        wait(10);
	    }
	    else if (row % 2 == 0 && maze[row-1][col] != maze[row+1][col]) { // if row is odd checks one up and down to see if they are not equal (negative -pathcount) then that can be torn down to create a corridor
	        fill(row-1, col, maze[row-1][col], maze[row+1][col]);
	        maze[row][col] = maze[row+1][col];
	        repaint();
	        wait(10);
	    }
    }
    
    void fill(int row, int col, int replace, int replaceWith) {
    	// Method checks all around the potential new empty code and checks that no loop is created, if loop is created then don't tear down
	    if (maze[row][col] == replace) {
	        maze[row][col] = replaceWith;
	        fill(row+1,col,replace,replaceWith);
	        fill(row-1,col,replace,replaceWith);
	        fill(row,col+1,replace,replaceWith);
	        fill(row,col-1,replace,replaceWith);
	    }
	}
    
    boolean solveMaze(int row, int col) {
    maze[1][0] = pathCode;
    if (maze[row][col] == emptyCode) {
        maze[row][col] = pathCode;
        repaint();
        if (row == rows-2 && col == columns-2)
        {
            maze[rows-2][columns-1] = pathCode;
            return true;
        }
        wait(10);
        
        if ( solveMaze(row-1,col)  ||
                solveMaze(row,col-1)  ||
                solveMaze(row+1,col)  ||
                solveMaze(row,col+1) )
            return true;
        maze[row][col] = visitedCode;
        repaint();
        synchronized(this) {
            wait(10);
        }
    }
    return false;
    
    }
       
    public void wait(int sleepTime)
    {
		try { Thread.sleep(sleepTime); }
		catch (InterruptedException e) { }
    }
}

