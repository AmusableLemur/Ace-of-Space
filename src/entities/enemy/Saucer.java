package entities.enemy;

import org.newdawn.slick.GameContainer;

public class Saucer extends Enemy {
    public Saucer(GameContainer gc) {
        super(gc, "graphics/enemyUFO.png");
    }

    @Override
    public void update(GameContainer gc, int delta) {
        super.update(gc, delta);
        graphic.rotate(2);
    }
}
