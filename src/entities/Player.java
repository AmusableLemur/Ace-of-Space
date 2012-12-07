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

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    @Override
    public boolean overlaps(CollisionObject o) {
        throw new UnsupportedOperationException("Not supported yet.");
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
        timeSinceFire += delta;
        Input input = gc.getInput();

        if (input.isKeyDown(Input.KEY_LEFT) && x > 0) {
            x -= delta * 0.5;
        }

        if (input.isKeyDown(Input.KEY_RIGHT) && (x + width) < gc.getWidth()) {
            x += delta * 0.5;
        }

        if (input.isKeyDown(Input.KEY_UP) && y > 0) {
            y -= delta * 0.2;
        }

        if (input.isKeyDown(Input.KEY_DOWN) && (y + height) < gc.getHeight()) {
            y += delta * 0.2;
        }

        if (timeSinceFire > 100) {
            bullets.add(new Bullet(x + width / 2 - 4, y, 0.9));
            timeSinceFire = 0;
        }

        Iterator i = bullets.iterator();

        while (i.hasNext()) {
            Bullet b = (Bullet)i.next();

            b.update(gc, delta);

            if (b.outsideOfScreen(gc)) {
                i.remove();
            }
        }
    }
}
