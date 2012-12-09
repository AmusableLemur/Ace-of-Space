package game;

import entities.Bullet;
import entities.Explosion;
import entities.Player;
import entities.enemy.Asteroid;
import entities.enemy.BigAsteroid;
import entities.enemy.Enemy;
import entities.enemy.Saucer;
import graphics.Background;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.font.effects.OutlineEffect;

/**
 * Main class for game, keeps track of viewport and game objects
 * @author Rasmus Larsson
 */
public class AceOfSpace extends BasicGame {
    private boolean gameStarted;
    private float score;
    private int gameTime;
    private CopyOnWriteArrayList<Enemy> enemies;
    private CopyOnWriteArrayList<Explosion> explosions;
    private Background background, stars;
    private Music music;
    private Player player;
    private State state;
    private HashMap<String,UnicodeFont> fonts;

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
        state = State.PLAYING;
        score = 0;

        if (!gameStarted) {
            background = new Background("graphics/bg.png", 0.05);
            stars = new Background("graphics/stars.png", 0.08);
            music = new Music("music/DefconZero.ogg");
            fonts = new HashMap<>();

            fonts.put("small", new UnicodeFont("graphics/apache.ttf", 32, false, false));
            fonts.put("large", new UnicodeFont("graphics/apache.ttf", 84, false, false));
            music.loop();

            for (UnicodeFont font : fonts.values()) {
                font.addAsciiGlyphs();
                font.getEffects().add(new ColorEffect(java.awt.Color.white));
                font.getEffects().add(new OutlineEffect(font.getSpaceWidth() / 8, java.awt.Color.gray));
                font.loadGlyphs();
            }
        }

        gc.setDefaultFont(fonts.get("small"));
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

        if (Math.random() < 0.05) {
            enemies.add(new Asteroid(gc));
        }

        if (Math.random() < 0.01) {
            enemies.add(new BigAsteroid(gc));
        }

        if (gameTime > 10000 && Math.random() < 0.005) {
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

                state = State.GAME_OVER;
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
            state = State.PAUSED;
        }

        if (!gc.hasFocus()) {
            state = State.FROZEN;
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
            case GAME_OVER:
                if (gc.getInput().isKeyPressed(Input.KEY_SPACE)) {
                    init(gc);
                }

                break;
            case PAUSED:
                if (gc.getInput().isKeyPressed(Input.KEY_SPACE)) {
                    music.resume();

                    state = State.PLAYING;
                }

                break;
            case PLAYING:
                play(gc, delta);

                break;
            case MENU:
                break;
            case FROZEN:
                if (gc.hasFocus()) {
                    state = State.PLAYING;
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

        String infoText = "Score: " + score;
        fonts.get("small").drawString(10, 10, infoText);

        switch (state) {
            case GAME_OVER:
                String title = "Game Over";
                String subtitle = "Press space to restart";

                fonts.get("large").drawString(
                        gc.getWidth() / 2 - fonts.get("large").getWidth(title) / 2,
                        gc.getHeight() / 2 - 60,
                        title
                    );

                fonts.get("small").drawString(
                        gc.getWidth() / 2 - fonts.get("small").getWidth(subtitle) / 2,
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
