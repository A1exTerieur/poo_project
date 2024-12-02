package objects;

import pt.iscte.poo.utils.Point2D;

public class Sword extends Consumable {
    private final int damage;

    public Sword(Point2D position, int damage) {
        super(position);
        this.damage = damage;
    }

    @Override
    public String getName() {
        return "Sword";
    }

    @Override
    public void use(Manel manel) {
    	manel.increaseDamage(damage);
    }

    public int getDamage() {
        return damage;
    }
}
