package objects;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Hammer extends Projectile {
	
	public Hammer(Point2D position) {
		super(position);
	}

	@Override
	public String getName() {
		return "Hammer";
	}

	public void move() {		
		position = position.plus(Direction.UP.asVector());	
	}
	
}
