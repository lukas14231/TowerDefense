package model;

import java.awt.Point;

public class MonsterTypeHealer extends Monster {

	public MonsterTypeHealer(Point position, double maxHP, double dmgMult,
			int bounty) {
		super(position, maxHP, dmgMult, 3, true, true, bounty);
	}
	
}
