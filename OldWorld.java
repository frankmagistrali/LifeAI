import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

/** Demonstrate basic AWT drawing with Graphics. **/
public class OldWorld extends Applet {
	Color darkGreen = new Color(0, 100, 0);
	Color lightGreen = new Color(0, 255, 0);
	Color Green = new Color(0, 175, 0);
	Color freshGreen = new Color(0, 255, 0);
	static int n = 100;
	Color[][] land = new Color[n][n];
	boolean start = true;

	public boolean checkWater(int i, int j) {
		if (i < 99 && land[i + 1][j] == Color.blue) {
			return true;
		} else if (i > 0 && land[i - 1][j] == Color.blue) {
			return true;
		} else if (j < 99 && land[i][j + 1] == Color.blue) {
			return true;
		} else if (j > 0 && land[i][j - 1] == Color.blue) {
			return true;
		} else {
			return false;
		}
	}

	public Color[][] grow() throws IOException {
		//	
		// if( System.in.read()!=-2828){
		// }
		// System.out.println("here");
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (land[i][j] == lightGreen || land[i][j] == freshGreen
						|| land[i][j] == Green || land[i][j] == darkGreen) {
					boolean water=checkWater(i,j);
					if (land[i][j] == freshGreen) {
						land[i][j] = lightGreen;
					} else if (land[i][j] == lightGreen) {
						if(water){
							land[i][j]=darkGreen;
						}else{
						land[i][j] = Green;
						}
					} else if (land[i][j] == Green) {
						land[i][j] = darkGreen;
					} else {
						double breed = 100 * Math.random();
						if (i != 0 && land[i - 1][j] == null && (breed >= 80 || water)) {
							land[i - 1][j] = lightGreen;
						} 
						else if (i != 99 && land[i+1][j] == null && (breed < 80 && breed >= 60 || water)) {
							land[i + 1][j] = freshGreen;
						}

						else if (j != 0 && land[i][j-1] == null && (breed < 60 && breed >= 40 || water)) {
							land[i][j - 1] = lightGreen;
						}

						else if (j != 99 && land[i][j+1] == null && (breed < 40 && breed >= 20||water)) {
							land[i][j + 1] = freshGreen;

						}

					}
				}

			}
		}

		repaint();

		return land;
	}

	public void setup() {
		land[50][50] = lightGreen;
pond(33,24,10);
	}
public void pond(int x, int y, int size){
	for (int i = 0; i < n; i++) {
		for (int j = 0; j < n; j++) {
			if(distance(i,j,x,y)<size){
			land[i][j] = Color.blue;
			}
		}

	}
}
public double distance(int i, int j, int x, int y){
	return Math.sqrt((i-x)*(i-x)+(j-y)*(j-y));
}
	public void draw(Graphics g) {

	}

	public void paint(Graphics g) {

		if (start) {
			setup();

			start = false;
		}
		// Get the drawing area
		setSize(700, 700);
		// int n = 100;
		int dY = getSize().height;
		int dX = getSize().width;
		int midY = dY / 2;
		int midX = dX / 2;
		int x = dX / n;
		int y = dY / n;

		g.setColor(Color.black);
		for (int i = 0; i < n; i++) {
			g.drawLine((i + 1) * x, 0, (i + 1) * x, dY);
			g.drawLine(0, (i + 1) * y, dX, (i + 1) * y);
		}

		// g.fillRect(30*x, 30*y, 3*x, 3*y);
		/**
		 * g.setColor (Color.red); // Set current drawing color // Draw a
		 * rectangle centered at the mid-point g.drawRect
		 * (midX-22,midY-22,44,44); // Set a new drawing color g.setColor
		 * (Color.green); // Fill a rectangle centered at the mid-point // Put
		 * it within the previous rectangle so that // border shows. g.fillRect
		 * (midX-21,midY-21,42,42); // Set a new drawing color g.setColor
		 * (Color.green.darker().darker()); // Draw a circle around the
		 * mid-point g.drawOval (midX-20,midY-21,40,40);
		 **/

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (land[i][j] != null) {
					g.setColor(land[i][j]);
				} else {
					g.setColor(Color.yellow);
				}
				g.fillRect(i * x, j * y, 1 * x, 1 * y);
			}

		}

		try {
			land = grow();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}