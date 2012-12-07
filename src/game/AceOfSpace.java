package game;

import entities.Bullet;
import entities.Enemy;
import entities.Player;
import java.util.ArrayList;
import java.util.Iterator;
import org.newdawn.slick.*;

public class AceOfSpace extends BasicGame {
    private static final int width = 800, height = 600;
    private ArrayList<Enemy> enemies;
    private Player player;

    public AceOfSpace() {
        super("Ace of Space");
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        enemies = new ArrayList<>();
        player = new Player(gc);
    }

    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
        if (Math.random() < 0.1 && !gc.isPaused()) {
            enemies.add(new Enemy(gc));
        }

        Iterator<Enemy> i = enemies.iterator();

        while (i.hasNext()) {
            Enemy e = i.next();

            e.update(gc, delta);

            if (e.outsideOfScreen(gc)) {
                i.remove();
            }

            Iterator<Bullet> bi = player.getBullets().iterator();

            while (bi.hasNext()) {
                Bullet b = bi.next();

                if (e.overlaps(b)) {
                    i.remove();
                    bi.remove();
                }
            }

            if (e.overlaps(player)) {
                gc.pause();
            }
        }

        player.update(gc, delta);
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        for (Enemy e : enemies) {
            e.render(gc, g);
        }

        player.render(gc, g);
    }

    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new AceOfSpace());

        app.setDisplayMode(width, height, false);
        app.setMinimumLogicUpdateInterval(10);
        app.setMaximumLogicUpdateInterval(30);
        app.start();
    }
}
