package Main;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class MainGenerator {

	public static int columns;
	public static int rows;
	public static int cellSize;
	public static int rowInput;
	public static int colInput;
	public static boolean dfs = false;
	public static boolean bfs = false;
	public static boolean wallF = false;
	

	public static JRadioButton algoDFS = new JRadioButton("Depth First Search",true);
	public static JRadioButton algoBFS = new JRadioButton("Breadth First Search");
	public static JRadioButton algoWallF = new JRadioButton("Left Wall Follower");
	
	public static void main(String[] args) 
	{		
		JTextField rowJText =new JTextField("7");
		JTextField colJText =new JTextField("7");

		ButtonGroup group = new ButtonGroup();
		group.add(algoDFS);
		group.add(algoBFS);
		group.add(algoWallF);

		
		Object[] fields= {
				"Enter number of rows", rowJText,
				"Enter number of columns",colJText,
				algoDFS,algoBFS,algoWallF
				};
		
		int opt = JOptionPane.showConfirmDialog(null,
				fields,"User Input Box",JOptionPane.OK_CANCEL_OPTION);
		
		if (opt == JOptionPane.CANCEL_OPTION)
			System.exit(0);
		
		
		if (opt == JOptionPane.OK_OPTION)
		{
			if(algoDFS.isSelected())
				dfs = true;
			
			if(algoBFS.isSelected())
				bfs = true;
			
			if(algoWallF.isSelected())
				wallF = true;
			
			rowInput = Integer.parseInt(rowJText.getText());
			colInput = Integer.parseInt(colJText.getText());
			
			if ( rowInput >=5 && colInput>=5)
			{
				if (rowInput%2!=0 && colInput%2!=0)
				{
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							rows = rowInput; // number of columns, must be even
							columns = colInput; // number of rows, must be even
							Frame frame = new Frame(); 
						}
					});
				}else {
					System.out.println("Has to be odd"); 
				}
			} else {
				System.out.println("Has to be 5 or greater");
			}
		}
	}
}
