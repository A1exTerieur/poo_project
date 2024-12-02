package objects;

public class Wall extends LevelElement{


	public Wall(int x, int y) {
		super(x, y);
	}

	@Override
	public String getName() {
		return "Wall";
	}

}
