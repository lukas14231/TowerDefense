package model;

import java.awt.Point;
import java.util.Vector;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

import controller.GamePanel;


public class SlowTower extends Tower {

	private double slow = 0.67;

	private int costRange2 = 100;
	private int costRange3 = 120;
	private int costRange4 = 140;
	private int costRange5 = 160;
	private int costEfficiency2 = 300;
	private int range2 = 65;
	private int range3 = 80;
	private int range4 = 95;
	private int range5 = 120;

	private Vector<Monster> monsters;

	public SlowTower(Point pos, GamePanel control) {
		super(pos, control);
		setRange(55);
		setPrice(100);
		investment = price;
		maxEfficiencyLevel = 2;
		maxRangeLevel = 5;
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

	public double getSlow() {
		return slow;
	}

	public int getInvestment() {
		return investment;
	}

	public void setSlow(double slow) {
		this.slow = slow;
	}

	@Override
	public void towerAction() {
		monsters = controller.getMonsters();
		for (Monster monster : monsters) {
			if (controller.getDistance(monster.getPosition(), this.position) < range) {
				if (monster.isSlowable()) {
					int actSpeed = monster.getActualSpeed();
					int maxSpeed = monster.getMaxSpeed();
					if (actSpeed > (int) (Math.round(maxSpeed * slow)))
						monster.setActualSpeed((int) (Math.round(maxSpeed
								* slow)));
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
		if (efficiencyLevel == 1) {
			setSlow(0.34);
			efficiencyLevel++;
			investment += costEfficiency2;
			upgraded = true;
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
		if (getEfficiencyLevel() != getMaxEfficiencyLevel()) {
			cost = costEfficiency2;
		}
		return cost;
	}

}
