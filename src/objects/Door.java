package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class Door implements ImageTile {
	private final int x;
	private final int y;
	
	public Door(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String getName() {
		return "DoorClosed";
	}

	@Override
	public int getLayer() {
		return 1;
	}

	@Override
	public Point2D getPosition() {
		return new Point2D(x, y);
	}

	
}
