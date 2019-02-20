import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class VariableDetails extends JPanel {
	public static int inputX;
	public static int inputY;
	public static int inputC;
	public static int valoop = 0;
	
	public VariableDetails() {
		
		setBorder(BorderFactory.createTitledBorder("Maze Variables"));
		
		JLabel rowLabel = new JLabel("Number of rows: "); // row label
		JLabel colLabel = new JLabel("Number of columns: "); // col label
//		JLabel celLabel = new JLabel("Cell size: "); // cell size label
		
		JTextField rowField = new JTextField(Integer.toString(MainGenerator.rowInput),10); // row input
		JTextField colField = new JTextField(Integer.toString(MainGenerator.colInput),10); // col input
//		JTextField celField = new JTextField("20",10); // cell size input
		
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
		
//		add(celLabel, gc);
				
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
		
//		add(celField, gc);
		
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
				
		ActionListener actList = new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				inputY = Integer.parseInt(rowField.getText()); // input of row
				inputX = Integer.parseInt(colField.getText()); // input of col
				
				if ( inputY >=5 && inputX>=5)
				{
					if (inputY%2!=0 && inputX%2!=0)
					{
						MazeArea.maloop = 1;
					}else {
						System.out.println("Has to be odd");
					}
				} else {
					System.out.println("Has to be 5 or greater");
				}

			}
		};
		
		genBtn.addActionListener(actList);
		rowField.addActionListener(actList);
		colField.addActionListener(actList);
//		celField.addActionListener(actList);
	}
}
