package entities.enemy;

import entities.GameObject;
import entities.Player;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public abstract class Enemy extends GameObject {
    private float speed;
    private int score;

    public Enemy(GameContainer gc, String image) throws SlickException {
        // Bring the Noise
        super((float)Math.random() * gc.getWidth(), -20, image);
        setY(-(getImage().getHeight()) - 5);

        speed = (float)Math.random() / 2 + 0.1f;
    }

    public int getScore() {
        return score;
    }

    public float getSpeed() {
        return speed;
    }

    @Override
    public void render(GameContainer gc, Graphics g) {
        g.drawImage(getImage(), getX(), getY());
    }

    protected void setScore(int score) {
        this.score = score;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    @Override
    public void update(GameContainer gc, int delta) {
        setY((int)(getY() + getSpeed() * delta));
    }

    public void update(GameContainer gc, int delta, Player player) {
        update(gc, delta);
    }
}
