package objects;

import pt.iscte.poo.utils.Point2D;

public class GoodMeat extends Consumable {
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
        //manel.addLife(hpToRegain);
    }
}
