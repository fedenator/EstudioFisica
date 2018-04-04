package org.fpalacios.engine.gobjects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.Color;

import java.math.BigDecimal;
import java.math.MathContext;

import org.fpalacios.engine.Engine.Key;

import org.fpalacios.flibs.util.Vector;

public class SimpleObject implements GameObject, PhisicsObject, GraphicsObject {

    protected BigDecimal x, y;
    protected BigDecimal mass;
    protected Polygon    shape;
    protected Color      color;
    protected int        layer;
    protected int[][]    vertices;
    protected Vector     vel = new Vector(BigDecimal.ZERO, BigDecimal.ZERO);

    public SimpleObject(BigDecimal x, BigDecimal y, int vertices[][], BigDecimal mass, Color color, int layer) {
        this.color = color;
        this.mass  = mass;
        this.x     = x;
        this.y     = y;

        this.vertices = vertices;

        shape = new Polygon();
		for (int point[] : vertices)
			shape.addPoint((x.toBigInteger().intValue()+point[0]), (y.toBigInteger().intValue()+point[1]));
    }

    public void applyForce(Vector v) {
        //Segunda ley de newton, la aceleracion(cambio en la velocidad)
		//es directamente proporcional a la fuerza e inversamente proporcional a la masa del objeto

		v = v.clone();
		v.scal( BigDecimal.ONE.divide( mass, MathContext.DECIMAL32 ) );
		vel.add(v);
    }

    public BigDecimal[] getCenterOfMass() {
        BigDecimal res[] = {x, y};
		return res;
    }

    public int getLayer() {
        return layer;
    }

    public BigDecimal getMass() {
        return mass;
    }

    public Shape getShape() {
        return shape;
    }

    public void render(Graphics g) {
        g.setColor(color);
        ((Graphics2D) g).fillPolygon(shape);
    }

    public void update(long delta) {
		x = x.add( vel.x.multiply( BigDecimal.valueOf(delta/1000000000f) ) );
		y = y.add( vel.y.multiply( BigDecimal.valueOf(delta/1000000000f) ) );

		shape = new Polygon();
		for (int point[] : vertices)
			shape.addPoint((x.toBigInteger().intValue()+point[0]), (y.toBigInteger().intValue()+point[1]));
	}

    public void update(int delta) {}

    public void onCollide(PhisicsObject obj) {}
    public void pollinput(Key e) {}

    public void setLayer(int layer) {
        this.layer = layer;
    }


}
