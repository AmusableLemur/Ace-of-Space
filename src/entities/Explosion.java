package entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Explosion extends Image {
    private float time, x, y;

    public Explosion(float x, float y, float time) throws SlickException {
        super("graphics/laserGreenShot.png");

        this.x = x;
        this.y = y;
        this.time = time;
    }

    public float getTime() {
        return time;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void update(GameContainer gc, int delta) {
        setTime(getTime() - delta);
    }

    public void render(GameContainer gc, Graphics g) {
        g.drawImage(this, getX() - getWidth() / 2, getY() - getHeight() / 2);
    }

    protected void setTime(float time) {
        this.time = time;
    }
}
