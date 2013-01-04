package model;

/**
 * Die Klasse Player repräsentiert den Spieler mit Anzahl seiner Leben und
 * seinem zur Verfügung stehendem Geld
 * 
 * 
 * @author Coop
 * 
 */
public class Player {

	private int lives;
	private int money;

	/**
	 * Der Konstruktor initialisiert einen Spieler mit Leben und Geld
	 * 
	 * @param lives
	 *            die Anzahl Leben
	 * @param money
	 *            das Geld
	 */
	public Player(int lives, int money) {
		setLives(lives);
		setMoney(money);
	}

	/**
	 * Fügt dem Kontostand Geld hinzu
	 * 
	 * @param money
	 *            das hinzuzufügende Geld
	 */
	public void addMoney(int money) {
		setMoney(getMoney() + money);
	}

	/**
	 * Zieht dem Kontostand den übergebnen Wert ab
	 * 
	 * @param money
	 *            der abzuziehende Wert
	 * @return true wenn der Wert abgezogen werden konnte und false, falls der
	 *         Wert größer war als der aktuelle Kontostand
	 */
	public boolean subMoney(int money) {
		if (getMoney() - money < 0) {
			return false;
		} else {
			setMoney(getMoney() - money);
			return true;
		}
	}
	/**
	 * Reduziert die Leben um 1 und gibt das Ergebnis von livesLeft() zurück.
	 * True wenn noch Leben vorhanden sind
	 * False wenn nicht
	 * 
	 * @return livesLeft()
	 */
	public boolean subLives()
	{
		setLives(getLives() -1);
		
		return livesLeft();
	}
	/**
	 * Gibt true zurück wenn noch Leben vorhanden sind, false wenn nicht
	 * 
	 * @return true wenn noch Leben vorhanden sind, false wenn nicht
	 */
	public boolean livesLeft() {
		if (getLives() <= 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Gibt die Anzahl Leben zurück
	 * 
	 * @return die Anzahl an Leben
	 */
	public int getLives() {
		return this.lives;
	}

	/**
	 * Setzt die Anzahl an Leben auf den übergebenen Wert
	 * 
	 * @param lives
	 *            die Anzahl Leben
	 */
	private void setLives(int lives) {
		this.lives = lives;
	}

	/**
	 * Gibt den aktuellen Kontostand zurück
	 * 
	 * @return den aktuellen Kontostand
	 */
	public int getMoney() {
		return this.money;
	}

	/**
	 * Setzt den Kontostand auf den übergebenen Wert
	 * 
	 * @param money
	 *            der übergebene Wert
	 */
	private void setMoney(int money) {
		this.money = money;
	}
}
