package entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Explosion extends Image {
    private double x, y, time;

    public Explosion(double x, double y, double time) throws SlickException {
        super("graphics/laserGreenShot.png");

        this.x = x;
        this.y = y;
        this.time = time;
    }

    public double getTime() {
        return time;
    }

    public void update(GameContainer gc, int delta) {
        time -= delta;
    }

    public void render(GameContainer gc, Graphics g) {
        g.drawImage(this, (int)x - getWidth() / 2, (int)y - getHeight() / 2);
    }
}
