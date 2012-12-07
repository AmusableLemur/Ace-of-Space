package entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public abstract class CollisionObject {
    protected double x, y;
    protected int width, height;

    public CollisionObject(double x, double y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public abstract boolean overlaps(CollisionObject o);

    public boolean outsideOfScreen(GameContainer gc) {
        if (x < -10) {
            return true;
        }

        if (y < -10) {
            return true;
        }

        if (x > gc.getWidth() + width + 10) {
            return true;
        }

        if (y > gc.getHeight() + height + 10) {
            return true;
        }

        return false;
    }

    public abstract void render(GameContainer gc, Graphics g);

    public abstract void update(GameContainer gc, int delta);
}
