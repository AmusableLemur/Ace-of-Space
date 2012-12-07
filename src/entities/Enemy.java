package entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class Enemy implements CollisionObject {
    public Enemy() {
        // Bring the Noise
    }

    @Override
    public boolean overlaps(CollisionObject o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void render(GameContainer gc, Graphics g) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(GameContainer gc, int delta) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
