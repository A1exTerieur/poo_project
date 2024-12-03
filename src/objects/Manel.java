package objects;

import pt.iscte.poo.game.Actions;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Manel implements ImageTile {
	
	private int life = 100;
	private Point2D position;
	private int damage = 15;
	
	public Manel(Point2D initialPosition){
		position = initialPosition;
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

	public Actions move(Direction dir, Room room, boolean gravity) {
        Point2D targetPosition = this.position.plus(dir.asVector());
        
        if (room.isWall(targetPosition) || targetPosition.getX() < 0 || targetPosition.getX() > 9 ) {
            return Actions.BLOCKED;
        }

        if (dir == Direction.UP || dir == Direction.DOWN) {
            if (!gravity && !room.isStair(targetPosition) && !room.isStair(position)) {
                return Actions.BLOCKED;
            }
        }
        

        if (room.isNextRoomTile(targetPosition)) {
            return Actions.DOOR;
        }
        
        if (room.isPrincess(targetPosition)) {
            return Actions.PRINCESS;
        }
        
        

        this.position = targetPosition;
        useItemAtPosition(targetPosition, room);
        return Actions.OK;
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
		if(life < 0) {
			life = 0;
		}
	}
	
	public void addLife(int hp) {
		life+=hp;
	}
	
	public void setPosition(Point2D position) {
		this.position = position;
	}
	
    public void useItemAtPosition(Point2D targetPosition, Room room) {
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
