package objects;

import javax.swing.text.Position;

import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class Sword implements ImageTile{
	
	private final int damage;
	private Point2D position;
	
	public Sword(Point2D position, int damage) {
		this.damage = damage;
		this.position = position;
		ImageGUI.getInstance().addImage(this);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Sword";
	}

	@Override
	public Point2D getPosition() {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public int getLayer() {
		// TODO Auto-generated method stub
		return 1;
	}
	
	public int getDamage() {
		return damage;
	}

}
