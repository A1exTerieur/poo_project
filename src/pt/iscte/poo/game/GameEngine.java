package pt.iscte.poo.game;

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
			if (Direction.isDirection(k)) {
			    System.out.println("Direction! ");
			    boolean nextRoomTriggered = currentRoom.moveManel(Direction.directionFor(k));
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
		lastTickProcessed++;
	}

	public void loadRoom(String nextRoomFile) {
	    System.out.println("Loading next room: " + nextRoomFile);
	    ImageGUI.getInstance().clearImages();
	    currentRoom = new Room("rooms/" + nextRoomFile);
	    ImageGUI.getInstance().update();
	}
	
	private void applyGravity() {
	    Point2D belowPosition = currentRoom.getManel().getPosition().plus(Direction.DOWN.asVector());
	    
	    if (!currentRoom.isWall(belowPosition) && !currentRoom.isStair(belowPosition)) {
	        currentRoom.getManel().move(Direction.DOWN);
	        ImageGUI.getInstance().update();
	    }
	}
	

}
