package src;

import apis.IGrazerState;
import com.sun.istack.internal.NotNull;
import lombok.Data;

@Data
public class GrazerState implements IGrazerState {

    @Override
    public void updateGrazerState(@NotNull Grazer grazer) {
        if (grazer.isAlive()) {

        }
    }


    //    void GrazerState(Grazer grazer) {
//
//        if (grazer.currentState == "eatingState") {
//            eat.EatingState(grazer);
//        } else if (grazer.currentState == "runningState") {
//            run.RunningState(grazer);
//        } else if (grazer.currentState == "walkingState") {
//            walk.WalkingState(grazer);
//        } else if (grazer.currentState == "wanderingState") {
//            wander.WanderingState(grazer, grazer.wandering);
//        }
//    }

}
