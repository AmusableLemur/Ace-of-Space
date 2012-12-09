package entities.enemy;

import entities.Player;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

public class Asteroid extends Enemy {
    private float horizontalSpeed, rotationSpeed;

    public Asteroid(GameContainer gc) throws SlickException {
        super(gc, "graphics/meteorSmall.png");
        setScore(20);

        rotationSpeed = (float)Math.random() * 6 - 3;
    }

    public Asteroid(GameContainer gc, float x, float y, float horizontalSpeed) throws SlickException {
        this(gc);
        setX((int)x);
        setY((int)y);

        this.horizontalSpeed = horizontalSpeed;
    }

    public float getHorizontalSpeed() {
        return horizontalSpeed;
    }

    public float getRotationSpeed() {
        return rotationSpeed;
    }

    @Override
    public void update(GameContainer gc, int delta, Player player) {
        super.update(gc, delta, player);
        getImage().rotate(getRotationSpeed());
        setX(getX() + getHorizontalSpeed());
    }
}
