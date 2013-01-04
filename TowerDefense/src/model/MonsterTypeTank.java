package model;

import java.awt.Point;


public class MonsterTypeTank extends Monster {
	
	public MonsterTypeTank(Point position, double maxHP, double dmgMult,
			int bounty) {
		super(position, maxHP, dmgMult, 3, true, false, bounty);

	}
}