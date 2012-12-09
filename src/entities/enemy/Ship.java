package entities.enemy;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

public class Ship extends Enemy {
    public Ship(GameContainer gc) throws SlickException {
        super(gc, "graphics/enemyShip.png");
    }
}
