package model;

import java.awt.Point;

import controller.GamePanel;

public class Spawner {

	private int counter;
	private int cooldown;
	private int initCooldown;
	private Point spawnPosition;
	private double maxHP;
	private double dmgMultiplier;
	private String monsterType;
	private GamePanel controller;
	private int bounty;

	public Spawner(Point spawnPos, double maxiHP, double dmgMult,
			String monType, int bount, int number, GamePanel control) {
		counter = number;
		initCooldown = 70;
		cooldown = initCooldown;
		spawnPosition = spawnPos;
		maxHP = maxiHP;
		dmgMultiplier = dmgMult;
		monsterType = monType;
		controller = control;
		bounty = bount;

	}

	public void spawnerAction() {
		if (cooldown > 0) {
			initCooldown--;
			cooldown--;
		} else if (counter > 0) {
			controller.addMonster(spawnPosition, maxHP, dmgMultiplier,
					monsterType, bounty);
			counter--;
			cooldown = 10;
		}

	}

	public int getCounter() {
		return counter;
	}

	public void setInitCooldown(int initCD) {
		initCooldown = initCD;
	}
	
	public void setCooldown(int CD) {
		cooldown = CD;
	}

	public void setInitCooldownInSec(int initSec) {
		initCooldown = initSec * controller.getTick();
	}

	public int getRestOfInitColldownInSec() {
		double time = ((double) (initCooldown * controller.getTick())) / 1000;

		return (int) Math.ceil(time);
	}

}
