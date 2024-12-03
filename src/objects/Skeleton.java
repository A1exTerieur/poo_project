package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class Skeleton implements ImageTile {
	
	private Point2D position;
	
	public Skeleton(Point2D initialPosition){
		position = initialPosition;
	}
	
	@Override
	public String getName() {
		return "Skeleton";
	}

	@Override
	public Point2D getPosition() {
		return position;
	}

	@Override
	public int getLayer() {
		return 2;
	}
}
