package entities.enemy;

import org.newdawn.slick.GameContainer;

public class Asteroid extends Enemy {
    private double xSpeed, rotationSpeed;

    public Asteroid(GameContainer gc) {
        super(gc, "graphics/meteorSmall.png");

        rotationSpeed = Math.random() * 6 - 3;
    }

    public Asteroid(GameContainer gc, double x, double y, double xSpeed) {
        this(gc);

        setX((int)x);
        setY((int)y);

        this.xSpeed = xSpeed;
    }

    @Override
    public void update(GameContainer gc, int delta) {
        super.update(gc, delta);
        graphic.rotate((float)rotationSpeed);

        setX(getX() + (int)xSpeed);
    }
}
