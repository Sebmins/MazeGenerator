import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.JFrame;

public class Frame {
	private VariableDetails vDetails;
	
	private static JFrame frame = new JFrame();
	private double columns = MazeArea.columns;
	private double rows = MazeArea.rows;
	private static int cell= 128;
	
	public Frame() {
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		frame.setSize(950,950);
        
        setSize3(rows,columns);
		
		frame.setLayout(new BorderLayout());

		System.out.println(frame.getSize());
		
		vDetails = new VariableDetails();
		MazeArea mArea = new MazeArea();
		
		Container c = frame.getContentPane();
		
		c.add(mArea, BorderLayout.CENTER); // Sets maze in centre of frame
		c.add(vDetails, BorderLayout.EAST); // Sets user input on right side
		
	}
	
	public static void setSize2(int width, int height) {
		frame.setSize(width,height); // Method can be called to change size of frame
	}
	
	public static void setSize3(double rows, double columns) {
		frame.setSize(950,950);
		Insets insets = frame.getInsets();
		int realWidth = (insets.left + insets.right);
		int realHeight = (insets.top + insets.bottom);
		
		if(columns != rows)
        {
        	if(columns < rows) {
        		cell = (int) Math.round(frame.getHeight()/ (double) rows);
        		
        	}else {
        		cell = (int) Math.round(frame.getWidth() / (double) columns);
        	}
        	int width = (int)((cell*columns)+realWidth + 241);
        	int height = (int)((cell*rows)+realHeight);
        	frame.setSize(width, height);
        }else {
        	cell = (int) Math.round(frame.getWidth() / (double) columns);
        	frame.setSize((int)(cell*columns+realWidth + 241),(int)(cell*rows+realHeight));
        }
	}
	
}
