package entities.enemy;

import entities.GameObject;
import entities.Player;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public abstract class Enemy extends GameObject {
    protected double speed;
    private int score;

    public Enemy(GameContainer gc, String image) {
        // Bring the Noise
        super(Math.random() * gc.getWidth(), -20, image);

        this.speed = Math.random() / 2 + 0.1;

        setY(-graphic.getHeight() - 5);
    }

    public int getScore() {
        return score;
    }

    @Override
    public void render(GameContainer gc, Graphics g) {
        g.drawImage(graphic, getX(), getY());
    }

    protected void setScore(int score) {
        this.score = score;
    }

    @Override
    public void update(GameContainer gc, int delta) {
        setY((int)(getY() + speed * delta));
    }

    public void update(GameContainer gc, int delta, Player player) {
        update(gc, delta);
    }
}
