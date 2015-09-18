import java.io.IOException;


public interface WorldInterface {
	int size=700;
	static int n =size/6;
int[][] grow() throws IOException;
double distance(int i, int j, int x, int y);
int[][] land= new int[n][n];
GrazerState grazeState=new GrazerState();
//Grazer[] grazers=new Grazer[100];
public int hat=8;

}
