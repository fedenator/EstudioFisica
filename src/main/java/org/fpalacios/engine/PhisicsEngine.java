package org.fpalacios.engine;

import java.util.ArrayList;

import java.awt.geom.Area;

import java.math.BigDecimal;

import org.fpalacios.engine.gobjects.PhisicsObject;

import org.fpalacios.flibs.util.FlinkedList;
import org.fpalacios.flibs.util.Vector;

import java.math.MathContext;

public class PhisicsEngine {

	private static PhisicsEngine instance;

	/*----------------------------- Constantes -------------------------------*/
	public static final BigDecimal G          = BigDecimal.valueOf(6.674).multiply( BigDecimal.TEN.pow(-11, MathContext.DECIMAL32) );
	public static final BigDecimal EARTH_MASS = BigDecimal.valueOf(5.9722).multiply( BigDecimal.TEN.pow(24, MathContext.DECIMAL32) );

	public static final Vector EARTH_GRAVITY = new Vector(BigDecimal.ZERO, BigDecimal.valueOf(98) );

	private static final BigDecimal TWO = new BigDecimal(2);

	/*----------------------------- Propiedades ------------------------------*/
	private FlinkedList<PhisicsObject> pobjects = new FlinkedList<>();

	//Aceleraciones constantes que no se calculan, por ejemplo para no tener que
	//la gravedad de la tierra si toda la simulacion sucede en la tierra
	private ArrayList<Vector> accelerations = new ArrayList<>();


	/*---------------------------- Constructores -----------------------------*/
	private PhisicsEngine() {}

	/*------------------------------- Funciones ------------------------------*/
	public void update(long delta) {
		gravity(delta);
		accelerate(delta);
		collides(delta);
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
					v.scal( BigDecimal.ONE.divide(sqrt(distanceSquare, 32), MathContext.DECIMAL32) );
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
		for (PhisicsObject pobject : pobjects) {
			for (Vector acceleration : accelerations) {
				Vector acc = acceleration.clone();
				acc.scal( BigDecimal.valueOf(delta / 1000000000f) );
				pobject.applyForce( acc );
			}
		}
	}

	private void collide(PhisicsObject A, PhisicsObject B) {
		BigDecimal mA  = A.getMass();
		BigDecimal mB  = B.getMass();
		Vector     VAi = A.getVel();
		Vector     VBi = B.getVel();
		Vector     aux;

		//        mA - mB           2mB
		//  VAf = ------- VAi  +  ------- VBi
		//        mA + mB         mA + mB
		Vector VAf = VAi.clone();
		aux = VBi.clone();
		VAf.scal( mA.subtract(mB).divide( mA.add(mB), MathContext.DECIMAL32 ) );
		aux.scal( mB.add(mB).divide( mA.add(mB), MathContext.DECIMAL32 ) );
		VAf.add(aux);

		//          2mA           mA - mB
		//  VBf = ------- VAi  +  ------- VBi
		//        mA + mB         mA + mB
		Vector VBf = VAi.clone();
		aux = VBi.clone();
		VBf.scal( mA.add(mA).divide( mA.add(mB), MathContext.DECIMAL32 ) );
		aux.scal( mA.subtract(mB).divide( mA.add(mB), MathContext.DECIMAL32) );
		VBf.add(aux);

		A.setVel(VAf);
		B.setVel(VBf);
	}

	private void collides(long delta) {
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
						collide(pobject1, pobject2);
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

	public void add(Vector acceleration) {
		accelerations.add(acceleration);
	}

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

	/*----------------------------- Getters y Setters ------------------------*/
	public static PhisicsEngine getInstance() {
		if (instance == null) instance = new PhisicsEngine();

		return instance;
	}

}
