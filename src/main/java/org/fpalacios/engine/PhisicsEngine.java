package org.fpalacios.engine;

import java.util.ArrayList;

import java.math.BigDecimal;

import org.fpalacios.engine.gobjects.PhisicsObject;

import org.fpalacios.flibs.util.FlinkedList;
import org.fpalacios.flibs.util.Vector;
import org.fpalacios.flibs.geo.BigPolygon;

import java.math.MathContext;

public class PhisicsEngine {

	private static PhisicsEngine instance;

	/*----------------------------- Constantes -------------------------------*/
	public static final BigDecimal G          = BigDecimal.valueOf(6.674).multiply( BigDecimal.TEN.pow(-11, MathContext.DECIMAL32) );
	public static final BigDecimal EARTH_MASS = BigDecimal.valueOf(5.9722).multiply( BigDecimal.TEN.pow(24, MathContext.DECIMAL32) );

	public static final Vector EARTH_GRAVITY = new Vector(BigDecimal.ZERO, BigDecimal.valueOf(98) );

	/*----------------------------- Propiedades ------------------------------*/
	private FlinkedList<PhisicsObject> pobjects = new FlinkedList<>();

	//Aceleraciones constantes que no se calculan, por ejemplo para no tener que
	//la gravedad de la tierra si toda la simulacion sucede en la tierra
	private ArrayList<Vector> accelerations = new ArrayList<>();


	/*---------------------------- Constructores -----------------------------*/
	private PhisicsEngine() {}

		/*------------------------------- Funciones ------------------------------*/
		public void update(long delta) {
			// gravity(delta);
			accelerate(delta);
			collides(delta);
		}

		private void gravity(long delta) {
			int i1 = 0;
			for (PhisicsObject pobject1 : pobjects) {
				int i2 = 0;
				if ( pobject1.isGravitational() ) for (PhisicsObject pobject2 : pobjects) {
					if (pobject2.isGravitational() && i2 > i1) {
						//       m1*m2   ^
						//F = -G -----  u21
						//        d^2
						//TODO: Rearmar la formula usando normalize de Vector
						BigDecimal deltaX = pobject1.getCenterOfMass().x.subtract( pobject2.getCenterOfMass().x );
						BigDecimal deltaY = pobject1.getCenterOfMass().y.subtract( pobject2.getCenterOfMass().y );
						BigDecimal distanceSquare = deltaX.pow(2).add( deltaY.pow(2) );
						BigDecimal Fg = G.multiply( pobject1.getMass().multiply( pobject2.getMass(), MathContext.DECIMAL32 ).divide(distanceSquare, MathContext.DECIMAL32), MathContext.DECIMAL32 );
						Vector v = new Vector(deltaX, deltaY);
						v.scal( BigDecimal.ONE.divide(Vector.sqrt(distanceSquare, 32), MathContext.DECIMAL32) );
						v.scal( Fg.multiply( new BigDecimal(delta), MathContext.DECIMAL32 ) );
						pobject2.applyForce(v);
						v.scal( Fg.multiply(new BigDecimal(-1), MathContext.DECIMAL32) );
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
			Vector     aux;

			if (mA == null || mB == null) {
				if (mA != null) {
					Vector VAf = A.getVel().clone();
					VAf.scal( BigDecimal.valueOf(-1) );
					A.setVel(VAf);
				} else {
					Vector VBf = B.getVel().clone();
					VBf.scal( BigDecimal.valueOf(-1) );
					B.setVel(VBf);
				}
				return;
			}

			//        mA - mB           2mB
			//  VAf = ------- VAi  +  ------- VBi
			//        mA + mB         mA + mB
			Vector VAf = A.getVel().clone();

			Vector relativeDistance = A.getCenterOfMass().clone();
			relativeDistance.substract( B.getCenterOfMass() );

			Vector relativeVelocity = A.getVel().clone();
			relativeVelocity.substract( B.getVel() );

			aux = relativeDistance.clone();
			aux.scal( relativeVelocity.dotProduct(relativeDistance).divide( relativeDistance.module().pow(2, MathContext.DECIMAL32), MathContext.DECIMAL32) );
			aux.scal( mB.add(mB).divide( mA.add(mB), MathContext.DECIMAL32 ) );
			VAf.substract(aux);

			//          2mA           mB - mA
			//  VBf = ------- VAi  +  ------- VBi
			//        mA + mB         mA + mB
			Vector VBf = B.getVel().clone();

			relativeDistance = B.getCenterOfMass().clone();
			relativeDistance.substract( A.getCenterOfMass() );

			relativeVelocity = B.getVel().clone();
			relativeVelocity.substract( A.getVel() );

			aux = relativeDistance.clone();
			aux.scal( relativeVelocity.dotProduct(relativeDistance).divide( relativeDistance.module().pow(2, MathContext.DECIMAL32), MathContext.DECIMAL32 ) );
			aux.scal( mA.add(mA).divide(mA.add(mB), MathContext.DECIMAL32) );
			VBf.substract(aux);

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
						if ( BigPolygon.areColliding(pobject1.getShape(), pobject2.getShape()) ) {
							collide(pobject1, pobject2 );
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

		/*----------------------------- Getters y Setters ------------------------*/
		public static PhisicsEngine getInstance() {
			if (instance == null) instance = new PhisicsEngine();

			return instance;
		}

	}
