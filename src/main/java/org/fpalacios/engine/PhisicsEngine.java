package org.fpalacios.engine;

import java.util.ArrayList;
import static java.util.Arrays.asList;

import java.awt.geom.Area;

import java.math.BigDecimal;

import org.fpalacios.engine.gobjects.PhisicsObject;

import org.fpalacios.flibs.util.FlinkedList;
import org.fpalacios.flibs.util.Vector;

import java.math.MathContext;

public class PhisicsEngine {

	public static final BigDecimal G          = BigDecimal.valueOf(6.674).multiply( BigDecimal.TEN.pow(-11, MathContext.DECIMAL32) );
	public static final BigDecimal EARTH_MASS = BigDecimal.valueOf(5.9722).multiply( BigDecimal.TEN.pow(24, MathContext.DECIMAL32) );

	public static final Vector EARTH_GRAVITY = new Vector(BigDecimal.ZERO, BigDecimal.valueOf(98) );

	private FlinkedList<PhisicsObject> pobjects = new FlinkedList<>();

	private ArrayList<Vector> accelerations = new ArrayList<>( asList(EARTH_GRAVITY) );

	public void update(long delta) {
		gravity(delta);
		detectCollitions(delta);
	}

	private void gravity(long delta) {
		int i1 = 0;
		for (PhisicsObject pobject1 : pobjects) {
			int i2 = 0;
			for (PhisicsObject pobject2 : pobjects) {
				if (i2 > i1) {
					//       m1*m2   ^
					//F = -G -----  u21
					//        d^2
					BigDecimal deltaX = pobject1.getCenterOfMass()[0].subtract( pobject2.getCenterOfMass()[0] );
					BigDecimal deltaY = pobject1.getCenterOfMass()[1].subtract( pobject2.getCenterOfMass()[1] );
					BigDecimal distanceSquare = deltaX.pow(2).add( deltaY.pow(2) );
					BigDecimal Fg = G.multiply( pobject1.getMass().multiply( pobject2.getMass() ).divide(distanceSquare, MathContext.DECIMAL32) );
					Vector v = new Vector(deltaX, deltaY);
					v.scal( BigDecimal.ONE.divide( sqrt(distanceSquare, 32), MathContext.DECIMAL32 ) );
					v.scal( Fg.multiply( new BigDecimal(delta) ) );
					pobject2.applyForce(v);
					v.scal( Fg.multiply(new BigDecimal(-1)) );
					pobject1.applyForce(v);
				}
				i2++;
			}
			i1++;
		}
	}

	private void accelerate(long delta) {
		for (PhisicsObject pobject : pobjects)
		for (Vector acceleration : accelerations) {
			Vector vel = acceleration.clone();
			vel.scal( new BigDecimal(delta / 1000000000f) );
			pobject.applyForce( vel );
		}
	}

	private void detectCollitions(long delta) {
		//TODO: Optimizar este FLinkedList para que se puedan conseguir iteradores
		//      hacia adelante o atras a partir de cierto indice
		int i1 = 0;
		for (PhisicsObject pobject1 : pobjects) {
			int i2 = 0;
			for (PhisicsObject pobject2 : pobjects) {
				if (i2 > i1) {
					Area a = new Area(pobject1.getShape());
					a.intersect( new Area(pobject2.getShape()) );
					if ( !a.isEmpty() ) {
						pobject1.onCollide(pobject2);
						pobject2.onCollide(pobject1);
					}
				}
				i2++;
			}
			i1++;
		}
	}

	public void add(PhisicsObject pobject) {
		pobjects.add(pobject);
	}

	private static final BigDecimal TWO = new BigDecimal(2);
	public static BigDecimal sqrt(BigDecimal A, final int SCALE) {
	    BigDecimal x0 = new BigDecimal("0");
	    BigDecimal x1 = new BigDecimal(Math.sqrt(A.doubleValue()));
	    while (!x0.equals(x1)) {
	        x0 = x1;
	        x1 = A.divide(x0, SCALE, BigDecimal.ROUND_HALF_UP);
	        x1 = x1.add(x0);
	        x1 = x1.divide(TWO, SCALE, BigDecimal.ROUND_HALF_UP);
	    }
	    return x1;
	}
}
