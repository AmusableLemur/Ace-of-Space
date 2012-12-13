package graphics;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Layer extends Image {
    private float speed, offset;

    public Layer(String resource, float speed) throws SlickException {
        super(resource);

        this.speed = speed;
    }

    public float getOffset() {
        return offset;
    }

    public float getSpeed() {
        return speed;
    }

    public void update(GameContainer gc, int delta) {
        if (getOffset() > 0) {
            resetOffset();
        }

        incrementOffset(delta * getSpeed());
    }

    public void render(GameContainer gc, Graphics g) {
        for (int x = 0; x < gc.getWidth(); x += getWidth()) {
            for (int y = (int)getOffset(); y < gc.getHeight(); y += getHeight()) {
                g.drawImage(this, x, y);
            }
        }
    }

    protected void resetOffset() {
        offset = -(getHeight());
    }

    protected void incrementOffset(float amount) {
        offset += amount;
    }
}
