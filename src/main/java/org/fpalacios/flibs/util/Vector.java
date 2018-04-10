package org.fpalacios.flibs.util;

import java.math.BigDecimal;
import java.math.MathContext;

public class Vector {

	private static final BigDecimal TWO = new BigDecimal(2);

	public BigDecimal x = BigDecimal.ZERO, y = BigDecimal.ZERO;

	public Vector() {}

	public Vector(BigDecimal x, BigDecimal y) {
		this.x = x;
		this.y = y;
	}

	public Vector(double x, double y) {
		this( BigDecimal.valueOf(x), BigDecimal.valueOf(y) );
	}

	public void add(Vector v) {
		x = x.add(v.x);
		y = y.add(v.y);
	}

	public void substract(Vector other) {
		x = x.subtract(other.x);
		y = y.subtract(other.y);
	}

	public BigDecimal dotProduct(Vector other) {
		return x.multiply(other.x, MathContext.DECIMAL32).add( y.multiply(other.y, MathContext.DECIMAL32) );
	}

	public BigDecimal module() {
		return sqrt(x.pow(2, MathContext.DECIMAL32).add( y.pow(2, MathContext.DECIMAL32) ), 32);
	}

	public void normalize() {
		divide( Vector.sqrt(x.pow(2).add( y.pow(2) ), 32) );
	}

	public void scal(BigDecimal scalar) {
		x = x.multiply(scalar, MathContext.DECIMAL32);
		y = y.multiply(scalar, MathContext.DECIMAL32);
	}

	public void divide(BigDecimal divisor) {
		scal( BigDecimal.ONE.divide(divisor, MathContext.DECIMAL32) );
	}

	public Vector getPerpendicularVector() {
		return new Vector(y.negate(), x);
	}

	public Vector clone() {
		return new Vector(x, y);
	}

	public String toString() {
		return "Vect[X:"+x.toPlainString()+"||Y:"+y.toPlainString()+"]";
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
}
