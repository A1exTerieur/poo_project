package objects;

import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public abstract class LevelElement implements ImageTile {
    private final int x;
    private final int y;

    public LevelElement(int x, int y) {
        this.x = x;
        this.y = y;
		ImageGUI.getInstance().addImage(this);
    }

    @Override
    public Point2D getPosition() {
        return new Point2D(x, y);
    }
    
    @Override
    public int getLayer() {
    	return 1;
    }
}
