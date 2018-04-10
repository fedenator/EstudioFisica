package org.fpalacios.engine.gobjects;

import java.math.BigDecimal;

import org.fpalacios.flibs.util.Vector;
import org.fpalacios.flibs.geo.BigPolygon;


public interface PhisicsObject {
	public BigPolygon getShape();
	public void       applyForce(Vector v);
	public BigDecimal getMass();
	public void       setVel(Vector v);
	public Vector     getVel();
	public Vector     getCenterOfMass();
	public void       onCollide(PhisicsObject obj);
	//Indica si el objecto afecta o es afectado por la gravedad
	public boolean    isGravitational();
}
