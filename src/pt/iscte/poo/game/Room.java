package pt.iscte.poo.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

import objects.Manel;
import objects.Stair;
import objects.Sword;
import objects.Trap;
import objects.Wall;
import objects.Bomb;
import objects.Consumable;
import objects.DonkeyKong;
import objects.Door;
import objects.Floor;
import objects.GoodMeat;
import objects.LevelElement;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Room {

	private List<LevelElement> levelElements = new ArrayList<>();
	private List<DonkeyKong> donkeyKongs = new ArrayList<>();
	private List<Consumable> levelConsumables= new ArrayList<>();

	private Point2D heroStartingPosition = new Point2D(1, 1);
	private Manel manel;
	private String nextRoomFile;


	public Room(String roomFile, Manel manel) {
		loadRoom(roomFile);
		this.manel = manel;
		this.manel.setPosition(heroStartingPosition);
		ImageGUI.getInstance().addImage(manel);

	}

	public int moveManel(Direction dir, boolean gravity) {
		return manel.move(dir, this, gravity);
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
						levelElements.add(new Wall(x, y));
						break;
					case 'B': // Bombe
						levelConsumables.add(new Bomb(new Point2D(x, y)));
						break;
					case 'H': // Position du héros
						heroStartingPosition = new Point2D(x, y);
						break;
					case 't': // Piège
						levelElements.add(new Trap(x, y));
						break;
					case 'm': // Viande
						levelConsumables.add(new GoodMeat(new Point2D(x, y)));
						break;
					case '0': // Prochaine salle
						levelElements.add(new Door(x, y));
						break;
					case 'S': // Escalier
						levelElements.add(new Stair(x, y));
						break;
					case 's': // Épée
						levelConsumables.add(new Sword(new Point2D(x, y), 20));
						break;
					case 'G': // Donkey Kong
						DonkeyKong donkeyKong = new DonkeyKong(new Point2D(x, y));
						donkeyKongs.add(donkeyKong);
						ImageGUI.getInstance().addImage(donkeyKong);
						break;

						
					}
					new Floor(x, y);
					
				}
				y++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public boolean isWall(Point2D position) {
		return levelElements.stream().anyMatch(el -> el.getPosition().equals(position) && el instanceof Wall);
	}

	public boolean isStair(Point2D position) {
		return levelElements.stream().anyMatch(el -> el.getPosition().equals(position) && el instanceof Stair);
	}

	public boolean isTrap(Point2D position) {
		return levelElements.stream().anyMatch(el -> el.getPosition().equals(position) && el instanceof Trap);
	}

	public boolean isNextRoomTile(Point2D position) {
		return levelElements.stream().anyMatch(el -> el.getPosition().equals(position) && el instanceof Door);
	}

	public String getNextRoomFile() {
		return nextRoomFile;
	}

	public Manel getManel() {
		return manel;
	}
	
	public Point2D getHeroStartingPosition() {
		return heroStartingPosition;
	}

	public List<DonkeyKong> getDonkeyKongs() {
		return donkeyKongs;
	}

	public void dkRemove(DonkeyKong dk) {
		donkeyKongs.remove(dk);
		dk.clearBananas();
		ImageGUI.getInstance().removeImage(dk);
		
	}

	public List<Consumable> getLevelConsumables() {
		return levelConsumables;
	}

	public void removeItem(Consumable item) {
		levelConsumables.remove(item);
		ImageGUI.getInstance().removeImage(item);
	}
	
	public void spawnBomb(Bomb bomb) {
		ImageGUI.getInstance().addImage(bomb);
	}
}