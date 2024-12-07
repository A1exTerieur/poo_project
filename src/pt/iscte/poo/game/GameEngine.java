package pt.iscte.poo.game;

import java.awt.event.KeyEvent;
import java.util.Iterator;

import objects.Banana;
import objects.Bomb;
import objects.Consumable;
import objects.DonkeyKong;
import objects.Manel;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class GameEngine implements Observer {
	private Manel manel;
	private Room currentRoom;
	private int lastTickProcessed = 0;
	private boolean haveTheBomb = false;
	private boolean bombDrop = false;
	private int tickBombDrop = 0;
	
	public GameEngine() {
		this.manel = new Manel(new Point2D(0,0));
		this.currentRoom = new Room("rooms/room0.txt", manel);
		ImageGUI.getInstance().update();
	}

	@Override
	public void update(Observed source) {
		
		if (ImageGUI.getInstance().wasKeyPressed()) {
			int k = ImageGUI.getInstance().keyPressed();
			haveTheBomb = currentRoom.getManel().isHaveTheBomb();
			if(haveTheBomb && (k == KeyEvent.VK_B || k == KeyEvent.VK_N)) {
				bombDrop = true;
				tickBombDrop = lastTickProcessed;
		    	currentRoom.getManel().useBomb(k);
		    	currentRoom.spawnBomb(currentRoom.getManel().getBomb());
		    }
			if (Direction.isDirection(k) && !stuckByDk()) {
			    int nextRoomTriggered = currentRoom.moveManel(Direction.directionFor(k), false);
			    hitByBanana();
			    
			    if(nextRoomTriggered == 2) {
			    	loadRoom(currentRoom.getNextRoomFile());
			    }
			}
		}
		int t = ImageGUI.getInstance().getTicks();
		while (lastTickProcessed < t) {
			processTick();
		}
		ImageGUI.getInstance().setStatusMessage("Life : "+ currentRoom.getManel().getLife()+ " | Damage : "+currentRoom.getManel().getDamage());
		ImageGUI.getInstance().update();
	}

	private void processTick() {
		System.out.println("tick = "+lastTickProcessed);
		
		if(bombDrop && !manel.getBomb().hasExploded()) {
			// Vérifier les collisions avec les bombes
			currentRoom.checkBombCollisions();
			
			if(lastTickProcessed < tickBombDrop + 5) {
				currentRoom.getManel().getBomb().tick(currentRoom);
			}
			
			if(lastTickProcessed == tickBombDrop + 4) {
				System.out.println("Bombdrop false");
				bombDrop = false;
			}
		    
		}
	    
		hitByBanana();
		checkManelLife();
		applyGravity();
		moveDonkeyKongs(); 
		
		lastTickProcessed++;
	}
	
	private void checkManelLife() {
		if(currentRoom.getManel().getLife() <= 0) {
			loadRoom("room0.txt");
			this.manel = new Manel(new Point2D(0,0));
		}
	}
	
	
	
	private boolean stuckByDk() {
		for(DonkeyKong dk: currentRoom.getDonkeyKongs()) {
			if((dk.getPosition().getY() == currentRoom.getManel().getPosition().getY()) && (dk.getPosition().getX() + 1 == currentRoom.getManel().getPosition().getX()) && (dk.getPosition().getX() < currentRoom.getManel().getPosition().getX()) || dk.getPosition().equals(currentRoom.getManel().getPosition())) {
				currentRoom.getManel().removeLife(dk.getDamage());
				dk.removeLife(currentRoom.getManel().getDamage());
				if(dk.getLife() <= 0) {
					currentRoom.dkRemove(dk);
				}
				System.out.println("Current life : "+currentRoom.getManel().getLife());
				return true;
			}
		}
		return false;
	}
	
	private void hitByBanana() {
		for(DonkeyKong ban: currentRoom.getDonkeyKongs()) {
			for(Banana elt: ban.getBananas()) {
				if(currentRoom.getManel().getPosition().equals(elt.getPosition())) {
					currentRoom.getManel().removeLife(30);
					System.out.println("Current life : "+currentRoom.getManel().getLife());
				}
			}
		}
	}

	public void loadRoom(String nextRoomFile) {
	    System.out.println("Loading next room: " + nextRoomFile);
	    ImageGUI.getInstance().clearImages();
	    currentRoom = new Room("rooms/" + nextRoomFile, manel);
	    ImageGUI.getInstance().update();
	}
	
	private void applyGravity() {
	    Point2D belowPosition = currentRoom.getManel().getPosition().plus(Direction.DOWN.asVector());
	    
	    if (!currentRoom.isWall(belowPosition) && !currentRoom.isStair(belowPosition) && !currentRoom.isTrap(belowPosition)) {
	        currentRoom.moveManel(Direction.DOWN, true);
	        ImageGUI.getInstance().update();
	    }
	}
	
	private void moveDonkeyKongs() {
		for (DonkeyKong dk : currentRoom.getDonkeyKongs()) {
			dk.moveRandom();
			dk.tryShootBanana();
			dk.updateBananas();
		}
		
		ImageGUI.getInstance().update();
	}
	
	public Room getCurrentRoom() {
		return currentRoom;
	}

}
