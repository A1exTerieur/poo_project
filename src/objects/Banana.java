package objects;

import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Banana implements ImageTile {

    private Point2D position;

	
	public Banana(Point2D position) {
		 this.position = position;
		 ImageGUI.getInstance().addImage(this);
	}

	@Override
	public String getName() {
		return "Banana";
	}

	@Override
	public int getLayer() {
		return 1;
	}

	@Override
	public Point2D getPosition() {
		return position;
	}
	
	public void move() {
		position = position.plus(Direction.DOWN.asVector());	
	}
	
}
