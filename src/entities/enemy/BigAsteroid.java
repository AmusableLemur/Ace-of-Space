package entities.enemy;

import entities.Player;
import org.newdawn.slick.GameContainer;

public class BigAsteroid extends Enemy {
    private double rotationSpeed;

    public BigAsteroid(GameContainer gc) {
        super(gc, "graphics/meteorBig.png");

        rotationSpeed = Math.random() * 4 - 2;
    }

    @Override
    public void update(GameContainer gc, int delta, Player player) {
        super.update(gc, delta, player);
        graphic.rotate((float)rotationSpeed);
    }
}
