package entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Bullet extends GameObject {
    private float speed;

    public Bullet(float x, float y, float speed) throws SlickException {
        super(x, y, "graphics/laserGreen.png");
        setX(getX() - getImage().getWidth() / 2);

        this.speed = speed;
    }

    public float getSpeed() {
        return speed;
    }

    @Override
    public void render(GameContainer gc, Graphics g) {
        g.drawImage(getImage(), getX(), getY());
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    @Override
    public void update(GameContainer gc, int delta) {
        setY(getY() - getSpeed() * delta);
    }
}
