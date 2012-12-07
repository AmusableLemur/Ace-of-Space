package entities.enemy;

import org.newdawn.slick.GameContainer;

public class Asteroid extends Enemy {
    private double rotationSpeed;

    public Asteroid(GameContainer gc) {
        super(gc, "graphics/meteorSmall.png");

        rotationSpeed = Math.random() * 3;
    }

    @Override
    public void update(GameContainer gc, int delta) {
        super.update(gc, delta);
        graphic.rotate((float)rotationSpeed);
    }
}
