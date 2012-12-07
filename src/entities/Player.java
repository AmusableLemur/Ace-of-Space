package entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class Player extends CollisionObject {
    public Player(GameContainer gc) {
        super(gc.getWidth() / 2 - 15, gc.getHeight() - 50, 30, 20);
    }

    @Override
    public boolean overlaps(CollisionObject o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void render(GameContainer gc, Graphics g) {
        g.fillRect((int)x, (int)y, width, height);
    }

    @Override
    public void update(GameContainer gc, int delta) {
        Input input = gc.getInput();

        if (input.isKeyDown(Input.KEY_LEFT)) {
            x -= delta * 0.5;
        }

        if (input.isKeyDown(Input.KEY_RIGHT)) {
            x += delta * 0.5;
        }

        if (input.isKeyDown(Input.KEY_UP)) {
            y -= delta * 0.2;
        }

        if (input.isKeyDown(Input.KEY_DOWN)) {
            y += delta * 0.2;
        }
    }
}
