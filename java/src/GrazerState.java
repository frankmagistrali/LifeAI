package src;

public class GrazerState {

    RunningState   run    = new RunningState();
    EatingState    eat    = new EatingState();
    WalkingState   walk   = new WalkingState();
    WanderingState wander = new WanderingState();

    void update(Grazer g) {
        if (g.alive) {
            if (g.age - g.full > 300 || g.full <= 0) {
                g.alive = false;
                System.out.println("died of: " + "age=" + g.age + " full=" + g.full);
            }
            GrazerState(g);
            g.full--;
            g.age++;
            if (g.breedTime > 0) {
                g.breedTime--;
            }
        }
    }

    void GrazerState(Grazer g) {

        if (g.currentState == "eatingState") {
            eat.EatingState(g);
        } else if (g.currentState == "runningState") {
            run.RunningState(g);
        } else if (g.currentState == "walkingState") {
            walk.WalkingState(g);
        } else if (g.currentState == "wanderingState") {
            wander.WanderingState(g, g.wandering);
        }
    }

}
