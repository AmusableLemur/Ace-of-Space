package graphics;

import java.util.ArrayList;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class Background {
    private ArrayList<Layer> layers;

    public Background() {
        this.layers = new ArrayList<>();
    }

    public void addLayer(Layer layer) {
        layers.add(layer);
    }

    public void update(GameContainer gc, int delta) {
        for (Layer layer : layers) {
            layer.update(gc, delta);
        }
    }

    public void render(GameContainer gc, Graphics g) {
        for (Layer layer : layers) {
            layer.render(gc, g);
        }
    }
}
