package entities;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Bullet extends CollisionObject {
    private Image graphic;
    private double speed;

    public Bullet(double x, double y, double speed) {
        super(x, y, 6, 6);

        this.speed = speed;

        try {
            graphic = new Image("graphics/player_bullet.png");
        } catch (SlickException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean overlaps(CollisionObject o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void render(GameContainer gc, Graphics g) {
        g.drawImage(graphic, (int)x, (int)y);
    }

    @Override
    public void update(GameContainer gc, int delta) {
        y -= speed * delta;
    }
}
