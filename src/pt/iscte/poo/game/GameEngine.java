package pt.iscte.poo.game;

import objects.Banana;
import objects.DonkeyKong;
import objects.Manel;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class GameEngine implements Observer {
	
	
	
	private Room currentRoom = new Room("rooms/room0.txt");
	private int lastTickProcessed = 0;
	
	public GameEngine() {
		ImageGUI.getInstance().update();
	}

	@Override
	public void update(Observed source) {
		
		if (ImageGUI.getInstance().wasKeyPressed()) {
			int k = ImageGUI.getInstance().keyPressed();
			System.out.println("Keypressed " + k);
			if (Direction.isDirection(k) && !stuckByDk()) {
			    System.out.println("Direction! ");
			    boolean nextRoomTriggered = currentRoom.moveManel(Direction.directionFor(k));
			    if(currentRoom.isSword(currentRoom.getManel().getPosition())) {
			    	currentRoom.getManel().increaseDamage(currentRoom.getSword().getDamage());
			    	System.out.println("Manel damage = "+currentRoom.getManel().getDamage());
			    }
			    if (nextRoomTriggered) {
			        loadRoom(currentRoom.getNextRoomFile());
			    }
			}
		}
		int t = ImageGUI.getInstance().getTicks();
		while (lastTickProcessed < t) {
			processTick();
		}
		ImageGUI.getInstance().update();
	}

	private void processTick() {
		System.out.println("Tic Tac : " + lastTickProcessed);
		applyGravity();
		moveDonkeyKongs(); 
		hitByBanana();
		lastTickProcessed++;
	}
	
	private boolean stuckByDk() {
		for(DonkeyKong dk: currentRoom.getDonkeyKongs()) {
			if((dk.getPosition().getY() == currentRoom.getManel().getPosition().getY()) && (dk.getPosition().getX() + 1 == currentRoom.getManel().getPosition().getX()) && (dk.getPosition().getX() < currentRoom.getManel().getPosition().getX())) {
				currentRoom.getManel().removeLife(dk.getDamage());
				dk.removeLife(currentRoom.getManel().getDamage());
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
					currentRoom.getManel().removeLife(10);
					System.out.println("Current life : "+currentRoom.getManel().getLife());
				}
			}
		}
	}

	public void loadRoom(String nextRoomFile) {
	    System.out.println("Loading next room: " + nextRoomFile);
	    ImageGUI.getInstance().clearImages();
	    currentRoom = new Room("rooms/" + nextRoomFile);
	    ImageGUI.getInstance().update();
	}
	
	private void applyGravity() {
	    Point2D belowPosition = currentRoom.getManel().getPosition().plus(Direction.DOWN.asVector());
	    
	    if (!currentRoom.isWall(belowPosition) && !currentRoom.isStair(belowPosition) && !currentRoom.isTrap(belowPosition)) {
	        currentRoom.getManel().move(Direction.DOWN);
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

}
