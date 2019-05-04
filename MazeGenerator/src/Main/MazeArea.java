package Main;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayDeque;
import java.util.Queue;

import javax.swing.JComponent;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;

import Main.WallFollower.Direction;

public class MazeArea extends JComponent implements Runnable {

	WallFollower wf = new WallFollower();
	
	int rowEnd2 = 0;
	int colEnd2 = 0;
	
	static int[][] maze; // matrix of the maze

	final static int wallCode = 0; // walls between maze
	final static int emptyCode = 1; 
	final static int pathCode = 2; // correct solving path 
	final static int visitedCode = 3; // incorrect solving path
	
	
	Color[] colour; // array that stores colours
	static int rows = (MainGenerator.rows); 
	static int columns = (MainGenerator.columns); 
	boolean mazeExists = false;  	
	public boolean create = true;
	static double waittime2 = 1;
	static double waitTime = Math.round(highLowAlgo(rows,columns));
	long startTime = System.currentTimeMillis();
	long elapsedTime = 0L;

	
	
	// r g b a 
	public MazeArea() {
		colour = new Color[] {
		new Color(0,0,0,200),//Black // Maze walls
		new Color(255,255,255,20),//White // Maze paths
		new Color(0,255,0,80), //Green // Solving path
		new Color(255,0,0,80), //Red // Visited cells
		};
		
		new Thread(this).start(); // starts thread here
	}
	
	private static BufferedImage image;
	
	int count = 0;
	public void setFace(BufferedImage img) {
		image = img;
	}
	
	synchronized protected void paintComponent(Graphics g) { 		// graphics method that allows painting of frame
		super.paintComponent(g); 									// method called when repaint(); called
		if(caloop==1) {
	//		wait(400);
			drawCam(g);
			redrawMaze(g);
		} else
			redrawMaze(g);
	}
		
	void drawCam(Graphics g) {
		if(this.image==null) {
			System.out.println("!! The JPANEL IMAGE IS NULL!!!");
			return;
		}
		g.drawImage(this.image,  0, 0, this.image.getWidth()*2, this.image.getHeight()*2, null);
	}
	
	void redrawMaze(Graphics g) {
		if (mazeExists = true) {
			int w = (int) Math.round(getWidth() / (double) columns); // width of each cell
			int h = (int) Math.round(getHeight()/ (double) rows); // height of each cell
			for (int j=0; j<columns; j++) // Goes through whole of the maze[][] matrix 
				for (int i=0; i<rows; i++) {
					try {
						if (maze[i][j] < 0) // if less than one than empty code
							g.setColor(colour[emptyCode]); // sets colour to empty code
						else
							g.setColor(colour[maze[i][j]]); // sets colour to colour of value in matrix
						g.fillRect( (j * w), (i * h), w, h); // fills square given width and height
				    }catch (Exception e){
						
					}
				}
			}
		
		g.setFont(new Font("arial",2,20));
		g.setColor(Color.WHITE);
		g.drawString("Web cam processing frame by frame [Frame: "+ (count++) + " ]",50,50);
	}
	
	public static int maloop =0;
	public static int caloop =0;
	
	public void run() {
		while(create=true)
		{
			System.out.println(waitTime);
			caloop = 0;
			maloop = 0;
			makeMaze();
			generateWalls();
			wait(200.0);
			if(MainGenerator.dfs==true)
				recursiveSolve(1,0,rows-2,columns-1);
			if(MainGenerator.bfs==true)
				BFS(maze, 1, 0, rows-2, columns-1);
			if(MainGenerator.wallF==true)
				followWallStart(1, 0, maze);
			PrintTimes pt = new PrintTimes();
			while(maloop < 1 || caloop == 1) {
				if(caloop == 1) { 
					System.out.println("run loop triggered");
					webcam();
				}else 
				wait(500.0);
			} // waits so you can see maze solution
			clear(); // clears maze and loops again
			rows = VariableDetails.inputY;
			columns = VariableDetails.inputX;
			Frame.setSize3(rows,columns);
		}
		
	}
	

	void clear() {
		for (int i = 0; i<rows; i++)
	    {
	        for (int j = 0; j < columns; j++)
	        {
	            maze[i][j] = wallCode; // puts matrix back to all wall code
	            repaint();
	            wait(0.2); // wait so can see the maze clearing
	        }
	    }
		maze = null;
		mazeExists = true;
	}
	
    void makeMaze() {
	    if (maze == null) 
	        maze = new int[rows][columns];
	    
	    mazeExists=true;
	    int pathCount = 0;
	    for (int i = 0; i<rows; i++) 
	        for (int j = 0; j < columns; j++)
	        	maze[i][j] = wallCode; // maze starts off with all wall code
	        
	   	repaint();
	    wait(waitTime);
	    maze[1][0] = emptyCode; // beginning of maze not covered in loop
	    for (int i = 1; i<rows-1; i += 2)
	        for (int j = 1; j<columns-1; j += 2) { // loop created to place empty code on all odd rows and columns
	        	pathCount++; // counts how many initial paths are needed
	            maze[i][j] = -pathCount; // makes empty code negative increasing incrementally 
	            repaint();	  
	            wait(waittime2*3);
	        }
	    maze[rows-2][columns-1] = emptyCode; // end of maze not covered in loop
	    repaint();
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
        int r;
        repaint();
        wait(waittime2);
        for (int i=wallCount-1; i>0; i--) {
            r = (int)(Math.random() * i); // Randomly chooses out of the possible choices
            tearDown(wallrow[r],wallcol[r]);
            repaint();
            wait(waittime2);
            wallrow[r] = wallrow[i];
            wallcol[r] = wallcol[i];
            
        }
        
        for (int i=1; i<rows-1; i++) 
            for (int j=1; j<columns-1; j++)
                if (maze[i][j] < 0)
                    maze[i][j] = emptyCode; // replaces negative with empty code
    }
	
    synchronized void tearDown(int row, int col) {
	    if (row % 2 == 1 && maze[row][col-1] != maze[row][col+1]) { 			// if row is odd checks one left and right to see if they are not equal (negative -pathcount) then that can be torn down to create a corridor
	        fill(row, col-1, maze[row][col-1], maze[row][col+1]);
	        maze[row][col] = maze[row][col+1];
	    }
	    else if (row % 2 == 0 && maze[row-1][col] != maze[row+1][col]) {		// if row is odd checks one up and down to see if they are not equal (negative -pathcount) then that can be torn down to create a corridor
	        fill(row-1, col, maze[row-1][col], maze[row+1][col]);
	        maze[row][col] = maze[row+1][col];
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
    
    boolean recursiveSolve(int row, int col, int rowEnd, int colEnd) {
    	if (maze[row][col] == emptyCode) { // if block is not a wall then try that route
	        maze[row][col] = pathCode; // add this block to solving path
	        repaint(); 
	        if (row == rowEnd && col == colEnd) // if in this block then found the exit
	            return true; // finish 
			wait(waitTime);
	      
	        if (recursiveSolve(row+1,col,rowEnd, colEnd) == true  || // checks left to see if it is correct
	        	recursiveSolve(row,col+1,rowEnd, colEnd) == true  || // checks below to see if it is correct
	        	recursiveSolve(row-1,col,rowEnd, colEnd) == true  || // checks right to see if it is correct
	        	recursiveSolve(row,col-1,rowEnd, colEnd) == true )   // checks up to see if it is correct
	        	return true;    
	        
	        maze[row][col] = visitedCode; // add this block to visited
	        repaint();
	        synchronized(this) {
	            wait(waitTime);
	        }
	    }
	    return false;
    } 
    
    // Below arrays details all 4 possible movements from a cell
	private final int row[] = { -1, 0, 0, 1 };
	private final int col[] = { 0, -1, 1, 0 };
	private boolean isValid(int maze[][], int visited2[][],int path,int row, int col)
	{
		return (row >= 0) && (row < rows) && (col >= 0) && (col < columns)
					   && (maze[row][col] == emptyCode || maze[row][col] == visitedCode) && visited2[row][col]<path;
	}


	private void BFS(int maze[][], int i, int j, int x, int y)
	{
		int[][] visited2 = new int[rows][columns];
		Queue<Node> queue = new ArrayDeque<>();
	
		maze[i][j]=visitedCode;
		repaint();
		wait(waitTime);
		int path = 2;
		visited2[i][j] = path;
		queue.add(new Node(i, j));

		while (!queue.isEmpty())
		{
			repaint();
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
					maze[r][c]=visitedCode;
					repaint();
					wait(waitTime);
    					
					if (r == x && c == y)
	    			{
	    				int pathN = visited2[r][c];
	    				maze[r][c] = pathCode;
	    				repaint();
	    				wait(waitTime);
	    				while(r!=1 && c!=0) {
		    				for (int k1 = 0; k1 < 4; k1++)
		        			{
    		    					
		        				if (isValid(maze, visited2, pathN, r + row[k1], c + col[k1]))
		        				{
		        					r = r+row[k1];
		        					c = c+col[k1];
		        					pathN = visited2[r][c];
		        					maze[r][c] = pathCode;
		        					repaint();
		        					wait(waitTime);
		        					k1=0;
		        				}
		        			}
	    				}queue.removeAll(queue);
	    			}
				}
			}
		}
	}

    
    static int highLowAlgo(int row,int col)
    {
    	int wait = (int) (1.5*(Math.log(500000/((row*col)-10)))-4);
    	
    	if (wait<0)
    		return 0;
    	
    	return wait;
    }
    static double highLowAlgo2(int row,int col)
    {
    	if (row+col>161 && row+col<320) 
    		return 1;
    	    	
    	double wait = (25*Math.pow(Math.E, (double) ((-(row+col)+14))/48 ));
    	return wait;
    }

    
    public static void wait(double sleepTime) // method delays usually used to paint components slower
    {
		try { Thread.sleep((long) sleepTime); }
		catch (InterruptedException e) { }
    }
    

	boolean move(int row, int col, int[][] maze) {
		maze[row][col]=3;
		repaint();
		wait(waitTime);
		
		try {	
			if(row==rows-2 && col==columns-1)
			{
				maze[row][col]=2;
				repaint();
				return true;
			}else {
				if(wf.getCurrentDirection() == Direction.Right){
					if(maze[row-1][col]==1 || maze[row-1][col]==2) {
						wf.setCurrentDirection(Direction.Up);
						maze[row][col]=2;
						move(row-1,col,maze);
					}else if (maze[row][col+1]==1 || maze[row][col+1]==2){
						maze[row][col]=2;
						move(row,col+1,maze);
					}else {
						wf.setCurrentDirection(Direction.Left);
						move(row,col,maze);
					}
				}if(wf.getCurrentDirection() == Direction.Up) {
					if(maze[row][col-1]==1 ||maze[row][col-1]==2) {
						wf.setCurrentDirection(Direction.Left);
						maze[row][col]=2;
						move(row,col-1,maze);
					}else if (maze[row-1][col]==1 || maze[row-1][col]==2){
						maze[row][col]=2;
						move(row-1,col,maze);
					}else {
						wf.setCurrentDirection(Direction.Down);
						move(row,col,maze);
					}
				}if(wf.getCurrentDirection() == Direction.Left) {
					if(maze[row+1][col]==1 || maze[row+1][col]==2) {
						wf.setCurrentDirection(Direction.Down);
						maze[row][col]=2;
						move(row+1,col,maze);
					}else if (maze[row][col-1]==1 || maze[row][col-1]==2){
						maze[row][col]=2;
						move(row,col-1,maze);
					}else {
						wf.setCurrentDirection(Direction.Right);
						move(row,col,maze);
					}
				}if(wf.getCurrentDirection() == Direction.Down) {
					if(maze[row][col+1]==1 || maze[row][col+1]==2) {
						wf.setCurrentDirection(Direction.Right);
						maze[row][col]=2;
						move(row,col+1,maze);
					}else if (maze[row+1][col]==1 || maze[row+1][col]==2){
						maze[row][col]=2;
						move(row+1,col,maze);
					}else {
						wf.setCurrentDirection(Direction.Up);
						move(row,col,maze);
					}
				}
			}
		}
		catch ( StackOverflowError err ) {
//			System.out.println(err);
			return false;
		}
		return false;
	}
	void followWallStart(int row, int col, int[][] maze)
	{
    	wf.setCurrentDirection(Direction.Right);
    	move(row,col,maze);
    	
	}
	
	public void webcam() {
		Frame.cantClose();
		MatToBufImg ImageConverter = new MatToBufImg();

		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		VideoCapture video = new VideoCapture(1);
		
		if(!video.isOpened()) {
			System.out.println("!!! DID NOT CONNEC 1!!");
		} else System.out.println("found webcam: "+video.toString());
		
		Mat video_image = new Mat();
		
		if(video.isOpened()) {
			while( true ) {
				if (caloop == 2) {
					System.out.println("break");
					break;
				}
				video.read(video_image);
				if( !video_image.empty()) {
					ImageConverter.setMatrix(video_image, ".jpg");
					BufferedImage bufImg = ImageConverter.getBufferedImage();
					setFace(bufImg);
					repaint();
				}
				else {
					System.out.println("!! Nothing capture from webcam !!");
					break;
				}
				
			}
			Frame.canClose();
			video.release();
		}
	}

}