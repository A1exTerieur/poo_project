package objects;

import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public abstract class Consumable implements ImageTile {
    protected Point2D position;

    public Consumable(Point2D position) {
        this.position = position;
        ImageGUI.getInstance().addImage(this);
    }

    @Override
    public Point2D getPosition() {
        return position;
    }

    @Override
    public int getLayer() {
        return 1;
    }

    public abstract void use(Manel manel);
}