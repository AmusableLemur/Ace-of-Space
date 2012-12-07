package entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class Bullet extends GameObject {
    private double speed;

    public Bullet(double x, double y, double speed) {
        super(x, y, "graphics/laserGreen.png");

        this.speed = speed;
    }

    @Override
    public void render(GameContainer gc, Graphics g) {
        g.drawImage(graphic, (int)x, (int)y);
    }

    @Override
    public void update(GameContainer gc, int delta) {
        y -= speed * delta;
    }
}
