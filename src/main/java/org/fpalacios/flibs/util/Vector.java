package org.fpalacios.flibs.util;

import java.math.BigDecimal;
import java.math.MathContext;

public class Vector {
	public BigDecimal x = BigDecimal.ZERO, y = BigDecimal.ZERO;

	public Vector() {}
	public Vector(BigDecimal x, BigDecimal y) {
		this.x = x;
		this.y = y;
	}

	public void add(Vector v) {
		x = x.add(v.x);
		y = y.add(v.y);
	}

	public void scal(BigDecimal scalar) {
		x = x.multiply(scalar, MathContext.DECIMAL32);
		y = y.multiply(scalar, MathContext.DECIMAL32);
	}

	public Vector clone() {
		return new Vector(x, y);
	}

	public String toString() {
		return "Vect[X:"+x.toPlainString()+"||Y:"+y.toPlainString()+"]";
	}
}
