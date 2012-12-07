package graphics;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Background extends Image {
    private double offset, speed;

    public Background(String resource, double speed) throws SlickException {
        super(resource);

        this.speed = speed;
    }

    public void update(GameContainer gc, int delta) {
        if (offset > 0) {
            offset = -getHeight();
        }

        offset += delta * speed;
    }

    public void render(GameContainer gc, Graphics g) {
        for (int x = 0; x < gc.getWidth(); x += getWidth()) {
            for (int y = (int)offset; y < gc.getHeight(); y += getHeight()) {
                g.drawImage(this, x, y);
            }
        }
    }
}
