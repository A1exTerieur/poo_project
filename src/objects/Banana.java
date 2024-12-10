package objects;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Banana extends Projectile {
	
	public Banana(Point2D position) {
		super(position);
	}

	@Override
	public String getName() {
		return "Banana";
	}

	public void move() {
		position = position.plus(Direction.DOWN.asVector());	
	}
	
}
