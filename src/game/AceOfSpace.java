package game;

import entities.CollisionObject;
import entities.Enemy;
import entities.Player;
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
        objects = new ArrayList<CollisionObject>();
        objects.add(new Player(gc));
    }

    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
        if (Math.random() < 0.1) {
            objects.add(new Enemy(Math.random() * gc.getWidth(), -20));
        }

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
        app.setMinimumLogicUpdateInterval(10);
        app.setMaximumLogicUpdateInterval(30);
        app.start();
    }
}
