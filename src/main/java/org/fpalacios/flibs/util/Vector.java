package org.fpalacios.flibs.util;

import java.math.BigDecimal;

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
		x = x.multiply(scalar);
		y = y.multiply(scalar);
	}

	public Vector clone() {
		return new Vector(x, y);
	}
}
