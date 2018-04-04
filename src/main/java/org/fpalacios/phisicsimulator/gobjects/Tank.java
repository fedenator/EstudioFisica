package org.fpalacios.phisicsimulator.gobjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;

import java.math.BigDecimal;

import org.fpalacios.phisicsimulator.abilities.SimpleShoot;
import org.fpalacios.engine.gobjects.Mob;
import org.fpalacios.engine.gobjects.PhisicsObject;
import org.fpalacios.flibs.util.Vector;

public class Tank extends Mob {

	protected SimpleShoot simpleShot;

	protected Vector vel = new Vector(BigDecimal.ZERO, BigDecimal.ZERO);
	protected BigDecimal x, y;

	protected Polygon shape;

	//DEBUG:
	long timer;
	boolean done = false;

	// private static final int vertices[][] = {
	// 		{15, 0},
	// 		{30, 20},
	// 		{30, 50},
	// 		{0,  50},
	// 		{0,  20}
	// 	};

	private static final int vertices[][] = {
			{  0,  0 },
			{ 10,  0 },
			{ 10, 10 },
			{  0, 10 },
		};

	public Color color;

	public Tank(BigDecimal x, BigDecimal y, Color color) {
		this.color = color;

		this.x = x;
		this.y = y;

		shape = new Polygon();
		for (int point[] : vertices)
			shape.addPoint((x.toBigInteger().intValue()+point[0]), (y.toBigInteger().intValue()+point[1]));

		addAbilitie( simpleShot = new SimpleShoot(this) );
	}

	public void update(long delta) {
		x = x.add( vel.x.multiply( new BigDecimal(""+(delta/1000000000f)) ) );
		y = y.add( vel.y.multiply( new BigDecimal(""+(delta/1000000000f)) ) );

		System.out.println("X: "+x+"||Y: "+y);

		//DEBUG:
		timer += delta;

		shape = new Polygon();
		for (int point[] : vertices)
			shape.addPoint((x.toBigInteger().intValue()+point[0]), (y.toBigInteger().intValue()+point[1]));

	}

	public void render(Graphics g) {
		System.out.println("sup");
		g.setColor(color);
		((Graphics2D) g).fillPolygon(shape);
	}

	public Shape getShape() {
		return shape;
	}

	public void applyForce(Vector v) {

		//Segunda ley de newton, la aceleracion(cambio en la velocidad)
		//es directamente proporcional a la fuerza e inversamente proporcional a la masa del objeto
		v = v.clone();
		v.scal( BigDecimal.ONE.divide( getMass() ) );
		vel.add(v);
	}

	public BigDecimal getMass() {
		return org.fpalacios.engine.PhisicsEngine.EARTH_MASS;
		// return BigDecimal.ONE;
	}

	public BigDecimal[] getCenterOfMass() {
		BigDecimal res[] = {x, y};
		return res;
	}

	public void onCollide(PhisicsObject obj) {
		if (done) return;
		done = true;
		System.out.println(timer);
	}

}
