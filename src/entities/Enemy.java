package entities;

public class Enemy implements CollisionObject {
	public Enemy() {
		// Bring the Noise
	}

	@Override
	public boolean overlaps(CollisionObject o) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
}
