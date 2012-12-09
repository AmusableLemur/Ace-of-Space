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
import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.font.effects.OutlineEffect;

/**
 * Main class for game, keeps track of viewport and game objects
 * @author Rasmus Larsson
 */
public class AceOfSpace extends BasicGame {
    /**
     * Used when player has been hit and game needs to be restarted
     */
    private static final int STATE_GAME_OVER = -1;

    /**
     * Player initiated pausing of game
     */
    private static final int STATE_PAUSED = 0;

    /**
     * Default mode, actual game is running
     */
    private static final int STATE_PLAYING = 1;

    /**
     * Unused, will be used for credits and a "start game" button
     */
    private static final int STATE_MENU = 2;

    /**
     * Freezes the game when it loses focus
     */
    private static final int STATE_FROZEN = 3;

    /**
     * Makes sure certain elements aren't loaded or started twice
     */
    private boolean gameStarted;

    /**
     * Player score
     */
    private float score;

    /**
     * Time since current session started
     */
    private int gameTime;

    /**
     * The current state
     */
    private int state;

    /**
     * Lists for various game objects, CopyOnWrite to allow for concurrent
     * modifications.
     */
    private CopyOnWriteArrayList<Enemy> enemies;
    private CopyOnWriteArrayList<Explosion> explosions;

    /**
     * Scrolling background, two layers for "deep" effect
     */
    private Background background, stars;

    /**
     * Background music, all other music is instantiated when it's used
     */
    private Music music;

    /**
     * The human controlled player object
     */
    private Player player;

    /**
     * Font faces
     */
    private UnicodeFont largeText, smallText;

    /**
     * Constructor, sets the title of the window
     */
    public AceOfSpace() {
        super("Ace of Space");
    }

    /**
     * Resets the game state and initiates game variables
     * @param gc
     * @throws SlickException
     */
    @Override
    public void init(GameContainer gc) throws SlickException {
        enemies = new CopyOnWriteArrayList<>();
        explosions = new CopyOnWriteArrayList<>();
        player = new Player(gc);
        gameTime = 0;
        state = STATE_PLAYING;
        score = 0;

        if (!gameStarted) {
            background = new Background("graphics/bg.png", 0.05);
            stars = new Background("graphics/stars.png", 0.08);
            music = new Music("music/DefconZero.ogg");
            smallText = new UnicodeFont("graphics/apache.ttf", 32, false, false);
            largeText = new UnicodeFont("graphics/apache.ttf", 84, false, false);

            music.loop();

            smallText.addAsciiGlyphs();
            smallText.getEffects().add(new ColorEffect(java.awt.Color.white));
            smallText.getEffects().add(new OutlineEffect(1, java.awt.Color.gray));
            smallText.loadGlyphs();

            largeText.addAsciiGlyphs();
            largeText.getEffects().add(new ColorEffect(java.awt.Color.white));
            largeText.getEffects().add(new OutlineEffect(3, java.awt.Color.gray));
            largeText.loadGlyphs();
        }

        gc.setDefaultFont(smallText);
        gc.setShowFPS(false);

        gameStarted = true;
    }

    /**
     * Method used for default game state, called on every logic update
     * @param gc
     * @param delta
     * @throws SlickException
     */
    public void play(GameContainer gc, int delta) throws SlickException {
        score += delta / 100;
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
                Sound sound = new Sound("sound/gameOver.wav");
                sound.play();

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

        if (gc.getInput().isKeyPressed(Input.KEY_SPACE)) {
            music.pause();
            state = STATE_PAUSED;
        }

        if (!gc.hasFocus()) {
            state = STATE_FROZEN;
        }
    }

    /**
     * Main logic update method, executes different game states
     * @param gc
     * @param delta
     * @throws SlickException
     */
    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
        if (gc.getInput().isKeyDown(Input.KEY_F)) {
            gc.setFullscreen(!gc.isFullscreen());
        }

        switch (state) {
            case STATE_GAME_OVER:

                if (gc.getInput().isKeyPressed(Input.KEY_SPACE)) {
                    init(gc);
                }

                break;
            case STATE_PAUSED:
                if (gc.getInput().isKeyPressed(Input.KEY_SPACE)) {
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

    /**
     * Renders viewport, agnostic to game state except for Game Over
     * @param gc
     * @param g
     * @throws SlickException
     */
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
