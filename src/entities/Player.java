package entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class Player implements CollisionObject {
    private double x, y;
    private int width, height;

    public Player() {
        x = 10;
        y = 10;
        width = 20;
        height = 20;
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
