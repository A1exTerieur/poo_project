package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Manel implements ImageTile {
	
	private int life = 100;
	private Point2D position;
	private int damage = 15;
	
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
	
	public int getDamage() {
		return damage;
	}
	
	public void increaseDamage(int point) {
		damage+=point;
	}
	
	public void removeLife(int damage) {
		life-=damage;
	}
	
	public void addLife(int hp) {
		life+=hp;
	}
	
}
