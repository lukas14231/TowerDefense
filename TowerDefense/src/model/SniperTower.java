package model;

import java.awt.Point;
import java.util.Vector;

import controller.GamePanel;


public class SniperTower extends Tower {

	private Vector<Monster> monsters;

	private int costRange2 = 30;
	private int costRange3 = 40;
	private int costRange4 = 50;
	private int costRange5 = 60;
	private int costEfficiency2 = 60;
	private int costEfficiency3 = 90;
	private int costEfficiency4 = 170;
	private int costEfficiency5 = 280;

	private int range2 = 140;
	private int range3 = 160;
	private int range4 = 185;
	private int range5 = 210;

	private double dmg = 90;
	private double dmg2 = 154;
	private double dmg3 = 258;
	private double dmg4 = 383;
	private double dmg5 = 522;
	private int cdTime = 30;
	private int cdTime2 = 29;
	private int cdTime3 = 28;
	private int cdTime4 = 25;
	private int cdTime5 = 20;

	private int coolDown;

	public SniperTower(Point pos, GamePanel control) {
		super(pos, control);
		setPrice(25);
		setRange(120);
		investment = price;
		coolDown = cdTime;
		maxEfficiencyLevel = 5;
		maxRangeLevel = 5;
	}
	
	public int getCdTimeByLevel (int level) {
		int value = 0;
		switch (level) {
		case 1:
			value = cdTime;
			break;
		case 2:
			value = cdTime2;
			break;
		case 3:
			value = cdTime3;
			break;
		case 4:
			value = cdTime4;
			break;
		case 5:
			value = cdTime5;
			break;
		}
		return value;
	}
	
	public double getDmgByLevel(int level) {
		double value = 0;
		switch (level) {
		case 1:
			value = dmg;
			break;
		case 2:
			value = dmg2;
			break;
		case 3:
			value = dmg3;
			break;
		case 4:
			value = dmg4;
			break;
		case 5:
			value = dmg5;
			break;
		}
		return value;
	}
	
	@Override
	public int getRangeByLevel(int level) {
		int value = 0;
		switch (level) {
		case 1:
			value = 50;
			break;
		case 2:
			value = range2;
			break;
		case 3:
			value = range3;
			break;
		case 4:
			value = range4;
			break;
		case 5:
			value = range5;
			break;
		}
		return value;
	}

	public double getDmg() {
		return dmg;
	}

	public void setDmg(double dmg) {
		this.dmg = dmg;
	}

	public int getCoolDown() {
		return coolDown;
	}

	public void setCoolDown(int coolDown) {
		this.coolDown = coolDown;
	}

	@Override
	public void towerAction() {
		if (coolDown > 0)
			setCoolDown(getCoolDown() - 1);
		else {
			monsters = controller.getMonsters();
			if (monsters.size() > 0) {
				for (int i = monsters.size() - 1; i >= 0; i--) {
					Monster monster = monsters.elementAt(i);
					if (controller.getDistance(monster.getPosition(),
							this.position) < range) {
						Point pos = new Point(position);
						Bullet bullet = new Bullet(pos, monster, dmg,
								controller);
						controller.addBullet(bullet);
						setCoolDown(cdTime);
						break;
					}
				}
			}
		}

	}

	@Override
	public boolean upgradeRange() {
		boolean upgraded = false;
		switch (rangeLevel) {
		case 1:
			setRange(range2);
			investment += costRange2;
			rangeLevel++;
			upgraded = true;
			break;
		case 2:
			setRange(range3);
			investment += costRange3;
			rangeLevel++;
			upgraded = true;
			break;
		case 3:
			setRange(range4);
			investment += costRange4;
			rangeLevel++;
			upgraded = true;
			break;
		case 4:
			setRange(range5);
			investment += costRange5;
			rangeLevel++;
			upgraded = true;
			break;

		}

		return upgraded;
	}

	@Override
	public boolean upgradeEfficiency() {
		boolean upgraded = false;
		switch (efficiencyLevel) {
		case 1:
			setDmg(dmg2);
			cdTime = cdTime2;
			investment += costEfficiency2;
			efficiencyLevel++;
			upgraded = true;
			break;
		case 2:
			setDmg(dmg3);
			cdTime = cdTime3;
			investment += costEfficiency3;
			efficiencyLevel++;
			upgraded = true;
			break;
		case 3:
			setDmg(dmg4);
			cdTime = cdTime4;
			investment += costEfficiency4;
			efficiencyLevel++;
			upgraded = true;
			break;
		case 4:
			setDmg(dmg5);
			cdTime = cdTime5;
			investment += costEfficiency5;
			efficiencyLevel++;
			upgraded = true;
			break;

		}
		return upgraded;
	}

	@Override
	public int getUpgradeRangeCost() {
		int cost = 0;
		switch (rangeLevel) {
		case 1:
			cost = costRange2;
			break;
		case 2:
			cost = costRange3;
			break;
		case 3:
			cost = costRange4;
			break;
		case 4:
			cost = costRange5;
			break;
		}
		return cost;
	}

	@Override
	public int getUpgradeEfficiencyCost() {
		int cost = 0;
		switch (efficiencyLevel) {
		case 1:
			cost = costEfficiency2;
			break;
		case 2:
			cost = costEfficiency3;
			break;
		case 3:
			cost = costEfficiency4;
			break;
		case 4:
			cost = costEfficiency5;
			break;
		}
		return cost;
	}

}
