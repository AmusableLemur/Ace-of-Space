package entities;

import java.util.ArrayList;
import java.util.Iterator;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class Player extends CollisionObject {
    private ArrayList<Bullet> bullets;
    private int timeSinceFire;

    public Player(GameContainer gc) {
        super(gc.getWidth() / 2 - 15, gc.getHeight() - 50, "graphics/ship.png");

        bullets = new ArrayList<>();
        timeSinceFire = 0;
    }

    protected void controls(GameContainer gc, int delta) {
        Input input = gc.getInput();

        if ((input.isKeyDown(Input.KEY_LEFT) || input.isControllerLeft(0)) && x > 0) {
            x -= delta * 0.5;
        }

        if ((input.isKeyDown(Input.KEY_RIGHT) || input.isControllerRight(0)) && (x + width) < gc.getWidth()) {
            x += delta * 0.5;
        }

        if ((input.isKeyDown(Input.KEY_UP) || input.isControllerUp(0)) && y > 0) {
            y -= delta * 0.2;
        }

        if ((input.isKeyDown(Input.KEY_DOWN) || input.isControllerDown(0)) && (y + height) < gc.getHeight()) {
            y += delta * 0.2;
        }
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    @Override
    public void render(GameContainer gc, Graphics g) {
        g.drawImage(graphic, (int)x, (int)y);

        for (Bullet b : bullets) {
            b.render(gc, g);
        }
    }

    @Override
    public void update(GameContainer gc, int delta) {
        controls(gc, delta);

        timeSinceFire += delta;

        if (timeSinceFire > 500) {
            bullets.add(new Bullet(x + width / 2 - 3, y, 0.9));
            timeSinceFire = 0;
        }

        Iterator<Bullet> i = bullets.iterator();

        while (i.hasNext()) {
            Bullet b = i.next();

            b.update(gc, delta);

            if (b.outsideOfScreen(gc)) {
                i.remove();
            }
        }
    }
}
