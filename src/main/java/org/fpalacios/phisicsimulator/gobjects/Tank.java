package org.fpalacios.phisicsimulator.gobjects;

import java.math.BigDecimal;

import java.awt.Color;

import org.fpalacios.phisicsimulator.abilities.SimpleShoot;
import org.fpalacios.engine.gobjects.Mob;
import org.fpalacios.engine.gobjects.PhisicsObject;

import org.fpalacios.flibs.util.Vector;

public class Tank extends Mob {

	protected SimpleShoot simpleShot;

	//DEBUG:
	long timer;
	boolean done = false;

	private static final Vector[] vertices = {
			new Vector(  0,  0 ),
			new Vector( 10,  0 ),
			new Vector( 10, 10 ),
			new Vector(  0, 10 )
		};

	public Tank(BigDecimal x, BigDecimal y, Color color) {
		super(BigDecimal.ONE, color, 1, new Vector[] {
				new Vector(  0,  0 ),
				new Vector( 10,  0 ),
				new Vector( 10, 10 ),
				new Vector(  0, 10 )
			});
		this.shape.translate( new Vector(x, y) );
		addAbilitie( simpleShot = new SimpleShoot(this) );
	}

	public void update(long delta) {
		super.update(delta);

		//DEBUG:
		timer += delta;
	}

	public void onCollide(PhisicsObject obj) {
		if (done) return;
		done = true;
		System.out.println(timer);
	}

}
