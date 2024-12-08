package objects;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Point2D;

public class GoodMeat extends Consumable {
	private int tickBeforebadMeat = 15;
	private boolean hasTransformed = false;
    public GoodMeat(Point2D position) {
        super(position);
    }

    @Override
    public String getName() {
        return "GoodMeat";
    }

    @Override
    public void use(Manel manel) {
        int hpToRegain = 100 - manel.getLife();
        manel.addLife(hpToRegain);
    }
    
    public void tick(Room room) {
        if (hasTransformed) {
        	return;
        };
        
        tickBeforebadMeat--;
        if (tickBeforebadMeat <= 0) {
        	hasTransformed = true;
            room.transformGoodMeatToBadMeat(position);
        }
    }
}
