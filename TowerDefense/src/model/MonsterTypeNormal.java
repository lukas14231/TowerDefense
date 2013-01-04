package model;

import java.awt.Point;

public class MonsterTypeNormal extends Monster {
	
	public MonsterTypeNormal(Point position, double maxHP, double dmgMult,
			int bounty) {
		super(position, maxHP, dmgMult, 3, true, false, bounty);

	}
}
