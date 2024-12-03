package objects;
import pt.iscte.poo.utils.Point2D;

public class Bomb extends Consumable{
	
	private Point2D pos;

	public Bomb(Point2D position) {
		super(position);
	}

	@Override
	public String getName() {
		return "Bomb";
	}

	@Override
	public int getLayer() {
		return 1;
	}

	@Override
	public void use(Manel manel) {
		manel.takeBomb(this);
		
	}

	public void explode(Point2D pos) {
		System.out.println("EXPLOSION");
		super.position = pos;
	}
}
