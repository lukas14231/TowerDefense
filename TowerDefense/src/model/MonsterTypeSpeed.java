package model;

import java.awt.Point;

public class MonsterTypeSpeed extends Monster {

	public MonsterTypeSpeed(Point position, double maxHP, double dmgMult,
			int bounty) {
		super(position, maxHP, dmgMult, 3, true, false, bounty);

	}
	
}
