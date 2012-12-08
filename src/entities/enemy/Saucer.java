package entities.enemy;

import entities.Player;
import org.newdawn.slick.GameContainer;

public class Saucer extends Enemy {
    public Saucer(GameContainer gc) {
        super(gc, "graphics/enemyUFO.png");

        setScore(100);
    }
    
    @Override
    public void update(GameContainer gc, int delta, Player player) {
        super.update(gc, delta, player);
        graphic.rotate(2);

        // TODO: Change to be actual angle towards player
        if (player.getX() > getX()) {
            setX(getX() + (float)speed * delta / 4);
        } else {
            setX(getX() - (float)speed * delta / 4);
        }
    }
}
