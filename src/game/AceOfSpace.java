package game;

import entities.*;
import java.util.ArrayList;
import org.newdawn.slick.*;

public class AceOfSpace extends BasicGame {
    private static final int width = 800, height = 600;
    private ArrayList<CollisionObject> objects;

    public AceOfSpace() {
        super("Ace of Space");
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        objects.add(new Player());
    }

    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
        for (CollisionObject o : objects) {
            o.update(gc, delta);
        }
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        for (CollisionObject o : objects) {
            o.render(gc, g);
        }
    }

    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new AceOfSpace());

        app.setDisplayMode(width, height, false);
        app.start();
    }
}
