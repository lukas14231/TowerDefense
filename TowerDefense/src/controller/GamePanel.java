package controller;

import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;
import javax.swing.Timer;

import view.GUIInterface;
import view.GameWindow;

import model.AOETower;
import model.Bullet;
import model.LaserTower;
import model.LevelGenerator;
import model.Monster;
import model.MonsterTypeHealer;
import model.MonsterTypeNormal;
import model.MonsterTypeRunner;
import model.MonsterTypeSpeed;
import model.MonsterTypeTank;
import model.Player;
import model.SlowTower;
import model.SniperTower;
import model.Spawner;
import model.Tower;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.Math;

//so sollte der GUI funktionieren
//hoffe du/ihr hast/habt nicht zu viel Arbeit die pfade zu aendern :)
public class GamePanel implements ActionListener {

	private static final int fieldSize = 25;
	private static final double sellMultiplier = 0.8;

	private int livesAtStart;
	private int tick = 70;
	private int money;
	private boolean isLost;
	private boolean isWon;
	private int actualLevel;
	private Spawner spawner = null;
	private String levelTextFile = "./resources/levels/level_03.txt";
	private Player player;
	private Vector<Point> path;
	private Vector<Point> indexPath;
	private Vector<Monster> monsters;
	private Vector<Tower> towers;
	private Vector<Bullet> bullets;
	private String levelText;
	private char[][] gameField;
	private Timer timer;
	private Timer waitTimer;
	private LevelGenerator levGen = new LevelGenerator();
	private static GUIInterface viewInterface = null;

	public Vector<Monster> getMonsters() {
		return monsters;
	}

	public Spawner getSpawner() {
		return spawner;
	}

	public Timer getTimer() {
		return timer;
	}

	public int getActualLevel() {
		return actualLevel;
	}

	public boolean gameIsLost() {
		return isLost;
	}

	public boolean gameIsWon() {
		return isWon;
	}

	public Player getPlayer() {
		return player;
	}

	public Vector<Tower> getTowers() {
		return towers;
	}

	public Vector<Bullet> getBullets() {
		return bullets;
	}

	public int getFieldSize() {
		return fieldSize;
	}

	public char[][] getGameField() {
		return gameField;
	}

	public void setTick(int newTick) {
		tick = newTick;
	}

	public int getTick() {
		return tick;
	}

	public GamePanel() {
		init();
	}

	/**
	 * initialisiert das GamePanel
	 */
	public void init() {

		livesAtStart = 7;
		money = 500;
		isLost = false;
		isWon = false;
		actualLevel = 0;
		player = new Player(livesAtStart, money);
		levelText = null;
		indexPath = new Vector<Point>();
		path = new Vector<Point>();
		monsters = new Vector<Monster>();
		towers = new Vector<Tower>();
		bullets = new Vector<Bullet>();
		levelTextFileToString();
		levelTextToGameField();
		indicesOfPath();
		indexPathToPathInMPoints();
		createNextSpawner();

	}

	public void resetGame() {
		livesAtStart = 7;
		money = 500;
		isLost = false;
		isWon = false;
		actualLevel = 0;
		player = new Player(livesAtStart, money);
		monsters = new Vector<Monster>();
		towers = new Vector<Tower>();
		bullets = new Vector<Bullet>();
		createNextSpawner();
		timer.stop();
		timer.start();
	}

	/**
	 * liest File aus "levelTextFile" ein, speichert das ganze Lvl als String in
	 * tempLevelText
	 */
	private void levelTextFileToString() {
		FileReader file;
		try {

			file = new FileReader(levelTextFile);
			int c;
			StringBuilder tempLevelText = new StringBuilder();

			while ((c = file.read()) != -1) {
				tempLevelText.append((char) c);
			}
			levelText = tempLevelText.toString();
			file.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * wandelt tempLevelText (String) in 2D char array gameField um und loescht
	 * den Zeilenumbruch
	 */
	private void levelTextToGameField() {
		// bestimmung der zeilen, spalten anzahl
		int i = 0;
		int rows;
		int cols = 1;
		int sum = levelText.length();
		while (levelText.charAt(i) != '\n') {
			cols++;
			i++;
		}
		rows = sum / cols;
		cols--; // weil \n mitgez�hlt wurden

		// 2D array des strings

		gameField = new char[cols][rows];

		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				gameField[col][row] = levelText
						.charAt((row * (cols + 1)) + col);// position
				// in
				// String,
				// ohne
				// \n
				// '\n' wurden NICHT uebernommen

			}
		}

	}

	/**
	 * zaehlt spawns
	 * 
	 * @return anzahl der spawns
	 */

	private int nrOfSpawns() {
		int count = 0;

		for (int i = 0; i < gameField[0].length; i++) {
			for (int j = 0; j < gameField.length; j++) {
				if (gameField[j][i] == 'S')
					count++;
			}
		}
		return count;
	}

	/**
	 * zaehlt ziele
	 * 
	 * @return anzahl der ziele
	 */

	private int nrOfTargets() {
		int count = 0;

		for (int i = 0; i < gameField[0].length; i++) {
			for (int j = 0; j < gameField.length; j++) {
				if (gameField[j][i] == 'X')
					count++;
			}
		}
		return count;
	}

	public Point getIndexOfSpawn() {
		return indexOfSpawn();
	}

	/**
	 * bestimmt "index" des spawns - also wo der sich im char array gameField
	 * befindet
	 * 
	 * @return Point mit Index als dessen koordinaten
	 */

	private Point indexOfSpawn() {
		int col = 0;
		int row = 0;
		for (int i = 0; i < gameField.length; i++) {
			for (int j = 0; j < gameField[0].length; j++) {
				if (gameField[i][j] == 'S') {
					col = i;
					row = j;
					break;
				}
			}
		}
		return new Point(col, row);
	}

	/**
	 * bestimmt "index" des ziels - also wo der sich im char array gameField
	 * befindet
	 * 
	 * @return Point mit Index als dessen koordinaten
	 */

	private Point indexOfTarget() {
		int col = 0;
		int row = 0;
		for (int i = 0; i < gameField.length; i++) {
			for (int j = 0; j < gameField[0].length; j++) {
				if (gameField[i][j] == 'X') {
					col = i;
					row = j;
					break;
				}
			}
		}

		return new Point(col, row);

	}

	/**
	 * checkt, ob ein char, einen pfad darstellt - hilfsfunktion f�r
	 * IndexOfFirstElementOfPath()
	 * 
	 * @param c
	 *            is der char
	 * @return is true, wenns nen pfad darstellt, ansonsten false
	 */

	private boolean charIsPath(char c) {
		boolean tempBool = false;
		if (c == '^' || c == 'v' || c == '<' || c == '>')
			tempBool = true;
		return tempBool;

	}

	/**
	 * findet erstes Pfadstueck, nach spawn - gibtn punkt zur�ck und dessen
	 * koordinaten sind die indizes
	 * 
	 * @return Punkt mit Index als dessen Koordinaten punkt hat koordinaten
	 *         -1,-1, wenn kein g�ltiger weg gefunden wird das mit exceptions
	 *         abzufangen, war mir zu aufwendig
	 */
	// geht alle 4 m�glichen f�lle durch
	private Point indexOfFirstElementOfPath() {
		Point tempPoint = new Point(-1, -1);
		Point indexSpawnPoint = indexPath.elementAt(0);
		int col = (int) indexSpawnPoint.getX();
		int row = (int) indexSpawnPoint.getY();
		// geht pfad nach oben?
		if (row > 0 && gameField[col][row - 1] != 'v'
				&& charIsPath(gameField[col][row - 1]))
			tempPoint.setLocation(col, row - 1);
		// oder nach links?
		else if (col > 0 && gameField[col - 1][row] != '>'
				&& charIsPath(gameField[col - 1][row]))
			tempPoint.setLocation(col - 1, row);
		// oder nach unten?
		else if (row < (gameField[0].length - 1)
				&& gameField[col][row + 1] != '^'
				&& charIsPath(gameField[col][row + 1]))
			tempPoint.setLocation(col, row + 1);
		// oder nach rechts?
		else if (col < (gameField.length - 1) && gameField[col + 1][row] != '<'
				&& charIsPath(gameField[col + 1][row]))
			tempPoint.setLocation(col + 1, row);
		// else ung�ltiges lvl

		return tempPoint;
	}

	/**
	 * findet (falls erstes wegst�ck nach spawn gefunden wurde)
	 * 
	 * @return
	 */
	private Point indexOfNextElementOfPath() {

		Point tempPoint = new Point(-1, -1);
		Point indexLastPathPoint = indexPath.elementAt(indexPath.size() - 1);
		int col = (int) indexLastPathPoint.getX();
		int row = (int) indexLastPathPoint.getY();
		if (col != -1) { // col is -1, wenn vorher kein weg gefunden wurde
			// diese if klausel is daf�r da, nullpointerexeptions in den
			// n�chsten if-klauseln zu verhindern
			// geht pfad nach oben?
			if (row > 0 && gameField[col][row] == '^')
				tempPoint.setLocation(col, row - 1);
			// oder nach links?
			else if (col > 0 && gameField[col][row] == '<')
				tempPoint.setLocation(col - 1, row);
			// oder nach unten?
			else if (row < (gameField[0].length - 1)
					&& gameField[col][row] == 'v')
				tempPoint.setLocation(col, row + 1);
			// oder nach rechts?
			else if (col < (gameField.length - 1) && gameField[col][row] == '>')
				tempPoint.setLocation(col + 1, row);
			// else ung�ltiges lvl
		}
		return tempPoint;

	}

	/**
	 * ruft die vorherigen funktionen so auf, dass der weg (falls es ein ziel
	 * und einen spawn gibt) als punkte (mit koordinaten die die indizies
	 * darstellen) in indexPath gespeichert wird die while schleife bricht ab,
	 * wenn kein weiteres wegst�ck gefunden wird - also -1,-1 als n�chstes
	 * element rauskommt. dieses wird aber NICHT in indexPath gespeichert, weil
	 * der pfad ja im ziel enden k�nnte
	 */

	private void indicesOfPath() {

		if (nrOfSpawns() == 1 && nrOfTargets() == 1) {
			indexPath.addElement(indexOfSpawn());
			indexPath.addElement(indexOfFirstElementOfPath());
			// Solang nicht P(-1,-1) rauskommt, bist du nicht fertig!
			while (!indexOfNextElementOfPath().equals(new Point(-1, -1))) {
				indexPath.addElement(indexOfNextElementOfPath());
			}

		}

	}

	/**
	 * pr�ft ob pfad erlaubt ist - ist eine hilfsfunktion f�r lvlcheck
	 * 
	 * @return true, wenn pfad ok is
	 */

	private boolean checkValidPath() {
		boolean tempBool = false;
		if (nrOfSpawns() == 1 && nrOfTargets() == 1) {
			if (indexPath.elementAt(indexPath.size() - 1).equals(
					indexOfTarget()))
				tempBool = true;
		}

		return tempBool;

	}

	/**
	 * hilfsfunktion die index in oberen linken eckpunkt umrechnet -> damit kann
	 * man gui element positionen berechnen - benutzt die breite, die wir f�r
	 * ein feld ansetzen
	 * 
	 * @param Index
	 *            ist punkt mit index als koordinaten
	 * @return punkt mit oberen linken eckpunkt als koordinaten
	 */

	private Point indexToCPoint(Point Index) {
		Point tempPoint = new Point(0, 0);
		tempPoint.setLocation(fieldSize * Index.getX(),
				fieldSize * Index.getY());
		return tempPoint;

	}

	/**
	 * hilfsfunktion die index in mittelpunkt von feldern umrechnet -> damit
	 * kann man die pfad positionen berechnen - benutzt die breite, die wir
	 * f�r ein feld ansetzen
	 * 
	 * @param Index
	 *            ist punkt mit index als koordinaten
	 * @return mittelpunkt der felder, die pfad darstellen
	 */

	public Point indexToMPoint(Point Index) {
		Point tempPoint = new Point(0, 0);
		tempPoint.setLocation(fieldSize * Index.getX() + (fieldSize - 1) / 2,
				fieldSize * Index.getY() + (fieldSize - 1) / 2);
		return tempPoint;

	}

	public Point cPointToMPoint(Point cPoint) {
		Point tempPoint = new Point(0, 0);
		tempPoint.setLocation(cPoint.getX() + (fieldSize - 1) / 2,
				cPoint.getY() + (fieldSize - 1) / 2);
		return tempPoint;
	}

	/**
	 * mappt IndexToMPoint auf indexPath -> hinterher haben wir fertigen pfad in
	 * path!
	 */
	public void indexPathToPathInMPoints() {
		path.removeAllElements();
		for (int i = 0; i < indexPath.size(); i++)
			path.addElement(indexToMPoint(indexPath.elementAt(i)));
	}

	/**
	 * bestimmt abstand (aufgerundet) den zwei punkte haben; abstand ist exakt,
	 * wenn x- oder y-werte der punkte gleich sind
	 * 
	 * @param p1
	 *            punkt 1
	 * @param p2
	 *            punkt 2
	 * @return abstand als int
	 */
	public int getDistance(Point p1, Point p2) {
		double x1 = p1.getX();
		double x2 = p2.getX();
		double y1 = p1.getY();
		double y2 = p2.getY();
		double temp = 0;

		temp = Math.pow((Math.abs((x1 - x2))), 2)
				+ Math.pow((Math.abs((y1 - y2))), 2);
		temp = Math.ceil(Math.sqrt(temp));

		return (int) temp;
	}

	public void addMonster(Point pos, double maxHP, double dmgMult,
			String monType, int bounty) {
		Monster monster = null;

		if (monType.equals("Runner"))
			monster = new MonsterTypeRunner(pos, maxHP, dmgMult, bounty);
		else if (monType.equals("Normal"))
			monster = new MonsterTypeNormal(pos, maxHP, dmgMult, bounty);
		else if (monType.equals("Healer"))
			monster = new MonsterTypeHealer(pos, maxHP, dmgMult, bounty);
		else if (monType.equals("Speed"))
			monster = new MonsterTypeSpeed(pos, maxHP, dmgMult, bounty);
		else if (monType.equals("Tank"))
			monster = new MonsterTypeTank(pos, maxHP, dmgMult, bounty);
		if (monster != null)
			monsters.addElement(monster);
	}

	private void removePassedMonstersAndAddBounty() {

		for (int i = 0; i < monsters.size(); i++) {
			if (monsters.elementAt(i).isReachedGoal()) {
				monsters.removeElementAt(i);
				i--;
			} else if (monsters.elementAt(i).getActualHP() <= 0) {
				player.addMoney(monsters.elementAt(i).getBounty());
				monsters.removeElementAt(i);
				i--;
			}
		}
	}

	public boolean buildTower(Point pos, String twrType) {
		boolean built = false;
		Tower tower = null;
		if (twrType.equals("aoeTower"))
			tower = new AOETower(pos, this);
		else if (twrType.equals("slowTower"))
			tower = new SlowTower(pos, this);
		else if (twrType.equals("laserTower"))
			tower = new LaserTower(pos, this);
		else if (twrType.equals("sniperTower"))
			tower = new SniperTower(pos, this);

		if (tower != null)
			if (player.getMoney() >= tower.getPrice()) {
				player.subMoney(tower.getPrice());
				addTower(tower);
				built = true;
			}
		return built;

	}

	public void addTower(Tower twr) {
		towers.addElement(twr);
	}

	public void removeTower(Tower twr) {
		towers.removeElement(twr);
	}

	public void sellTower(Tower twr) {
		double refund;
		refund = Math.round(twr.getInvestment() * sellMultiplier);
		removeTower(twr);
		player.addMoney((int) refund);
	}

	public void upgradTowerEfficiency(Tower twr) {
		int cost = twr.getUpgradeEfficiencyCost();
		if (player.getMoney() >= cost) {
			if (twr.upgradeEfficiency())
				player.subMoney(cost);
		}
	}

	public void upgradTowerRange(Tower twr) {
		int cost = twr.getUpgradeRangeCost();
		if (player.getMoney() >= cost) {
			twr.upgradeRange();
			player.subMoney(cost);
		}
	}

	public void addBullet(Bullet bul) {
		bullets.addElement(bul);
	}

	public void removeBullet(Bullet bul) {
		bullets.removeElement(bul);
	}

	/**
	 * bestimmt n�chste position eines monsters - arbeitet rekursiv
	 * 
	 * @param pos
	 *            aktuelle position
	 * @param steps
	 *            actuelle geschwindigkeit
	 * @param count
	 *            aktuelles ziel (destinationcounter)
	 * @param indexOfMonster
	 *            index des monsters im vektor - sodass man dessen counter
	 *            erh�hen kann
	 * @return n�chste position als MPoint
	 */

	private Point nextPosition(Point pos, int steps, int count,
			int indexOfMonster) {
		Point nextPos = new Point(pos);
		if (count > path.size() - 1) {// abbruch, wenn
			// letzte position im pfad (target) bereits erreicht
			monsters.elementAt(indexOfMonster).setReachedGoal(true);// dann
			// bist
			// du
			// bereits im
			// target
			if (player.subLives()) // Leben abziehen und pr�fun ob noch Leben
									// vorhadne sind
			{
				// Update der Lebensanzeige in GUI
			} else {
				isLost = true; // Spielende
			}
		} else {
			Point dest = path.elementAt(count);// ziel eingeben
			double x1 = pos.getX();
			double x2 = dest.getX();
			double y1 = pos.getY();
			double y2 = dest.getY();
			int distance = getDistance(pos, dest);
			// guckt, ob man
			if (steps < distance) {
				// umst�ndliche if klausel - guckt in welche richtung es geht
				if ((x2 - x1) < 0)
					nextPos.setLocation(x1 - steps, y1);
				else if ((x2 - x1) > 0)
					nextPos.setLocation(x1 + steps, y1);
				else if ((y2 - y1) < 0)
					nextPos.setLocation(x1, y1 - steps);
				else if ((y2 - y1) > 0)
					nextPos.setLocation(x1, y1 + steps);
			} else if (steps == distance) {// k�nnt man zusammenfassen mit
				// der n�chsten bedingung und
				// und oben k�nnte man eine abfrage
				// auf speed==0 machen , aber ein rek aufruf dauert l�nger als
				// eine
				// if abfrage, also lassen wirs so
				count++;// ziel erreicht, n�chstes ziel anpeilen
				monsters.elementAt(indexOfMonster).setActualDestinationCounter(
						count);
				nextPos.setLocation(dest);
			} else {
				count++;
				monsters.elementAt(indexOfMonster).setActualDestinationCounter(
						count);
				// rek aufruf:
				// neuer start punkt ist letztes ziel
				// neue schrittzahl is alte minus abstand
				// count wurde um 1 erh�ht
				// index bleibt
				nextPos.setLocation(nextPosition(dest,
						(steps - getDistance(pos, dest)), count, indexOfMonster));
			}
		}
		return nextPos;
	}

	/**
	 * Bewegt jedes Monster auf seine n�chste Position und setzt aktuelle
	 * geschwindigkeit wieder auf max
	 */
	private void moveAllMonstersOnceAndSetActualSpeedToMax() {

		if (monsters.size() > 0) {
			for (int i = 0; i < monsters.size(); i++) {
				Monster mon = monsters.elementAt(i);
				Point nextPos = nextPosition(mon.getPosition(),
						mon.getActualSpeed(),
						mon.getActualDestinationCounter(), i);
				mon.setPosition(nextPos);
				mon.setActualSpeed(mon.getMaxSpeed());
			}
		}
	}

	/**
	 * f�hrt alle Turm Aktionen aus
	 */
	private void actAllTowersOnce() {
		if (towers.size() > 0) {
			for (int i = 0; i < towers.size(); i++) {
				Tower twr = towers.elementAt(i);
				twr.towerAction();
			}
		}
	}

	private void actAllBulletsOnce() {
		if (bullets.size() > 0) {
			for (int i = 0; i < bullets.size(); i++) {
				Bullet bullet = bullets.elementAt(i);
				bullet.bulletAction();
			}
		}
	}

	private void actSpawnOnce() {
		if (spawner != null)
			spawner.spawnerAction();
	}

	private void createNextSpawner() {
		actualLevel++;
		double a = 1.5;
		switch (actualLevel) {
		case 1:
			spawner = new Spawner(indexToMPoint(getIndexOfSpawn()), a * 100, 1,
					"Normal", 15, 8, this);
			break;
		case 2:
			spawner = new Spawner(indexToMPoint(getIndexOfSpawn()), a * 120, 1,
					"Speed", 19, 6, this);
			break;
		case 3:
			spawner = new Spawner(indexToMPoint(getIndexOfSpawn()), a * 130, 1,
					"Runner", 21, 7, this);
			break;
		case 4:
			spawner = new Spawner(indexToMPoint(getIndexOfSpawn()), a * 140,
					0.7, "Tank", 25, 5, this);
			break;
		case 5:
			spawner = new Spawner(indexToMPoint(getIndexOfSpawn()), a * 120, 1,
					"Healer", 22, 8, this);
			break;
		case 6:
			spawner = new Spawner(indexToMPoint(getIndexOfSpawn()), a * 1000,
					0.8, "Normal", 300, 1, this);
			break;
		case 7:
			spawner = new Spawner(indexToMPoint(getIndexOfSpawn()), a * 180,
					0.9, "Runner", 25, 6, this);
			break;
		case 8:
			spawner = new Spawner(indexToMPoint(getIndexOfSpawn()), a * 220, 1,
					"Speed", 33, 5, this);
			break;
		case 9:
			spawner = new Spawner(indexToMPoint(getIndexOfSpawn()), a * 260,
					0.7, "Normal", 35, 5, this);
			break;
		case 10:
			spawner = new Spawner(indexToMPoint(getIndexOfSpawn()), a * 300,
					0.55, "Tank", 70, 3, this);
			break;
		case 11:
			spawner = new Spawner(indexToMPoint(getIndexOfSpawn()), a * 290,
					0.85, "Healer", 55, 5, this);
			break;
		case 12:
			spawner = new Spawner(indexToMPoint(getIndexOfSpawn()), a * 2000,
					0.82, "Runner", 700, 1, this);
			break;
		case 13:
			spawner = new Spawner(indexToMPoint(getIndexOfSpawn()), a * 300, 1,
					"Speed", 55, 8, this);
			break;
		case 14:
			spawner = new Spawner(indexToMPoint(getIndexOfSpawn()), a * 370,
					0.8, "Healer", 100, 4, this);
			break;
		case 15:
			spawner = new Spawner(indexToMPoint(getIndexOfSpawn()), a * 400,
					0.46, "Tank", 100, 5, this);
			break;
		case 16:
			spawner = new Spawner(indexToMPoint(getIndexOfSpawn()), a * 410,
					0.78, "Normal", 120, 6, this);
			break;
		case 17:
			spawner = new Spawner(indexToMPoint(getIndexOfSpawn()), a * 430,
					0.9, "Runner", 140, 4, this);
			break;
		case 18:
			spawner = new Spawner(indexToMPoint(getIndexOfSpawn()), a * 3100,
					0.7, "Healer", 1500, 1, this);
			break;
		case 19:
			spawner = new Spawner(indexToMPoint(getIndexOfSpawn()), a * 500,
					0.4, "Tank", 110, 8, this);
			break;
		case 20:
			spawner = new Spawner(indexToMPoint(getIndexOfSpawn()), a * 600,
					0.75, "Normal", 120, 6, this);
			break;
		case 21:
			spawner = new Spawner(indexToMPoint(getIndexOfSpawn()), a * 700,
					0.86, "Runner", 130, 5, this);
			break;
		case 22:
			spawner = new Spawner(indexToMPoint(getIndexOfSpawn()), a * 700, 1,
					"Speed", 170, 3, this);
			break;
		case 23:
			spawner = new Spawner(indexToMPoint(getIndexOfSpawn()), a * 800,
					0.8, "Healer", 200, 7, this);
			break;
		case 24:
			spawner = new Spawner(indexToMPoint(getIndexOfSpawn()), a * 5000,
					0.87, "Speed", 3100, 1, this);
			break;
		case 25:
			spawner = new Spawner(indexToMPoint(getIndexOfSpawn()), a * 800,
					0.2, "Tank", 270, 5, this);
			break;
		case 26:
			spawner = new Spawner(indexToMPoint(getIndexOfSpawn()), a * 900,
					0.8, "Runner", 220, 3, this);
			break;
		case 27:
			spawner = new Spawner(indexToMPoint(getIndexOfSpawn()), a * 1000,
					0.76, "Healer", 240, 6, this);
			break;
		case 28:
			spawner = new Spawner(indexToMPoint(getIndexOfSpawn()), a * 1100,
					0.68, "Normal", 250, 3, this);
			break;
		case 29:
			spawner = new Spawner(indexToMPoint(getIndexOfSpawn()), a * 1200,
					0.95, "Speed", 260, 6, this);
			break;
		case 30:
			spawner = new Spawner(indexToMPoint(getIndexOfSpawn()), a * 10000,
					0.05, "Tank", 31337, 1, this);
			break;
		default:
			isWon = true;
		}
	}

	/**
	 * ACTION!
	 * 
	 * wird getestet
	 */

	@Override
	public void actionPerformed(ActionEvent event) {
		// timer stopp - bisher nur testweise
		// pro tick wird das hier gemacht
		if (isLost) {
			timer.stop();
			setRun(false);
		} else if (isWon) {
			timer.stop();
			setRun(false);
		}

		if (spawner.getCounter() == 0 && monsters.isEmpty()) {
			createNextSpawner();
		}

		actSpawnOnce();
		moveAllMonstersOnceAndSetActualSpeedToMax();
		actAllTowersOnce();
		actAllBulletsOnce();
		removePassedMonstersAndAddBounty();

		// update monsters in gui, falls eine instanz von gui da ist
		if (viewInterface != null) {
			viewInterface.updateGUI();
		}

	}

	public void createNewLevel(int cols, int rows) {

		livesAtStart = 7;
		money = 500;
		isLost = false;
		isWon = false;
		actualLevel = 0;
		player = new Player(livesAtStart, money);
		levelText = null;
		indexPath = new Vector<Point>();
		path = new Vector<Point>();
		monsters = new Vector<Monster>();
		towers = new Vector<Tower>();
		bullets = new Vector<Bullet>();
		levelTextFileToString();
		levelTextToGameField();
		gameField = levGen.getNewLevel(cols, rows);
		indicesOfPath();
		indexPathToPathInMPoints();
		createNextSpawner();
		viewInterface = new GameWindow(this);
		if (levGen.getCounter() != indexPath.size())
			createNewLevel(cols, rows);

	}

	public void skipSpawnTimer() {
		getSpawner().setCooldown(1);
		getSpawner().setInitCooldown(1);
	}

	// initialisierung und start des timers
	public void startTimer() {
		timer = new Timer(tick, this);
		timer.setRepeats(true);
		timer.start();
	}

	public void startWaitTimer() {
		waitTimer = new Timer(5000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				timer.start();
			}
		});
		waitTimer.setRepeats(false);
		timer.start();
	}

	/**
	 * ALLES WAS UNTERHALB STEHT IST VORERST NUR ZUM TESTEN
	 */

	// braucht man, damit die main methode nicht vorm ersten actionevent beendet
	// wird
	private boolean run = true;

	public boolean isRun() {
		return run;
	}

	public void setRun(boolean run) {
		this.run = run;
	}

	public void setSpawner(Spawner insSpawn) {
		spawner = insSpawn;
	}

	public Vector<Point> getPath() {
		return path;
	}

	public Vector<Point> getIndexPath() {
		return indexPath;
	}

	public static void main(String[] args) {

		GamePanel controller = new GamePanel();
		viewInterface = new GameWindow(controller);

		while (controller.isRun())
			;

	}

}
