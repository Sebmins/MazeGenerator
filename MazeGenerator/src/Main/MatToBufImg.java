package Main;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.Highgui;

	public class MatToBufImg{
		Mat mat;
		MatOfByte mob;
		String file;
		
		public MatToBufImg() {
		}
		public MatToBufImg(Mat amatrix, String fileExt) {
			mat = amatrix;
			file= fileExt;
		}
		public void setMatrix(Mat amatrix, String fileExt){
			mat = amatrix;
			file = fileExt;
			mob = new MatOfByte();
		}
		
		public BufferedImage getBufferedImage() {
			Highgui.imencode(file,mat,mob);
			byte[] byteArray = mob.toArray();
			BufferedImage buf = null;
			try {
				InputStream iS = new ByteArrayInputStream(byteArray);
				buf = ImageIO.read(iS);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return buf;
			
		}
	
}