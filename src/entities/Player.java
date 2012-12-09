package entities;

import java.util.concurrent.CopyOnWriteArrayList;
import org.newdawn.slick.*;

public class Player extends GameObject {
    private CopyOnWriteArrayList<Bullet> bullets;
    private int timeSinceFire;

    public Player(GameContainer gc) throws SlickException {
        super(gc.getWidth() / 2 - 15, gc.getHeight() - 80, "graphics/player.png");
        setWidth(getWidth() / 2);

        bullets = new CopyOnWriteArrayList<>();
        timeSinceFire = 0;
    }

    protected void controls(GameContainer gc, int delta) {
        Input input = gc.getInput();

        if ((input.isKeyDown(Input.KEY_LEFT) || input.isControllerLeft(0)) && getX() > 0) {
            setX((int)(getX() - delta * 0.5));
        }

        if ((input.isKeyDown(Input.KEY_RIGHT) || input.isControllerRight(0)) && (getX() + getWidth()) < gc.getWidth()) {
            setX((int)(getX() + delta * 0.5));
        }

        if ((input.isKeyDown(Input.KEY_UP) || input.isControllerUp(0)) && getHeight() > 0) {
            setY((int)(getY() - delta * 0.2));
        }

        if ((input.isKeyDown(Input.KEY_DOWN) || input.isControllerDown(0)) && (getY() + getHeight()) < gc.getHeight()) {
            setY((int)(getY() + delta * 0.2));
        }
    }

    public CopyOnWriteArrayList<Bullet> getBullets() {
        return bullets;
    }

    @Override
    public void render(GameContainer gc, Graphics g) {
        g.drawImage(getImage(), getX() - getImage().getWidth() / 4, getY());

        for (Bullet b : getBullets()) {
            b.render(gc, g);
        }
    }

    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
        controls(gc, delta);

        timeSinceFire += delta;

        if (timeSinceFire > 500) {
            Sound sound = new Sound("sound/laser.wav");
            timeSinceFire = 0;

            getBullets().add(new Bullet(getCenterX(), getY(), 0.9f));
            sound.play();
        }

        for (Bullet b : bullets) {
            b.update(gc, delta);

            if (b.outsideOfScreen(gc)) {
                getBullets().remove(b);
            }
        }
    }
}
