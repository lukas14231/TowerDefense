package model;

import java.awt.Point;
import java.util.Vector;

import controller.GamePanel;


public class AOETower extends Tower {

	private Vector<Monster> monsters;

	private int costRange2 = 60;
	private int costRange3 = 70;
	private int costRange4 = 75;
	private int costRange5 = 80;
	private int costEfficiency2 = 60;
	private int costEfficiency3 = 120;
	private int costEfficiency4 = 260;
	private int costEfficiency5 = 400;

	private int range2 = 60;
	private int range3 = 72;
	private int range4 = 88;
	private int range5 = 110;

	private double dpt = 1.3;
	private double dpt2 = 2.2;
	private double dpt3 = 5;
	private double dpt4 = 10;
	private double dpt5 = 20;

	public AOETower(Point pos, GamePanel control) {
		super(pos, control);
		setPrice(75);
		setRange(50);
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

	protected double getDpt() {
		return dpt;
	}

	protected void setDpt(double dpt) {
		this.dpt = dpt;
	}

	@Override
	public void towerAction() {
		monsters = controller.getMonsters();
		for (int i = 0; i < monsters.size(); i++) {
			Monster monster = monsters.elementAt(i);
			Point monPos = monster.getPosition();
			int distance = controller.getDistance(monPos, position);
			if (distance < range) {
				if (monster.isHealable()) {
					monster.setActualHP(monster.getActualHP() + dpt);
					if (monster.getActualHP() > monster.getMaxHP())
						monster.setActualHP(monster.getMaxHP());
				} else
					monster.setActualHP(monster.getActualHP()
							- monster.getDmgMultiplier() * dpt);
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
