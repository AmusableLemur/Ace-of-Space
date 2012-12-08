package entities;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public abstract class GameObject extends Rectangle {
    protected Image graphic;

    public GameObject(double x, double y, String image) {
        super((int)x, (int)y, 0, 0);

        try {
            graphic = new Image(image);
        } catch (SlickException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }

        setWidth(graphic.getWidth());
        setHeight(graphic.getHeight());
    }

    public boolean outsideOfScreen(GameContainer gc) {
        if (getX() < -(getWidth() + 30)) {
            return true;
        }

        if (getY() < -(getHeight() + 30)) {
            return true;
        }

        if (getX() > gc.getWidth() + getWidth() + 30) {
            return true;
        }

        if (getY() > gc.getHeight() + getHeight() + 30) {
            return true;
        }

        return false;
    }

    public abstract void render(GameContainer gc, Graphics g);

    public abstract void update(GameContainer gc, int delta);
}
