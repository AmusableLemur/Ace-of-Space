package game;

import org.newdawn.slick.*;

public class AceOfSpace extends BasicGame {
	private static final int width = 800, height = 600;

	public AceOfSpace() {
		super("Ace of Space");
	}

	@Override
	public void init(GameContainer gc) throws SlickException {

	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {

	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {

	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new AceOfSpace());

		app.setDisplayMode(width, height, false);
		app.start();
	}
}
