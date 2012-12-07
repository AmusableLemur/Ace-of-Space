package entities;

import org.newdawn.slick.*;

public interface CollisionObject {
    public boolean overlaps(CollisionObject o);

    public void render(GameContainer gc, Graphics g);

    public void update(GameContainer gc, int delta);
}
