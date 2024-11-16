package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DonkeyKong implements ImageTile {

	private int life = 3;
	private Point2D position;
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

	@Override
	public int getLayer() {
		return 1;
	}

	public void moveRandom() {
		int deltaX = rand.nextBoolean() ? 1 : -1;
		Vector2D movement = new Vector2D(deltaX, 0);
		System.out.println(position.getX());
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

	private void removeLife() {
		life--;
	}

	private void addLife() {
		life++;
	}

	public List<Banana> getBananas() {
		return bananas;
	}
}
