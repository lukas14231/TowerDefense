package model;

import java.awt.Point;

import controller.GamePanel;


public class Bullet {

	private GamePanel controller;
	private Monster target;
	private Point position;
	private double dmg;
	private int hitRange = 2;
	private double speed = 10;

	public Bullet(Point pos, Monster tar, double dmg2, GamePanel control) {
		target = tar;
		position = pos;
		controller = control;
		dmg = dmg2;
	}

	private boolean moveOnceAndCheckIfHit() {
		boolean tempBool = false;
		double x1 = position.getX();
		double y1 = position.getY();
		double x2 = target.getPosition().getX();
		double y2 = target.getPosition().getY();
		double dx = x2 - x1;
		double dy = y2 - y1;
		double xFraction = Math.abs(dx) / (Math.abs(dx) + Math.abs(dy));
		double yFraction = Math.abs(dy) / (Math.abs(dx) + Math.abs(dy));
		double nextX;
		//BUGGED!
		if (dx > 0)
			nextX = x1 + Math.round(xFraction * speed);
		else
			nextX = x1 - Math.round(xFraction * speed);
		double nextY;
		if (dy > 0)
			nextY = y1 + Math.round(yFraction * speed);
		else
			nextY = y1 - Math.round(yFraction * speed);
		position.setLocation(nextX, nextY);
		if (Math.signum(dx) != Math.signum(x2 - nextX)
				|| Math.signum(dy) != Math.signum(y2 - nextY))
			tempBool = true;

		return tempBool;
	}

	// eigentlich unnötig, aber wir behaltens ma
	private boolean isInHitRange() {
		boolean tempBool = false;
		if (controller.getDistance(target.getPosition(), position) < hitRange)
			tempBool = true;

		return tempBool;
	}

	public void bulletAction() {
		if (target == null)
			controller.removeBullet(this);
		else {
			if (moveOnceAndCheckIfHit()) {
				target.setActualHP(target.getActualHP() - dmg);
				controller.removeBullet(this);
			}
		}
	}

	public Monster getTarget() {
		return target;
	}

	public Point getPosition() {
		return position;
	}

}
