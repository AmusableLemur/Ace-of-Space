package entities;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public abstract class CollisionObject {
    protected Image graphic;
    protected double x, y;
    protected int width, height;

    public CollisionObject(double x, double y, String image) {
        this.x = x;
        this.y = y;

        try {
            graphic = new Image(image);
        } catch (SlickException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.width = graphic.getWidth();
        this.height = graphic.getHeight();
    }

    public abstract boolean overlaps(CollisionObject o);

    public boolean outsideOfScreen(GameContainer gc) {
        if (x < -30) {
            return true;
        }

        if (y < -30) {
            return true;
        }

        if (x > gc.getWidth() + width + 30) {
            return true;
        }

        if (y > gc.getHeight() + height + 30) {
            return true;
        }

        return false;
    }

    public abstract void render(GameContainer gc, Graphics g);

    public abstract void update(GameContainer gc, int delta);
}
