package entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class Bullet extends CollisionObject {
    private double speed;

    public Bullet(double x, double y, double speed) {
        super(x, y, "graphics/player_bullet.png");

        this.speed = speed;
    }

    @Override
    public boolean overlaps(CollisionObject o) {
        throw new UnsupportedOperationException("Not supported yet.");
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
