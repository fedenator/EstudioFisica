package org.fpalacios.engine.abilities;

public class Casteable implements Ability{
	public long cd;
	public long timer = 0;
	public boolean canCast = true;

	public Casteable(double cd) {
		double aux = cd * 1000000000f;
		this.cd = (long) aux;
	}

	protected void cast() {
		canCast = false;
	}

	public void update(long delta) {
		if (canCast) return;

		timer += delta;
		if (timer >= cd) {
			timer = 0;
			canCast = true;
		}
	}
}
