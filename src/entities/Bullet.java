package entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class Bullet extends CollisionObject {
    private double speed;

    public Bullet(double x, double y, double speed) {
        super(x, y, 5, 5);

        this.speed = speed;
    }

    @Override
    public boolean overlaps(CollisionObject o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void render(GameContainer gc, Graphics g) {
        g.fillOval((int)x, (int)y, width, height);
    }

    @Override
    public void update(GameContainer gc, int delta) {
        y -= speed * delta;
    }
}
