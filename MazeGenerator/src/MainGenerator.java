import javax.swing.SwingUtilities;

public class MainGenerator {

	public static int sizeX;
	public static int sizeY;
	public static int cellSize;

	public static void main(String[] args) 
	{
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				sizeX = 19; // number of columns, must be even
				sizeY = 19; // number of rows, must be even
				cellSize =12; // how big each cell is
				Frame frame = new Frame(); 
				
			}
		});
		
		
	}

}
