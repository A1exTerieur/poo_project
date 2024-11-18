package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Manel implements ImageTile {
	
	private int life = 3;
	private Point2D position;
	
	public Manel(Point2D initialPosition){
		position = initialPosition;
	}
	
	@Override
	public String getName() {
		return "JumpMan";
	}

	@Override
	public Point2D getPosition() {
		return position;
	}

	@Override
	public int getLayer() {
		// TODO Auto-generated method stub
		return 1;
	}

	public void move(Direction dir) {
		position = position.plus(dir.asVector());	
	}
	
	public int getLife() {
		return life;
	}
	
	public void removeLife() {
		life--;
	}
	
	public void addLife() {
		life++;
	}
	
}
