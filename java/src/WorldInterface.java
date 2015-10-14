package src;

import java.io.IOException;


public interface WorldInterface {

    int size = 700;


    void grow() throws IOException;

    double distance(int i, int j, int x, int y);

//    int[][]     land       = new int[worldSizeLength][worldSizeLength];
    //Grazer[] grazers=new Grazer[100];
    public int hat = 8;

}
