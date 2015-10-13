package src;

import apis.ICritter;
import lombok.Data;

@Data
public class Grazer implements ICritter {

    int     full;
    int     age;
    int     iPos;
    int     jPos;
    String  currentState;
    private boolean updateGraphics;
    boolean alive;
    String wandering = null;
    int breedTime;
    int sex;

    public Grazer(int i, int j, int s) {
        full = 30;
        age = 0;
        currentState = "walkingState";
        iPos = i;
        jPos = j;
        alive = true;
        breedTime = 20;
        sex = s;
    }
}
