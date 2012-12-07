package entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class Player extends CollisionObject {
    public Player() {
        super(10, 10, 20, 20);
    }

    @Override
    public boolean overlaps(CollisionObject o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void render(GameContainer gc, Graphics g) {
        g.fillRect((int)x, (int)y, width, height);
    }

    @Override
    public void update(GameContainer gc, int delta) {
        x += delta * 0.1;
    }
}
