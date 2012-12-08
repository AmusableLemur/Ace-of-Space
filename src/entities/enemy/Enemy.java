package entities.enemy;

import entities.GameObject;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public abstract class Enemy extends GameObject {
    private double speed;

    public Enemy(GameContainer gc, String image) {
        // Bring the Noise
        super(Math.random() * gc.getWidth(), -20, image);

        this.speed = Math.random() / 2 + 0.1;
    }

    @Override
    public void render(GameContainer gc, Graphics g) {
        g.drawImage(graphic, getX(), getY());
    }

    @Override
    public void update(GameContainer gc, int delta) {
        setY((int)(getY() + speed * delta));
    }
}
