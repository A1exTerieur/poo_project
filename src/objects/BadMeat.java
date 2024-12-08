package objects;

import pt.iscte.poo.utils.Point2D;

public class BadMeat extends Consumable{

	public BadMeat(Point2D position) {
		super(position);
	}

	@Override
	public String getName() {
		return "BadMeat";
	}

	@Override
	public void use(Manel manel) {
		manel.removeLife(30);
	}

}
