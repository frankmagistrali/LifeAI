package src;

import apis.ICritter;
import com.sun.istack.internal.NotNull;
import lombok.Data;

@Data
public class Grazer implements ICritter {

    public static RunningState   RUN    = new RunningState();
    public static EatingState    EAT    = new EatingState();
    public static WalkingState   WALK   = new WalkingState();
    public static WanderingState WANDER = new WanderingState();

    public static int FEEDVALUE = 7;

    private int         fullness;
    private int         age;
    private GrazerState currentState;
    private boolean     updateGraphics;
    private boolean     alive;
//    String wandering = null;
    private int         breedTime;
    private int         index; //TODO: I really would rather not have them know about the space their in... but maybe they need to?
    private WorldSpace  currentSpace;
    private int         sex;

    public Grazer(@NotNull final WorldSpace space, int s) {
        fullness = 30;
        age = 0;
        currentState = WALK;
        alive = true;
        breedTime = 20;
        sex = s;
        this.currentSpace = space;
    }

    @Override
    public Grazer update() {
        currentState.updateGrazerState(this);
        if (getAge() - getFullness() > 300 || getFullness() <= 0) {
            setAlive(false);
            System.out.println("died of: " + "age=" + getAge() + " fullness=" + getFullness());
        }
        fullness--;
        age++;

        if (getBreedTime() > 0) {
            breedTime--;
        }
        return alive ? this : null;
    }

    public void eat(int feedValue) {
        fullness += feedValue;
    }
}
