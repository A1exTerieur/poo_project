package objects;

import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class GoodMeat implements ImageTile {
	
	private Point2D position;
	
	public GoodMeat(Point2D position) {
		this.position = position;
		ImageGUI.getInstance().addImage(this);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "GoodMeat";
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
	
	public void regainFullHp(Manel manel) {
		int hpToRegain = 100 - manel.getLife();
		manel.addLife(hpToRegain);
	}

}
