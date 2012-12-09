package entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public abstract class GameObject extends Rectangle {
    private Image image;

    public GameObject(float x, float y, String image) throws SlickException {
        super(x, y, 0, 0);

        this.image = new Image(image);

        setWidth(this.image.getWidth());
        setHeight(this.image.getHeight());
    }

    public Image getImage() {
        return image;
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

    public abstract void render(GameContainer gc, Graphics g) throws SlickException;

    public void setImage(Image image) {
        this.image = image;
    }

    public abstract void update(GameContainer gc, int delta) throws SlickException;
}
