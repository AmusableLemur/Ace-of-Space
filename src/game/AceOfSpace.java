package game;

import entities.Bullet;
import entities.Enemy;
import entities.Player;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;

public class AceOfSpace extends BasicGame {
    private static final int STATE_GAME_OVER = -1;
    private static final int STATE_PAUSED = 0;
    private static final int STATE_PLAYING = 1;
    private static final int STATE_MENU = 2;

    private int score, state;
    private ArrayList<Enemy> enemies;
    private Input input;
    private Player player;
    private UnicodeFont largeText, smallText;

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

            state = STATE_PLAYING;
        }
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        enemies = new ArrayList<>();
        input = gc.getInput();
        player = new Player(gc);
        state = STATE_PLAYING;
        score = 0;
        smallText = new UnicodeFont("graphics/apache.ttf", 32, false, false);
        largeText = new UnicodeFont("graphics/apache.ttf", 84, false, false);

        smallText.addAsciiGlyphs();
        smallText.getEffects().add(new ColorEffect(java.awt.Color.white));
        smallText.loadGlyphs();

        largeText.addAsciiGlyphs();
        largeText.getEffects().add(new ColorEffect(java.awt.Color.white));
        largeText.loadGlyphs();

        gc.setDefaultFont(smallText);
        gc.setShowFPS(false);
    }

    public void pause(GameContainer gc, int delta) {
        if (input.isKeyPressed(input.KEY_SPACE)) {
            state = STATE_PLAYING;
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

                    score += 10;
                }
            }

            if (e.overlaps(player)) {
                state = STATE_GAME_OVER;
            }
        }

        player.update(gc, delta);

        if (input.isKeyPressed(input.KEY_SPACE)) {
            state = STATE_PAUSED;
        }
    }

    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
        switch (state) {
            case STATE_GAME_OVER:
                gameOver(gc, delta);
                break;
            case STATE_PAUSED:
                pause(gc, delta);
                break;
            case STATE_PLAYING:
                play(gc, delta);
                break;
            case STATE_MENU:
                break;
        }
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        for (Enemy e : enemies) {
            e.render(gc, g);
        }

        player.render(gc, g);

        String infoText = "Score: " + score;
        smallText.drawString(10, 10, infoText);

        switch (state) {
            case STATE_GAME_OVER:
                String title = "Game Over";
                String subtitle = "Press space to restart";

                largeText.drawString(
                        gc.getWidth() / 2 - largeText.getWidth(title) / 2,
                        gc.getHeight() / 2 - 60,
                        title
                    );

                smallText.drawString(
                        gc.getWidth() / 2 - smallText.getWidth(subtitle) / 2,
                        gc.getHeight() / 2 + 20,
                        subtitle
                    );

                break;
        }
    }

    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new AceOfSpace());

        app.setDisplayMode(800, 600, false);
        app.setMinimumLogicUpdateInterval(10);
        app.setMaximumLogicUpdateInterval(30);
        app.start();
    }
}
