package objects;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Manel implements ImageTile {
	
	private int life = 100;
	private Point2D position;
	private int damage = 15;
	 private Room room; 
	
	public Manel(Point2D initialPosition, Room room){
		position = initialPosition;
		this.room = room;
	}
	
	@Override
	public String getName() {
		return "JumpMan";
	}

	@Override
	public Point2D getPosition() {
		return position;
	}

	@Override
	public int getLayer() {
		return 2;
	}

	public int move(Direction dir) {
        Point2D targetPosition = this.position.plus(dir.asVector());

        if (room.isWall(targetPosition)) {
            System.out.println("Blocked by a wall!");
            return -1;
        }

        if (dir == Direction.UP || dir == Direction.DOWN) {
            if (!room.isStair(targetPosition) && !room.isStair(position)) {
                System.out.println("Cannot move vertically without stairs!");
                return -1;
            }
        }
        

        if (room.isNextRoomTile(targetPosition)) {
            System.out.println("Moving to the next room!");
            return 2;
        }
        
        

        this.position = targetPosition;
        System.out.println("Moved to " + targetPosition);
        useItemAtPosition(targetPosition);
        return 1;
    }
	
	public int getLife() {
		return life;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public void increaseDamage(int point) {
		damage+=point;
	}
	
	public void removeLife(int damage) {
		life-=damage;
	}
	
	public void addLife(int hp) {
		life+=hp;
	}
	
    public void useItemAtPosition(Point2D targetPosition) {
        room.getLevelConsumables().stream()
            .filter(item -> item.getPosition().equals(targetPosition))
            .findFirst()
            .ifPresent(item -> {
                item.use(this);
                System.out.println("Used item: " + item.getName());
                room.removeItem(item);
            });
    }
}
