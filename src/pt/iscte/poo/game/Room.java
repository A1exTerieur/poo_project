package pt.iscte.poo.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

import objects.Manel;
import objects.Stair;
import objects.Wall;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Room {
	
    private List<Wall> walls = new ArrayList<>();
    private List<Stair> stairs = new ArrayList<>();
	private Point2D heroStartingPosition = new Point2D(1, 1);
	private Manel manel;
	private Point2D nextRoomTile;
    private String nextRoomFile;
	
	public Room(String roomFile) {
        loadRoom(roomFile);
        manel = new Manel(heroStartingPosition);
        ImageGUI.getInstance().addImage(manel);

        for (Wall wall : walls) {
            ImageGUI.getInstance().addImage(wall);
        }
    }

	public boolean moveManel(Direction dir) {
        Point2D targetPosition = manel.getPosition().plus(dir.asVector());
        Point2D currentPosition = manel.getPosition();
        
        if (!isWall(targetPosition)) {
            
        	if(dir == Direction.UP  || dir == Direction.DOWN) {
        		if(isStair(targetPosition) || isStair(currentPosition)) {
        			  manel.move(dir);
        		}
        	}else {
        		manel.move(dir);
        	}
        	
            if (isNextRoomTile(targetPosition)) {
                return true;
            }

        }
        return false;
    }
	
	 private void loadRoom(String roomFile) {
	        try (Scanner scanner = new Scanner(new File(roomFile))) {
	            int y = 0;

	            while (scanner.hasNextLine()) {
	                String line = scanner.nextLine();

	                if (line.startsWith("#")) {
	                    String[] parts = line.split(";");
	                    nextRoomFile = parts[1].trim();
	                    continue;
	                }

	                for (int x = 0; x < line.length(); x++) {
	                    char c = line.charAt(x);
	                    switch (c) {
	                        case 'W': // Mur
	                            walls.add(new Wall(x, y));
	                            break;
	                        case 'H': // Position du héros
	                            heroStartingPosition = new Point2D(x, y);
	                            break;
	                        case '0': // Case menant à la salle suivante
	                            nextRoomTile = new Point2D(x, y);
	                            break;
	                        case 'S': // Escalier
	                            Stair stair = new Stair(x, y);
	                            stairs.add(stair);
	                            ImageGUI.getInstance().addImage(stair);
	                            break;
	                    }
	                }
	                y++;
	            }

	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        }
	    }
	
    public boolean isWall(Point2D position) {
        for (Wall wall : walls) {
            if (wall.getPosition().equals(position)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isStair(Point2D position) {
        for (Stair stair : stairs) {
            if (stair.getPosition().equals(position)) {
                return true;
            }
        }
        return false;
    }

    private boolean isNextRoomTile(Point2D position) {
        return position.equals(nextRoomTile);
    }
    
    public String getNextRoomFile() {
        return nextRoomFile;
    }
    
    public Manel getManel() {
		return manel;
	}
	
}