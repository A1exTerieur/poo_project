package objects;

import java.util.Random;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Bat implements ImageTile{
	
	private Point2D position;
	private Random rand = new Random();
	
	public Bat(Point2D position) {
		this.position = position;
	}

	@Override
	public String getName() {
		return "Bat";
	}

	@Override
	public Point2D getPosition() {
		return position;
	}

	@Override
	public int getLayer() {
		return 2;
	}
	
	public int getDamage() {
		return 20;
	}
	
	public void moveRandom(Room room) {
		int deltaX = rand.nextBoolean() ? 1 : -1;
		Vector2D movement = new Vector2D(deltaX, 0);
		Point2D belowPosition = position.plus(Direction.DOWN.asVector());
		if (!room.isWall(belowPosition) && room.isStair(belowPosition) && !room.isTrap(belowPosition)) {
			position = belowPosition;
			ImageGUI.getInstance().update();
	    }
		else if (position.getX() + movement.getX() > 0 && position.getX() + movement.getX()< 10) {
			position = position.plus(movement);
		}
	}

}
