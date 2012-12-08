package game;

import entities.Bullet;
import entities.Explosion;
import entities.Player;
import entities.enemy.Asteroid;
import entities.enemy.BigAsteroid;
import entities.enemy.Enemy;
import entities.enemy.Saucer;
import graphics.Background;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.font.effects.OutlineEffect;

public class AceOfSpace extends BasicGame {
    private static final int STATE_GAME_OVER = -1;
    private static final int STATE_PAUSED = 0;
    private static final int STATE_PLAYING = 1;
    private static final int STATE_MENU = 2;
    private static final int STATE_FROZEN = 3;

    private boolean gameStarted;
    private double score;
    private int gameTime, state;
    private CopyOnWriteArrayList<Enemy> enemies;
    private CopyOnWriteArrayList<Explosion> explosions;
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
        enemies = new CopyOnWriteArrayList<>();
        explosions = new CopyOnWriteArrayList<>();
        background = new Background("graphics/bg.png", 0.05);
        stars = new Background("graphics/stars.png", 0.08);
        input = gc.getInput();
        music = new Music("music/DefconZero.ogg");
        player = new Player(gc);
        gameTime = 0;
        state = STATE_PLAYING;
        score = 0;
        smallText = new UnicodeFont("graphics/apache.ttf", 32, false, false);
        largeText = new UnicodeFont("graphics/apache.ttf", 84, false, false);

        if (!gameStarted) {
            music.loop();
        }

        smallText.addAsciiGlyphs();
        smallText.getEffects().add(new ColorEffect(java.awt.Color.white));
        smallText.getEffects().add(new OutlineEffect(1, java.awt.Color.gray));
        smallText.loadGlyphs();

        largeText.addAsciiGlyphs();
        largeText.getEffects().add(new ColorEffect(java.awt.Color.white));
        largeText.getEffects().add(new OutlineEffect(3, java.awt.Color.gray));
        largeText.loadGlyphs();

        gc.setDefaultFont(smallText);
        gc.setShowFPS(false);

        gameStarted = true;
    }

    public void play(GameContainer gc, int delta) throws SlickException {
        score += (double)delta / 100;
        gameTime += delta;

        if (Math.random() < 0.05 && !gc.isPaused()) {
            enemies.add(new Asteroid(gc));
        }

        if (Math.random() < 0.01 && !gc.isPaused()) {
            enemies.add(new BigAsteroid(gc));
        }

        if (gameTime > 10000 && Math.random() < 0.005 && !gc.isPaused()) {
            enemies.add(new Saucer(gc));
        }

        for (Enemy e : enemies) {
            e.update(gc, delta, player);

            if (e.outsideOfScreen(gc)) {
                enemies.remove(e);
            }

            for (Bullet b : player.getBullets()) {
                if (e.intersects(b)) {
                    score += e.getScore();

                    enemies.remove(e);
                    player.getBullets().remove(b);

                    explosions.add(new Explosion(b.getX(), b.getY(), 100));

                    Sound sound = new Sound("sound/explosion.wav");
                    sound.play();

                    if (e instanceof BigAsteroid) {
                        enemies.add(new Asteroid(gc, e.getX(), e.getY(), -2));
                        enemies.add(new Asteroid(gc, e.getX(), e.getY(), 0));
                        enemies.add(new Asteroid(gc, e.getX(), e.getY(), 2));
                    }
                }
            }

            if (e.intersects(player)) {
                music.pause();
                state = STATE_GAME_OVER;
            }
        }

        for (Explosion ex : explosions) {
            ex.update(gc, delta);

            if (ex.getTime() < 0) {
                explosions.remove(ex);
            }
        }

        player.update(gc, delta);
        background.update(gc, delta);
        stars.update(gc, delta);

        if (input.isKeyPressed(input.KEY_SPACE)) {
            music.pause();
            state = STATE_PAUSED;
        }

        if (!gc.hasFocus()) {
            state = STATE_FROZEN;
        }
    }

    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
        switch (state) {
            case STATE_GAME_OVER:
                gameOver(gc, delta);

                break;
            case STATE_PAUSED:
                if (input.isKeyPressed(Input.KEY_SPACE)) {
                    music.resume();
                    state = STATE_PLAYING;
                }

                break;
            case STATE_PLAYING:
                play(gc, delta);

                break;
            case STATE_MENU:
                break;
            case STATE_FROZEN:
                if (gc.hasFocus()) {
                    state = STATE_PLAYING;
                }
                
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
