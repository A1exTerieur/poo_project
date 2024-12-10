package objects;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Fireball extends Projectile{
	
	private static final int DAMAGE = 20;

	public Fireball(Point2D position) {
		super(position);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Fire";
	}

	@Override
	public void move() {
		position = position.plus(Direction.LEFT.asVector());
	}
	
	public int getDamage() {
		return DAMAGE;
	}

}
