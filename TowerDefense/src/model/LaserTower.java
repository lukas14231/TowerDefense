package model;

import java.awt.Point;
import java.util.Vector;

import controller.GamePanel;


public class LaserTower extends Tower {

	private Vector<Monster> monsters;
	private Monster target;

	private int costRange2 = 40;
	private int costRange3 = 50;
	private int costRange4 = 60;
	private int costRange5 = 70;
	private int costEfficiency2 = 60;
	private int costEfficiency3 = 100;
	private int costEfficiency4 = 200;
	private int costEfficiency5 = 300;

	private int range2 = 85;
	private int range3 = 100;
	private int range4 = 115;
	private int range5 = 135;

	private double dpt = 2;
	private double dpt2 = 3;
	private double dpt3 = 7;
	private double dpt4 = 12;
	private double dpt5 = 25;

	public LaserTower(Point pos, GamePanel control) {
		super(pos, control);
		setRange(75);
		setPrice(50);
		investment = price;
		maxEfficiencyLevel = 5;
		maxRangeLevel = 5;
	}
	
	public double getDptByLevel(int level) {
		double value = 0;
		switch (level) {
		case 1:
			value = dpt;
			break;
		case 2:
			value = dpt2;
			break;
		case 3:
			value = dpt3;
			break;
		case 4:
			value = dpt4;
			break;
		case 5:
			value = dpt5;
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

	@Override
	public void towerAction() {
		target = null;
		monsters = controller.getMonsters();

		if (monsters.size() > 0) {
			for (int i = 0; i < monsters.size(); i++) {
				Monster monster = monsters.elementAt(i);
				if (controller
						.getDistance(monster.getPosition(), this.position) < range) {
					setTarget(monster);
					target.setActualHP(target.getActualHP()
							- target.getDmgMultiplier() * dpt);
					break;
				}
			}
		}
	}

	public double getDpt() {
		return dpt;
	}

	public void setDpt(double dpt) {
		this.dpt = dpt;
	}

	public Monster getTarget() {
		return target;
	}

	public void setTarget(Monster target) {
		this.target = target;
	}

	public int getInvestment() {
		return investment;
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
			setDpt(dpt2);
			investment += costEfficiency2;
			efficiencyLevel++;
			upgraded = true;
			break;
		case 2:
			setDpt(dpt3);
			investment += costEfficiency3;
			efficiencyLevel++;
			upgraded = true;
			break;
		case 3:
			setDpt(dpt4);
			investment += costEfficiency4;
			efficiencyLevel++;
			upgraded = true;
			break;
		case 4:
			setDpt(dpt5);
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

}
