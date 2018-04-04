package org.fpalacios.phisicsimulator.gobjects;

import java.math.BigDecimal;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Color;

import org.fpalacios.engine.gobjects.GraphicsObject;
import org.fpalacios.engine.gobjects.PhisicsObject;
import org.fpalacios.flibs.util.Vector;

public class Wall implements GraphicsObject, PhisicsObject {

	public Rectangle rect;

	public Wall(int x, int y, int w, int h) {
		rect = new Rectangle(x, y, w, h);
	}

	public Shape getShape() {
		return rect;
	}

	public void applyForce(Vector v) {}

	public BigDecimal getMass() {
		return BigDecimal.ONE;
	}

	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(rect.x, rect.y, rect.width, rect.height);
	}

	public int getLayer() {
		return 1;
	}

	public void setLayer(int layer) {}

	public BigDecimal[] getCenterOfMass() {
		BigDecimal res[] = { new BigDecimal(rect.getX()), new BigDecimal(rect.getY())};
		return res;
	}

	@Override
	public void onCollide(PhisicsObject obj) {

	}


}
