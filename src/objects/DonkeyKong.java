package objects;

import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DonkeyKong implements ImageTile {

	private int life = 100;
	private Point2D position;
	private final int damage = 10;
	private Random rand = new Random();
	private List<Banana> bananas = new ArrayList<>();


	public DonkeyKong(Point2D initialPosition){
		position = initialPosition;
	}

	@Override
	public String getName() {
		return "DonkeyKong";
	}

	@Override
	public Point2D getPosition() {
		return position;
	}
	
	public int getDamage() {
		return damage;
	}

	@Override
	public int getLayer() {
		return 1;
	}

	public void moveRandom() {
		int deltaX = rand.nextBoolean() ? 1 : -1;
		Vector2D movement = new Vector2D(deltaX, 0);
		if (position.getX() + movement.getX() > 0 && position.getX() + movement.getX()< 10) {
			position = position.plus(movement);
		}
	}

	public void tryShootBanana() {
		if(rand.nextBoolean()) {
			Banana banana = new Banana(new Point2D(position.getX(), position.getY()));
			bananas.add(banana);
		}
	}

	public void updateBananas() {
		for (Banana banana : bananas) {
			banana.move();
		}
	}

	public int getLife() {
		return life;
	}

	public void removeLife(int damage) {
		life-=damage;
	}
	
	public void addLife(int hp) {
		life+=hp;
	}

	public List<Banana> getBananas() {
		return bananas;
	}

	public void clearBananas() {
		for(Banana el : bananas) {
			ImageGUI.getInstance().removeImage(el);

		}
		bananas.clear();
	}
}
