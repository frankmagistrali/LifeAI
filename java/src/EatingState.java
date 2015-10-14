package src;

import com.sun.istack.internal.NotNull;

public class EatingState extends GrazerState {

    @Override
    public void updateGrazerState(@NotNull Grazer grazer) {
        super.updateGrazerState(grazer);
        WorldSpace space = grazer.getCurrentSpace();
        if (space.getGrass() >= 15) {
            grazer.eat(grazer.FEEDVALUE);
            space.setGrass(space.getGrass() - grazer.FEEDVALUE*2);
            space.setUpdateGraphics(true);
        } else if (space.getGrass() > 0) {
            grazer.eat(space.getGrass() / 2);
            space.setGrass(0);
            space.setUpdateGraphics(true);
        } else {
            grazer.setCurrentState(Grazer.WALK);
        }
    }
}
