package entities.enemy;

import org.newdawn.slick.GameContainer;

public class BigAsteroid extends Enemy {
    private double rotationSpeed;

    public BigAsteroid(GameContainer gc) {
        super(gc, "graphics/meteorBig.png");

        rotationSpeed = Math.random() * 4 - 2;

        setY(-graphic.getHeight() - 5);
    }

    @Override
    public void update(GameContainer gc, int delta) {
        super.update(gc, delta);
        graphic.rotate((float)rotationSpeed);
    }
}
