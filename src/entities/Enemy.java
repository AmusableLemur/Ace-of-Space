package entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class Enemy extends GameObject {
    private double speed;

    public Enemy(GameContainer gc) {
        // Bring the Noise
        super(Math.random() * gc.getWidth(), -20, "graphics/enemyShip.png");

        this.speed = Math.random() / 2 + 0.1;
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
