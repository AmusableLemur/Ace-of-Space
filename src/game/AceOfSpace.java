package game;

import entities.Bullet;
import entities.Enemy;
import entities.Player;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.*;

public class AceOfSpace extends BasicGame {
    private static final int GAME_OVER = -1;
    private static final int PAUSED = 0;
    private static final int PLAYING = 1;
    private static final int MENU = 2;

    private int state;
    private ArrayList<Enemy> enemies;
    private Input input;
    private Player player;

    public AceOfSpace() {
        super("Ace of Space");
    }

    public void gameOver(GameContainer gc, int delta) {
        if (input.isKeyPressed(input.KEY_SPACE)) {
            try {
                init(gc);
            } catch (SlickException ex) {
                Logger.getLogger(AceOfSpace.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            state = PLAYING;
        }
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        enemies = new ArrayList<>();
        input = gc.getInput();
        player = new Player(gc);
        state = PLAYING;
    }

    public void pause(GameContainer gc, int delta) {
        if (input.isKeyPressed(input.KEY_SPACE)) {
            state = PLAYING;
        }
    }

    public void play(GameContainer gc, int delta) throws SlickException {
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
                state = GAME_OVER;
            }
        }

        player.update(gc, delta);

        if (input.isKeyPressed(input.KEY_SPACE)) {
            state = PAUSED;
        }
    }

    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
        switch (state) {
            case GAME_OVER:
                gameOver(gc, delta);
                break;
            case PAUSED:
                pause(gc, delta);
                break;
            case PLAYING:
                play(gc, delta);
                break;
            case MENU:
                break;
        }
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

        app.setDisplayMode(800, 600, false);
        app.setMinimumLogicUpdateInterval(10);
        app.setMaximumLogicUpdateInterval(30);
        app.start();
    }
}
