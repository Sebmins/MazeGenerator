import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class MainGenerator {

	public static int columns;
	public static int rows;
	public static int cellSize;
	public static int rowInput;
	public static int colInput;
	
	public static void main(String[] args) 
	{		
		
		JTextField rowJText =new JTextField("7");
		JTextField colJText =new JTextField("7");
		
		Object[] fields= {
				"Enter number of rows", rowJText,
				"Enter number of columns",colJText,
				};
		
		int opt = JOptionPane.showConfirmDialog(null,
				fields,"User Input Box",JOptionPane.OK_CANCEL_OPTION);
		
		if (opt == JOptionPane.CANCEL_OPTION)
		{
			System.exit(0);
		}
		
		if (opt == JOptionPane.OK_OPTION)
		{
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
