package entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class Enemy extends CollisionObject {
    private double speed;

    public Enemy(double x, double y) {
        // Bring the Noise
        super(x, y, "graphics/enemy.png");

        this.speed = Math.random() / 2 + 0.1;
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
        y += speed * delta;
    }
}
