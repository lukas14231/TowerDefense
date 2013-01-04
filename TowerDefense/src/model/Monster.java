package model;

import java.awt.Point;

public class Monster {

	private double maxHP;
	private double actualHP;
	private int maxSpeed;
	private int actualSpeed;
	private Point position;
	private int actualDestinationCounter;
	private double dmgMultiplier;
	private boolean slowable;
	private boolean healable;
	private int bounty;
	private boolean reachedGoal;

	public Monster(Point position, double maxHP, double dmgMultiplier, int maxSpeed, 
			boolean slowable, boolean healable, int bounty) {

		setMaxHP(maxHP);
		setActualHP(maxHP);
		setMaxSpeed(maxSpeed);
		setActualSpeed(maxSpeed);
		setPosition(position);
		setActualDestinationCounter(1);
		setDmgMultiplier(dmgMultiplier);
		setSlowable(slowable);
		setHealable(healable);
		setBounty(bounty);
		setReachedGoal(false);

	}

	public double getMaxHP() {
		return maxHP;
	}

	protected void setMaxHP(double maxHP2) {
		this.maxHP = maxHP2;
	}

	public double getActualHP() {
		return actualHP;
	}

	protected void setActualHP(double maxHP2) {
		this.actualHP = maxHP2;
	}

	public int getMaxSpeed() {
		return maxSpeed;
	}

	protected void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public int getActualSpeed() {
		return actualSpeed;
	}

	public void setActualSpeed(int actualSpeed) {
		this.actualSpeed = actualSpeed;
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public int getActualDestinationCounter() {
		return actualDestinationCounter;
	}

	public void setActualDestinationCounter(int actualDestinationCounter) {
		this.actualDestinationCounter = actualDestinationCounter;
	}

	public double getDmgMultiplier() {
		return dmgMultiplier;
	}

	protected void setDmgMultiplier(double dmgMultiplier2) {
		this.dmgMultiplier = dmgMultiplier2;
	}

	protected boolean isSlowable() {
		return slowable;
	}

	protected void setSlowable(boolean slowable) {
		this.slowable = slowable;
	}

	protected boolean isHealable() {
		return healable;
	}

	protected void setHealable(boolean healable) {
		this.healable = healable;
	}

	public int getBounty() {
		return bounty;
	}

	public void setBounty(int bounty) {
		this.bounty = bounty;
	}

	public boolean isReachedGoal() {
		return reachedGoal;
	}

	public void setReachedGoal(boolean reachedGoal) {
		this.reachedGoal = reachedGoal;
	}

	

}
