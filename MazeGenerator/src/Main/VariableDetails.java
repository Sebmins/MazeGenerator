package Main;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.opencv.core.Core;

public class VariableDetails extends JPanel {
	public static int inputX;
	public static int inputY;
	public static int inputC;
	public static int valoop = 0;
	public static int raloop = 0;
	static JTextField waitField = new JTextField("1");
	
	public VariableDetails() {

		
		setBorder(BorderFactory.createTitledBorder("Maze Variables"));
		
		JLabel rowLabel = new JLabel("Number of rows: "); // row label
		JLabel colLabel = new JLabel("Number of columns: "); // col label
		JLabel waitLabel = new JLabel("Wait time: "); // col label
		
		JTextField rowField = new JTextField(Integer.toString(MainGenerator.rowInput),10); // row input
		JTextField colField = new JTextField(Integer.toString(MainGenerator.colInput),10); // col input
		
		JButton genBtn = new JButton("Generate"); 
		JButton camActBtn = new JButton("Activate Cam");
		JButton camDeBtn = new JButton("Deactivate Cam");
		JButton robotBtn = new JButton("Robot Go");
		JTextField robotDist = new JTextField("1600",10);
		JLabel blank = new JLabel("      ");
		
		setLayout(new GridBagLayout());
		
		GridBagConstraints gc = new GridBagConstraints();
		
		gc.anchor = GridBagConstraints.LINE_END; // Adds to end of frame layout
		
		gc.gridx = 0; // row position first left in layout
		gc.gridy = 0;
		
//		gc.weightx = 1;
		gc.weighty = 1;
		
		add(rowLabel, gc);
		
		gc.gridy = 1;
		
		add(colLabel, gc);
				
		//--------------------------------Second Row--------------------------------//
		 
		gc.anchor = GridBagConstraints.LINE_START;
		
		gc.gridx = 1; // row position first right in layout
		gc.gridy = 0;
		
		add(rowField, gc);
		 
		gc.gridy = 1;
		
		add(colField, gc);
		
		gc.gridy = 2;
		
		//--------------------------------RadioButtons--------------------------------//

		gc.gridx = 0; // cell size position third in left layout
		gc.gridy = 2;
		gc.gridwidth = 100;
		
		add(MainGenerator.algoDFS, gc);
		
		gc.gridy = 3;
		
		add(MainGenerator.algoBFS, gc);
		
		gc.gridy = 4;
		
		add(MainGenerator.algoWallF, gc);
		
		gc.gridy = 5;
		
		add(blank, gc);
		
		//--------------------------------Gen Button--------------------------------//
		
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
//		gc.weightx = 20; // high weight means all variables will be at top
		gc.gridy = 6;
		gc.gridwidth = 100;
		gc.fill = GridBagConstraints.HORIZONTAL;
		add(genBtn, gc); // Adds button to the bottom of panel
	
		ActionListener actGen = new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{

				double inputW = Double.parseDouble(VariableDetails.waitField.getText());
				MazeArea.waittime2 = inputW;
				
				if(MainGenerator.algoDFS.isSelected()) {
					MainGenerator.dfs = true;
					MainGenerator.bfs = false;
					MainGenerator.wallF = false;
				}
				
				if(MainGenerator.algoBFS.isSelected()) {
					MainGenerator.dfs = false;
					MainGenerator.bfs = true;
					MainGenerator.wallF = false;
				}
				
				if(MainGenerator.algoWallF.isSelected()) {
					MainGenerator.dfs = false;
					MainGenerator.bfs = false;
					MainGenerator.wallF = true;
				}
				
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

//				waitField.equals("1");
			}
		};

		genBtn.addActionListener(actGen);
		rowField.addActionListener(actGen);
		colField.addActionListener(actGen);
		
		//--------------------------------- Wait Time -----------------------------------//
		
//		gc.anchor = GridBagConstraints.LINE_END; // Adds to end of frame layout
		
		gc.gridx = 0; // row position first left in layout
		gc.gridy = 7;
		
//		gc.weightx = 1;
		gc.weighty = 1;
		
		add(waitLabel, gc);
		
//		gc.anchor = GridBagConstraints.LINE_START;
		gc.weighty = 60; 
		gc.gridx = 1; // row position first right in layout
		gc.gridy = 7;
		
		add(waitField, gc);
		
		
		
		//--------------------------------Cam Deactivate--------------------------------//
		
//		gc.weightx = 80; // high weight means all variables will be at top
		
		gc.weighty = 0.2; 
		
		gc.anchor = GridBagConstraints.LAST_LINE_END;
		gc.gridy = 8;
		gc.gridx = 0;
		
		
		add(camActBtn, gc);
		
		ActionListener actCam = new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				MazeArea.caloop=1;
			}
		};
		
		camActBtn.addActionListener(actCam);
		
		//--------------------------------Cam Activate--------------------------------//
		
		gc.weighty = 0.5; 
		gc.anchor = GridBagConstraints.LAST_LINE_START;
		gc.gridy = 9;
		add(camDeBtn, gc);
		
		ActionListener deCam = new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("caploop2");
				MazeArea.caloop=2;
			}
		};
		
		camDeBtn.addActionListener(deCam);

		//--------------------------------Robot Go--------------------------------//
		
		
		gc.weighty = 0.5; 
		gc.anchor = GridBagConstraints.LAST_LINE_START;
		gc.gridy = 10;
		add(robotBtn, gc);
		
		ActionListener actRob = new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				int dist = Integer.parseInt(robotDist.getText());
				if(raloop == 0) {
					raloop = 1;
					Thread thread = new Thread(new Runnable(){
						public void run()
						{
							Navigation.finchMovement(Navigation.q, dist);
						}
					});
					thread.start();
					raloop = 0;
				}
						
						
				
			}
		};
		robotBtn.addActionListener(actRob);
		
		
		gc.anchor = GridBagConstraints.LAST_LINE_START;
		gc.gridy = 11;
		add(robotDist, gc);
	}
}
