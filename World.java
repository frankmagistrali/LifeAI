import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

import javax.swing.plaf.SliderUI;

/** Demonstrate basic AWT drawing with Graphics. **/
public class World extends Applet implements WorldInterface {
	Color darkGreen = new Color(0, 100, 0);
	Color lightGreen = new Color(0, 255, 0);
	Color Green = new Color(0, 175, 0);

	public Grazer[] getGrazers() {
		return grazers;
	}

	public void setGrazers(Grazer[] grazers) {
		this.grazers = grazers;
	}

	Color freshGreen = new Color(0, 255, 0);
	Color grass;
	static int WATER = -1;
	// static int n = 100;
	private static int[][] land = new int[n][n];
	int start = 100;
	private static Grazer[] grazers = new Grazer[300];
	 private static int numGrazers = 0;

	// GrazerState grazeState=new GrazerState();

	public int[][] getLand() {
		return land;
	}

	public void setLand(int[][] land) {
		this.land = land;
	}

	public int getNumGrazers() {
		return numGrazers;
	}

	public void setNumGrazers(int numGrazers) {
		this.numGrazers = numGrazers;
	}

	public boolean checkWater(int i, int j) {
		if (i < 99 && land[i + 1][j] == WATER) {
			return true;
		} else if (i > 0 && land[i - 1][j] == WATER) {
			return true;
		} else if (j < 99 && land[i][j + 1] == WATER) {
			return true;
		} else if (j > 0 && land[i][j - 1] == WATER) {
			return true;
		} else {
			return false;
		}
	}

	public String pickADirection() {
		int direction = (int) (8 * Math.random());

		switch (direction) {
		case 0:
			return "n";
		case 1:
			return "ne";
		case 2:
			return "e";
		case 3:
			return "se";
		case 4:
			return "s";
		case 5:
			return "sw";
		case 6:
			return "w";
		case 7:
			return "nw";
		}
		return null;
	}

	public int[][] grow() throws IOException {

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (land[i][j] > 0 && land[j][j] <= 50) {
					boolean water = checkWater(i, j);
					if (land[i][j] == 1) {
						land[i][j] = 2;
					} else if (land[i][j] > 1 && land[i][j] <= 15) {
						if (water) {
							land[i][j] += 5;
						} else {
							land[i][j]++;
						}
					} else if (land[i][j] > 15 && land[i][j] <= 30) {
						if (water) {
							land[i][j] += 5;
						} else {
							land[i][j]++;
						}
					} else if (land[i][j] <= 50 || water) {
						double breedChance = 100 * Math.random() - land[i][j];
						if (breedChance > 48) {
							String breedDir = pickADirection();
							// ////there is something not quite right here, even
							// if next to water it wont always breed
							// ////need to make sure the spot is open, if not,
							// choose a new one
							if (i > 0 && land[i - 1][j] == 0
									&& (breedDir == "w" || water)) {
								land[i - 1][j] = 1;
							} else if (i < n - 1 && land[i + 1][j] == 0
									&& (breedDir == "e" || water)) {
								land[i + 1][j] = 1;
							}

							else if (j > 0 && land[i][j - 1] == 0
									&& (breedDir == "n" || water)) {
								land[i][j - 1] = 1;
							}

							else if (j < n - 1 && land[i][j + 1] == 0
									&& (breedDir == "s" || water)) {
								land[i][j + 1] = 1;
							} else if (i > 0 && j < n - 1
									&& land[i - 1][j + 1] == 0
									&& (breedDir == "sw " || water)) {
								land[i - 1][j + 1] = 1;
							} else if (i > 0 && j > 0
									&& land[i - 1][j - 1] == 0
									&& (breedDir == "nw" || water)) {
								land[i - 1][j - 1] = 1;
							} else if (i < n - 1 && j < n - 1
									&& land[i + 1][j + 1] == 0
									&& (breedDir == "se" || water)) {
								land[i + 1][j + 1] = 1;
							} else if (i < n - 1 && j > 0
									&& land[i + 1][j - 1] == 0
									&& (breedDir == "ne" || water)) {
								land[i + 1][j - 1] = 1;
							}
							if (land[i][j] < 50) {
								land[i][j]++;
							}
						}
					}
					if (!water) {
						double die = (100 * Math.random());
						if (die > 99.99) {
							land[i][j] = 0;
						}
					}
				}

			}
		}

		repaint();

		return land;
	}

	public void setup() {
		pond(43, 34, 15);
		pond(25, 52, 12);
		pond(32, 60, 14);
		pond(35, 70, 18);
		pond(38, 80, 10);
		pond(87, 93, 10);

		for (int i = 0; i < 40; i++) {
			int iStart = (int) ((n - 1) * Math.random());
			int jStart = (int) ((n - 1) * Math.random());
			if (land[iStart][jStart] != -1) {
				land[iStart][jStart] += 30;
			}
		}
		//
		// Grazer g1=new Grazer(60,58);
		// Grazer g2=new Grazer(60,42);
		// Grazer g3=new Grazer(12,12);
		// Grazer g4=new Grazer(57,48);
		//
		// grazers[0]=g1;
		// grazers[1]=g2;
		// grazers[2]=g3;
		// grazers[3]=g4;
		newGrazer(60, 58);
		newGrazer(60, 58);
		newGrazer(69, 58);
		newGrazer(50, 58);
		newGrazer(60, 48);
		newGrazer(60, 40);
setLand(land);
setGrazers(grazers);
	}

	public void pond(int x, int y, int size) {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (distance(i, j, x, y) < size) {
					land[i][j] = WATER;
				}
			}
		}
	}

	public double distance(int i, int j, int x, int y) {
		return Math.sqrt((i - x) * (i - x) + (j - y) * (j - y));
	}

	public double distance2(int i, int j, int x, int y) {
		return (i - x) * (i - x) + (j - y) * (j - y);
	}

	public void paint(Graphics g) {

		if (start==100) {
			setup();
			start--;
		}
		// Get the drawing area
		setSize(size, size);
		// int n = 100;
		int dY = getSize().height;
		int dX = getSize().width;
		int midY = dY / 2;
		int midX = dX / 2;
		int x = dX / n;
		int y = dY / n;

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (land[i][j] > 0) {
					grass = new Color(0, 255 - land[i][j] * 3, 0);
					g.setColor(grass);
				} else if (land[i][j] == WATER) {
					g.setColor(Color.blue);
				} else {
					g.setColor(Color.yellow);

				}
				g.fillRect(i * x, j * y, 1 * x, 1 * y);
			}
		}
		if(start==0){
		for (int i = 0; i < numGrazers; i++) {
			if (grazers[i].alive) {
			grazeState.update(grazers[i]);
			if (grazers[i].sex==0){
				g.setColor(Color.CYAN);
			}else{
			g.setColor(Color.red);
			}
			g.fillOval(grazers[i].iPos * x, grazers[i].jPos * y, x, y);
			}
		}
		grazerCleanup();
		}else{
			start--;
		}
		// g.fillOval(0, 0, 100, 100);
		//		
		// try {
		// if( System.in.read()!=-2828){
		// }
		// } catch (IOException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		// System.out.println("waiting");

		// System.out.println("in world: "+land[grazers[0].iPos][grazers[0].jPos]);
		try {
			land = grow();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// try{
		// //do what you want to do before sleeping
		// Thread.currentThread().sleep(1000);//sleep for 1000 ms
		// //do what you want to do after sleeptig
		// }
		// catch(InterruptedException ie){
		// //If this thread was intrrupted by nother thread
		// }

	}

	public void newGrazer(int i, int j) {
		if (numGrazers < 300) {
			int sex=(int) (Math.random()*2);
			Grazer g5 = new Grazer(i, j,sex);
			grazers[numGrazers] = g5;
			numGrazers++;
			System.out.println("breeding!" + " " + numGrazers);
		}

	}
	public void grazerCleanup(){
		for (int i = 0; i < numGrazers; i++) {
			if(!grazers[i].alive){
				grazers[i]=grazers[numGrazers-1];
				numGrazers--;
			}
		}
	}
}