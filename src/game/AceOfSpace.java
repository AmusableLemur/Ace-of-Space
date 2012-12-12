package game;

import entities.Bullet;
import entities.Explosion;
import entities.Player;
import entities.enemy.Asteroid;
import entities.enemy.BigAsteroid;
import entities.enemy.Enemy;
import entities.enemy.Saucer;
import graphics.Background;
import graphics.Layer;
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
    private int gameTime;
    private CopyOnWriteArrayList<Enemy> enemies;
    private CopyOnWriteArrayList<Explosion> explosions;
    private Background background;
    private Music music;
    private Player player;
    private State state;
    private HashMap<String,UnicodeFont> fonts;

    public AceOfSpace() throws SlickException {
        super("Ace of Space");

        background = new Background();
    }

    public Background getBackground() {
        return background;
    }

    public CopyOnWriteArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public CopyOnWriteArrayList<Explosion> getExplosions() {
        return explosions;
    }

    public UnicodeFont getFont(String index) {
        return fonts.get(index);
    }

    public int getGameTime() {
        return gameTime;
    }

    public Music getMusic() {
        return music;
    }

    public Player getPlayer() {
        return player;
    }

    public State getState() {
        return state;
    }

    /**
     * Resets the game state and initiates game variables
     * @param gc
     * @throws SlickException
     */
    @Override
    public void init(GameContainer gc) throws SlickException {
        getBackground().addLayer(new Layer("graphics/bg.png", 0.05f));
        getBackground().addLayer(new Layer("graphics/stars.png", 0.08f));
        setMusic(new Music("music/DefconZero.ogg"));
        getMusic().loop();

        HashMap<String, UnicodeFont> fonts = new HashMap<>();

        fonts.put("small", new UnicodeFont("graphics/apache.ttf", 32, false, false));
        fonts.put("large", new UnicodeFont("graphics/apache.ttf", 84, false, false));

        for (UnicodeFont font : fonts.values()) {
            font.addAsciiGlyphs();
            font.getEffects().add(new ColorEffect(java.awt.Color.white));
            font.getEffects().add(new OutlineEffect(font.getSpaceWidth() / 8, java.awt.Color.gray));
            font.loadGlyphs();
        }

        setFonts(fonts);
        gc.setDefaultFont(fonts.get("small"));
        gc.setShowFPS(false);

        startNewGame(gc);
    }

    /**
     * Method used for default game state, called on every logic update
     * @param gc
     * @param delta
     * @throws SlickException
     */
    public void play(GameContainer gc, int delta) throws SlickException {
        getPlayer().incrementScore(delta / 100f);
        setGameTime(getGameTime() + delta);

        if (Math.random() < 0.05) {
            enemies.add(new Asteroid(gc));
        }

        if (Math.random() < 0.01) {
            enemies.add(new BigAsteroid(gc));
        }

        if (getGameTime() > 10000 && Math.random() < 0.005) {
            getEnemies().add(new Saucer(gc));
        }

        for (Enemy e : enemies) {
            e.update(gc, delta, player);

            if (e.outsideOfScreen(gc)) {
                getEnemies().remove(e);
            }

            for (Bullet b : player.getBullets()) {
                if (e.intersects(b)) {
                    getPlayer().incrementScore(e.getScore());
                    getEnemies().remove(e);
                    getPlayer().getBullets().remove(b);
                    getExplosions().add(new Explosion(b.getX(), b.getY(), 100));

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
                setState(State.GAME_OVER);
            }
        }

        for (Explosion ex : getExplosions()) {
            ex.update(gc, delta);

            if (ex.getTime() < 0) {
                getExplosions().remove(ex);
            }
        }

        if (gc.getInput().isKeyPressed(Input.KEY_SPACE)) {
            getMusic().pause();
            setState(State.PAUSED);
        }

        if (!gc.hasFocus()) {
            setState(State.FROZEN);
        }

        getPlayer().update(gc, delta);
        getBackground().update(gc, delta);
    }

    /**
     * Renders viewport, agnostic to game state except for Game Over
     * @param gc
     * @param g
     * @throws SlickException
     */
    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        getBackground().render(gc, g);

        for (Enemy e : enemies) {
            e.render(gc, g);
        }

        for (Explosion e : explosions) {
            e.render(gc, g);
        }

        getPlayer().render(gc, g);

        String infoText = "Score: " + getPlayer().getScore();
        getFont("small").drawString(10, 10, infoText);

        switch (state) {
            case GAME_OVER:
                String title = "Game Over";
                String subtitle = "Press space to restart";

                getFont("large").drawString(
                        gc.getWidth() / 2 - getFont("large").getWidth(title) / 2,
                        gc.getHeight() / 2 - 60,
                        title
                    );

                getFont("small").drawString(
                        gc.getWidth() / 2 - getFont("small").getWidth(subtitle) / 2,
                        gc.getHeight() / 2 + 20,
                        subtitle
                    );

                break;
        }
    }

    public void startNewGame(GameContainer gc) throws SlickException {
        setEnemies(new CopyOnWriteArrayList<Enemy>());
        setExplosions(new CopyOnWriteArrayList<Explosion>());
        setPlayer(new Player(gc));
        setGameTime(0);
        setState(State.PLAYING);
    }

    public void setEnemies(CopyOnWriteArrayList<Enemy> enemies) {
        this.enemies = enemies;
    }

    public void setExplosions(CopyOnWriteArrayList<Explosion> explosions) {
        this.explosions = explosions;
    }

    public void setFonts(HashMap<String, UnicodeFont> fonts) {
        this.fonts = fonts;
    }

    public void setGameTime(int gameTime) {
        this.gameTime = gameTime;
    }

    public void setMusic(Music music) {
        this.music = music;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setState(State state) {
        this.state = state;
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
                    startNewGame(gc);
                }

                break;
            case PAUSED:
                if (gc.getInput().isKeyPressed(Input.KEY_SPACE)) {
                    getMusic().resume();
                    setState(State.PLAYING);
                }

                break;
            case PLAYING:
                play(gc, delta);

                break;
            case MENU:
                break;
            case FROZEN:
                if (gc.hasFocus()) {
                    setState(State.PLAYING);
                }

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
