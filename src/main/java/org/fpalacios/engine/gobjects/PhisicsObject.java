package org.fpalacios.engine.gobjects;

import java.awt.Shape;

import java.math.BigDecimal;

import org.fpalacios.flibs.util.Vector;


public interface PhisicsObject {
	public Shape        getShape();
	public void         applyForce(Vector v);
	public BigDecimal   getMass();
	public void         setVel(Vector v);
	public Vector       getVel();
	public BigDecimal[] getCenterOfMass();
	public void         onCollide(PhisicsObject obj);
}
