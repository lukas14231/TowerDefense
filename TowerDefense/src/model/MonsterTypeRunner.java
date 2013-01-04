package model;

import java.awt.Point;

public class MonsterTypeRunner extends Monster {

	public MonsterTypeRunner(Point position, double maxHP, double dmgMult,
			int bounty) {
		super(position, maxHP, dmgMult, 6, true, false, bounty);
	}

}
