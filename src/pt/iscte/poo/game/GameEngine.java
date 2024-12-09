package pt.iscte.poo.game;

import java.awt.event.KeyEvent;
import java.util.Iterator;

import objects.Banana;
import objects.Bat;
import objects.Bomb;
import objects.Consumable;
import objects.DonkeyKong;
import objects.GoodMeat;
import objects.Manel;
import objects.Skeleton;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class GameEngine implements Observer {
	
	private final static int DIED_TIMER = 5;	
	
	private Manel manel;
	private Room currentRoom;
	private int lastTickProcessed = 0;
	private int timer = DIED_TIMER;
	
	public GameEngine() {
		this.manel = new Manel(new Point2D(0,0));
		this.currentRoom = new Room("rooms/room0.txt", manel);
		ImageGUI.getInstance().update();
	}

	@Override
	public void update(Observed source) {
		
		if (ImageGUI.getInstance().wasKeyPressed()) {
			int k = ImageGUI.getInstance().keyPressed();
			if (Direction.isDirection(k) && !stuckByDk()) {
			    Actions result = currentRoom.moveManel(Direction.directionFor(k), false);
			    
			    switch(result) {
			    	case DOOR : loadRoom(currentRoom.getNextRoomFile()); break;
			    	case PRINCESS : closeGame(); break;
			    	case OK :
				default:
					break; 
			    }
			    
			    hitByBanana();
			        
			}
			if( currentRoom.getManel().isHaveTheBomb() && (k == KeyEvent.VK_B || k == KeyEvent.VK_N)) {
		    	currentRoom.getManel().useBomb(k);
		    	currentRoom.spawnBomb(currentRoom.getManel().getBomb());
		    }
			
		}
		int t = ImageGUI.getInstance().getTicks();
		while (lastTickProcessed < t) {
			processTick();
		}
		checkCollisionBats();
		StringBuilder msg = new StringBuilder();
		msg.append("Life : "+ manel.getLife()+ " | Damage : "+manel.getDamage());
		for(DonkeyKong dk: currentRoom.getDonkeyKongs()) {
			msg.append(" | Donkey Kong Life : "+dk.getLife());
		}
		ImageGUI.getInstance().setStatusMessage(msg.toString());
		ImageGUI.getInstance().update();
		
		return;
	}

	private void processTick() {
		//System.out.println("Tic Tac : " + lastTickProcessed);
		checkCollisionBats();
		checkManelLife();
		applyGravity();
		moveDonkeyKongs(); 
		moveBats();
		checkTrap();
		hitByBanana();
		processBomb();
		processGoodMeat();
		checkManelGameLife();
		lastTickProcessed++;
	}
	
	private void checkTrap() {
		if(currentRoom.isTrap(manel.getPosition().plus(Direction.DOWN.asVector())) || currentRoom.isTrap(manel.getPosition())) {
			manel.removeLife(30);
		}
	}

	private void checkManelLife() {
		if(manel.getLife() <= 0 && timer == DIED_TIMER) {
			manel.removeGameLife(1);
			ImageGUI.getInstance().removeImage(manel);
			ImageGUI.getInstance().addImage(new Skeleton(manel.getPosition()));
			
			timer --;

		}else if (timer < DIED_TIMER) {
			timer --;
			if(timer ==0) {
				timer = DIED_TIMER;
				manel.setPosition(currentRoom.getHeroStartingPosition());
				manel.setMaxLife();
				currentRoom.spawnManel(manel);
				//loadRoom("room0.txt");
			}
		}
		
	}
	
	private void checkManelGameLife() {
		if(manel.getGameLife() == 0) {
			manel = new Manel(currentRoom.getHeroStartingPosition());
			loadRoom("room0.txt");
		}
		
	}
	
	private void checkCollisionBats() {
	    Iterator<Bat> iterator = currentRoom.getBats().iterator();

	    while (iterator.hasNext()) {
	        Bat bat = iterator.next();
	        if (bat.getPosition().equals(manel.getPosition())) {
	            manel.removeLife(bat.getDamage());
	            iterator.remove(); // Suppression sécurisée via l'iterator
	            currentRoom.removeBat(bat);
	        }
	    }
	}

	
	private boolean stuckByDk() {
		for(DonkeyKong dk: currentRoom.getDonkeyKongs()) {
			if((dk.getPosition().getY() == manel.getPosition().getY()) && (dk.getPosition().getX() + 1 == manel.getPosition().getX()) && (dk.getPosition().getX() < manel.getPosition().getX()) || dk.getPosition().equals(manel.getPosition())) {
				manel.removeLife(dk.getDamage());
				dk.removeLife(manel.getDamage());
				if(dk.getLife() <= 0) {
					currentRoom.dkRemove(dk);
				}
							
				return true;
			}
		}
		return false;
	}
	
	private void hitByBanana() {
		for(DonkeyKong ban: currentRoom.getDonkeyKongs()) {
			for(Banana elt: ban.getBananas()) {
				if(manel.getPosition().equals(elt.getPosition())) {
					manel.removeLife(30);
				}
			}
		}
	}

	public void loadRoom(String nextRoomFile) {
	    ImageGUI.getInstance().clearImages();
	    currentRoom = new Room("rooms/" + nextRoomFile, manel);
	    ImageGUI.getInstance().update();
	}
	
	private void applyGravity() {
	    Point2D belowPosition = manel.getPosition().plus(Direction.DOWN.asVector());
	    
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
	
	private void moveBats() {
		for(Bat bat : currentRoom.getBats()) {
			bat.moveRandom(currentRoom);
		}
		ImageGUI.getInstance().update();
	}
	
	private void closeGame() {
	    ImageGUI.getInstance().dispose();
	}
	
	private void processGoodMeat() {
		for(GoodMeat meat : currentRoom.getGoodMeat()) {
			meat.tick(currentRoom);
		}
	};
	
	private void processBomb() {
		for(Bomb bomb : currentRoom.getDroppedBombs()) {
			bomb.tick(currentRoom);
		}
	};


}
