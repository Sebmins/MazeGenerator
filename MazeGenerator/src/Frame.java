import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Frame {
	private VariableDetails vDetails;
	
	private static JFrame frame = new JFrame();
	
	public Frame() {
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		frame.setSize(700,500);
		frame.setLayout(new BorderLayout());
		
		
		vDetails = new VariableDetails();
		MazeArea mArea = new MazeArea();
		
		Container c = frame.getContentPane();
		
		c.add(mArea, BorderLayout.CENTER); // Sets maze in centre of frame
		c.add(vDetails, BorderLayout.EAST); // Sets user input on right side
		

	}
	
	public static void setSize2(int width, int height) {
		frame.setSize(width,height); // Method can be called to change size of frame
		
	}
	
	
}
