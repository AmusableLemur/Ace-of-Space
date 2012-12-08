package game;

import entities.Bullet;
import entities.Explosion;
import entities.Player;
import entities.enemy.Asteroid;
import entities.enemy.Enemy;
import entities.enemy.Saucer;
import entities.enemy.Ship;
import graphics.Background;
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

    private boolean gameStarted;
    private double score;
    private int state;
    private ArrayList<Enemy> enemies;
    private ArrayList<Explosion> explosions;
    private Background background, stars;
    private Input input;
    private Music music;
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

            music.resume();
            state = STATE_PLAYING;
        }
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        enemies = new ArrayList<>();
        explosions = new ArrayList<>();
        background = new Background("graphics/bg.png", 0.05);
        stars = new Background("graphics/stars.png", 0.08);
        input = gc.getInput();
        music = new Music("music/DefconZero.ogg");
        player = new Player(gc);
        state = STATE_PLAYING;
        score = 0;
        smallText = new UnicodeFont("graphics/apache.ttf", 32, false, false);
        largeText = new UnicodeFont("graphics/apache.ttf", 84, false, false);

        if (!gameStarted) {
            music.loop();
        }

        smallText.addAsciiGlyphs();
        smallText.getEffects().add(new ColorEffect(java.awt.Color.white));
        smallText.loadGlyphs();

        largeText.addAsciiGlyphs();
        largeText.getEffects().add(new ColorEffect(java.awt.Color.white));
        largeText.loadGlyphs();

        gc.setDefaultFont(smallText);
        gc.setShowFPS(false);

        gameStarted = true;
    }

    public void pause(GameContainer gc, int delta) {
        if (input.isKeyPressed(Input.KEY_SPACE)) {
            music.resume();
            state = STATE_PLAYING;
        }
    }

    public void play(GameContainer gc, int delta) throws SlickException {
        score += (double)delta / 100;

        if (Math.random() < 0.05 && !gc.isPaused()) {
            enemies.add(new Asteroid(gc));
        }

        Iterator<Enemy> ei = enemies.iterator();

        while (ei.hasNext()) {
            Enemy e = ei.next();

            e.update(gc, delta);

            if (e.outsideOfScreen(gc)) {
                ei.remove();
            }

            Iterator<Bullet> bi = player.getBullets().iterator();

            while (bi.hasNext()) {
                Bullet b = bi.next();

                if (e.intersects(b)) {
                    ei.remove();
                    bi.remove();

                    explosions.add(new Explosion(b.getX(), b.getY(), 100));

                    score += 20;
                }
            }

            if (e.intersects(player)) {
                music.pause();
                state = STATE_GAME_OVER;
            }
        }

        Iterator<Explosion> ix = explosions.iterator();

        while (ix.hasNext()) {
            Explosion ex = ix.next();

            ex.update(gc, delta);

            if (ex.getTime() < 0) {
                ix.remove();
            }
        }

        player.update(gc, delta);
        background.update(gc, delta);
        stars.update(gc, delta);

        if (input.isKeyPressed(input.KEY_SPACE)) {
            music.pause();
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
        background.render(gc, g);
        stars.render(gc, g);

        for (Enemy e : enemies) {
            e.render(gc, g);
        }

        for (Explosion e : explosions) {
            e.render(gc, g);
        }

        player.render(gc, g);

        String infoText = "Score: " + (int)score;
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
