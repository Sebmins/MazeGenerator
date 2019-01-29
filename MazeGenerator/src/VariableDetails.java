import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class VariableDetails extends JPanel {
	public VariableDetails() {
		
		setBorder(BorderFactory.createTitledBorder("Maze Variables"));
		
		JLabel rowLabel = new JLabel("Number of rows: "); // row label
		JLabel colLabel = new JLabel("Number of columns: "); // col label
		JLabel celLabel = new JLabel("Cell size: "); // cell size label
		
		JTextField rowField = new JTextField("25",10); // row input
		JTextField colField = new JTextField("25",10); // col input
		JTextField celField = new JTextField("20",10); // cell size input
		
		JButton genBtn = new JButton("Generate"); 
		
		setLayout(new GridBagLayout());
		
		GridBagConstraints gc = new GridBagConstraints();
		
		
		
		gc.anchor = GridBagConstraints.LINE_END; // Adds to end of frame layout
		
		gc.gridx = 0; // row position first left in layout
		gc.gridy = 0;
		
		gc.weightx = 0.5;
		gc.weighty = 0.5;
		
		add(rowLabel, gc);
		
		gc.gridx = 0; // column position second in left layout 
		gc.gridy = 1;
		
		add(colLabel, gc);
				
		gc.gridx = 0; // cell size position third in left layout
		gc.gridy = 2;
		
		add(celLabel, gc);
				
		//--------------------------------Second Row--------------------------------//
		 
		gc.anchor = GridBagConstraints.LINE_START;
		
		gc.gridx = 1; // row position first right in layout
		gc.gridy = 0;
		
		add(rowField, gc);
		
		gc.gridx = 1; // column position second in right layout 
		gc.gridy = 1;
		
		add(colField, gc);
		
		gc.gridx = 1; // cell size position third in right layout
		gc.gridy = 2;
		
		add(celField, gc);
		
		//--------------------------------Third Row--------------------------------//
		
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.weightx = 10; // high weight means all variables will be at top
		gc.weighty = 10; 
		gc.gridx = 0;
		gc.gridy = 3;
		gc.gridwidth = 100;
		gc.fill = GridBagConstraints.HORIZONTAL;
		add(genBtn, gc); // Adds button to the bottom of panel
		
		// USER INPUT // 
		
		genBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				int inputX = Integer.parseInt(rowField.getText()); // input of row
				int inputY = Integer.parseInt(colField.getText()); // input of col
				int inputC = Integer.parseInt(celField.getText()); // input of cellsize
				  
				MainGenerator.cellSize = inputC; // sets input to variables
				MainGenerator.sizeX = inputX;
				MainGenerator.sizeY = inputY;
								
				MazeArea ma = new MazeArea(); 
				
				Thread t = new Thread(ma); // Trying to restart thread on button press but doesn't work
				
				t.start();
			}
		});
	}
}
