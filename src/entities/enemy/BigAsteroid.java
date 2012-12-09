package entities.enemy;

import entities.Player;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

public class BigAsteroid extends Enemy {
    private float rotationSpeed;

    public BigAsteroid(GameContainer gc) throws SlickException {
        super(gc, "graphics/meteorBig.png");
        setScore(40);

        rotationSpeed = (float)Math.random() * 4 - 2;
    }

    public float getRotationSpeed() {
        return rotationSpeed;
    }

    @Override
    public void update(GameContainer gc, int delta, Player player) {
        super.update(gc, delta, player);
        getImage().rotate(getRotationSpeed());
    }
}
