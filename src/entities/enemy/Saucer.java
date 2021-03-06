package entities.enemy;

import entities.Player;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

public class Saucer extends Enemy {
    public Saucer(GameContainer gc) throws SlickException {
        super(gc, "graphics/enemyUFO.png");
        setScore(100);
    }

    @Override
    public void update(GameContainer gc, int delta, Player player) {
        super.update(gc, delta, player);
        getImage().rotate(2);

        // TODO: Change to be actual angle towards player
        if (player.getCenterX() > getCenterX()) {
            setX(getX() + getSpeed() * delta / 4);
        } else {
            setX(getX() - getSpeed() * delta / 4);
        }
    }
}
