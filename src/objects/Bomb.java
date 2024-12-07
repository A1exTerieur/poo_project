package objects;
import java.util.List;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Point2D;

public class Bomb extends Consumable{
	private int ticksUntilExplosion = 5;
	private boolean hasExploded = false;

	public Bomb(Point2D position) {
		super(position);
	}

	@Override
	public String getName() {
		return "Bomb";
	}

	@Override
	public int getLayer() {
		return 1;
	}

	public boolean hasExploded() {
		return hasExploded;
	}

	@Override
	public void use(Manel manel) {
		manel.takeBomb(this);
		
	}

	public void tick(Room room) {
        if (hasExploded) return;

        ticksUntilExplosion--;
        if (ticksUntilExplosion <= 0) {
            explode(room);
        }
    }
	
	public void dropTheBomb(Point2D pos) {
		super.position = pos;
	}

	public void explode(Room room) {
	    if (hasExploded) return;
	    hasExploded = true;

	    System.out.println("EXPLOSION at " + super.getPosition());
	    Point2D bombPosition = this.getPosition();

	    // Détruire les objets dans un rayon de 1
	    for (int x = -1; x <= 1; x++) {
	        for (int y = -1; y <= 1; y++) {
	            Point2D target = new Point2D(bombPosition.getX() + x, bombPosition.getY() + y);

	            // Collecter les consommables à supprimer
	            List<Consumable> itemsToRemove = room.getLevelConsumables().stream()
	                .filter(c -> c.getPosition().equals(target))
	                .toList(); // Collecter les éléments dans une liste temporaire

	            // Supprimer les éléments collectés
	            itemsToRemove.forEach(room::removeItem);

	            // Vérifie si Manel est dans le rayon d'explosion
	            if (room.getManel().getPosition().equals(target)) {
	                room.getManel().removeLife(1);
	            }

	         // Collecter et supprimer les DonkeyKongs dans le rayon
	            List<DonkeyKong> dkToRemove = room.getDonkeyKongs().stream()
	                .filter(dk -> dk.getPosition().equals(target))
	                .toList();

	            for (DonkeyKong dk : dkToRemove) {
	                room.dkRemove(dk);
	            }
	        }
	    }

	    // Retirer la bombe après explosion
	    room.removeItem(this);
	}


}
