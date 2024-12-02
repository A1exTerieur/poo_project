package objects;


public class Floor extends LevelElement {

	public Floor(int x, int y) {
		super(x, y);
	}

	@Override
	public String getName() {
		return "Floor";
	}
	
	@Override
	public int getLayer() {
		return 0;
	}

}
