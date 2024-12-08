package objects;
import java.util.ArrayList;
import java.util.List;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Point2D;

public class Bomb extends Consumable{
	private int ticksUntilExplosion = 5;
	private boolean hasExploded = false;
	private boolean isDropped = false;
	private boolean end = false;

	private List<Fire> fires = new ArrayList<>();
	private int tickAfterExplosion = 1;
	
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

	public boolean isEnd() {
		return end;
	}
	
	public boolean isDropped() {
		return isDropped;
	}

	@Override
	public void use(Manel manel) {
		manel.takeBomb(this);
		
	}

	public void tick(Room room) {
        if (hasExploded) {
        	if(tickAfterExplosion <= 0) {
        		end = true;
        		room.removeItem(this);
         	    for(Fire fire : fires) {
         	    	ImageGUI.getInstance().removeImage(fire);
         	    }
        	}else {
        		tickAfterExplosion--;
        	}
        	
    	   
        };
        
        ticksUntilExplosion--;
        if (ticksUntilExplosion <= 0) {
            explode(room);
        }
    }
	
	public void dropTheBomb(Point2D pos) {
		isDropped = true;
		super.position = pos;
	}

	public void explode(Room room) {
	    if (hasExploded) return;
	    hasExploded = true;

	    Point2D bombPosition = this.getPosition();

	    for (int x = -1; x <= 1; x++) {
	        for (int y = -1; y <= 1; y++) {
	            Point2D target = new Point2D(bombPosition.getX() + x, bombPosition.getY() + y);

	            // Remove consumables in the blast radius excluding the bomb itself
	            List<Consumable> itemsToRemove = room.getLevelConsumables().stream()
	                .filter(c -> c.getPosition().equals(target) && c != this)
	                .toList();

	            itemsToRemove.forEach(room::removeItem);

	            // Damage Manel if in the blast radius
	            if (room.getManel().getPosition().equals(target)) {
	                room.getManel().removeLife(33);
	            }

	            // Add Fire at the target position
	            fires.add(new Fire(target.getX(), target.getY()));
	        

	            // Remove DonkeyKongs in the blast radius
	            List<DonkeyKong> dkToRemove = room.getDonkeyKongs().stream()
	                .filter(dk -> dk.getPosition().equals(target))
	                .toList();

	            dkToRemove.forEach(room::dkRemove);
	        }
	    }
	}


}
