package model;

import java.awt.Point;

import controller.GamePanel;


public abstract class Tower {

	public Point position;
	public int range;
	public int price;
	public int investment; //gesamtkosten -> wichtig, wenn wir tuerme verkaufen
	public int rangeLevel;
	public int maxRangeLevel;
	public int efficiencyLevel;
	public int maxEfficiencyLevel;
	public GamePanel controller;

	public Tower(Point pos, GamePanel control) {
		controller = control;
		setPosition(pos);
		setRangeLevel(1);
		setEfficiencyLevel(1);
	}

	public int getInvestment(){
		return investment;		
	}
	
	public Point getPosition() {
		return position;
	}

	protected void setPosition(Point position) {
		this.position = position;
	}

	public int getRange() {
		return range;
	}

	protected void setRange(int range) {
		this.range = range;
	}

	public int getPrice() {
		return price;
	}

	protected void setPrice(int price) {
		this.price = price;
	}

	public int getRangeLevel() {
		return rangeLevel;
	}

	protected void setRangeLevel(int rangeLevel) {
		this.rangeLevel = rangeLevel;
	}

	public int getEfficiencyLevel() {
		return efficiencyLevel;
	}

	protected void setEfficiencyLevel(int efficiencyLevel) {
		this.efficiencyLevel = efficiencyLevel;
	}
	
	protected int getMaxRangeLevel()
	{
		return maxRangeLevel;
	}
	
	protected int getMaxEfficiencyLevel()
	{
		return maxEfficiencyLevel;
	}
	


	/**
	 * Aktion des Turms
	 */
	public abstract void towerAction();
	
	public abstract boolean upgradeRange();
	public abstract boolean upgradeEfficiency();
	
	public abstract int getUpgradeRangeCost();
	public abstract int getUpgradeEfficiencyCost();
	
	public abstract int getRangeByLevel(int level);
	
}
