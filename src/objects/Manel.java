package objects;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import pt.iscte.poo.game.Actions;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Manel implements ImageTile {
	
	private int life = 100;
	private int gameLife = 3;
	private Point2D position;
	private int damage = 15;
	private boolean haveTheBomb = false;
	private Bomb bomb = null;
	private List<Projectile> projectiles = new ArrayList<>();
	
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
	
	public void setMaxLife() {
		life = 100;
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
        
        if(room.isFakeWall(targetPosition)) {
        	room.transformFakeWallToTrap(targetPosition);
        	return Actions.TRAP;
        }
        

        if (room.isNextRoomTile(targetPosition)) {
            System.out.println("Moving to the next room!");
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

	public int getGameLife() {
		return gameLife;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public Bomb getBomb() {
		return bomb;
	}
	
	public void removeGameLife(int num) {
		gameLife-=num;
	}
	
	public void increaseDamage(int point) {
		damage+=point;
	}
	
	public void takeBomb(Bomb bomb) {
		haveTheBomb = true;
		this.bomb = bomb;
	}
	
	public void useBomb(int k) {
		if(k == KeyEvent.VK_B) {
			bomb.dropTheBomb(new Point2D(position.getX() - 1, position.getY()));
		} else {
			bomb.dropTheBomb(new Point2D(position.getX() + 1, position.getY()));
		}
		haveTheBomb = false;
	}
	
	public boolean isHaveTheBomb() {
		return haveTheBomb;
	}

	public void removeLife(int damage) {
		life-=damage;
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
    
    public void shoot(int k) {
    	switch(k) {
    		case KeyEvent.VK_H: projectiles.add(new Hammer(position)); break;
    		case KeyEvent.VK_F: projectiles.add(new Fireball(position)); break;
    	}
    }
    
    public void updateProjectiles() {
    	for(Projectile proj : projectiles) {
			proj.move();
			
		}
    }
    
    public List<Projectile> getProjectiles() {
		return projectiles;
	}
}
