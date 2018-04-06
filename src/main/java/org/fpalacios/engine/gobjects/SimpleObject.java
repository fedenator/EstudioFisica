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

    /*------------------------------ Propiedades -----------------------------*/
    protected BigDecimal mass;
    protected Polygon    shape;
    protected Color      color;
    protected int        layer;
    protected Vector     vel = new Vector(BigDecimal.ZERO, BigDecimal.ZERO);

    /*---------------------------- Constructores -----------------------------*/
    public SimpleObject(BigDecimal x, BigDecimal y, int vertices[][], BigDecimal mass, Color color, int layer) {
        this.color = color;
        this.mass  = mass;

        shape = new Polygon();
		for (int point[] : vertices)
			shape.addPoint(point[0], point[1]);

        shape.translate( x.intValue(), y.intValue() );
    }

    /*----------------------------- Funciones --------------------------------*/
    //Segunda ley de newton, la aceleracion(cambio en la velocidad)
    //es directamente proporcional a la fuerza e inversamente proporcional a la masa del objeto
    public void applyForce(Vector v) {
		v = v.clone();
		v.scal( BigDecimal.ONE.divide (mass, MathContext.DECIMAL32) );
		vel.add(v);
    }

    //Aplicar una aceleracion directamente
    public void addAcceleration(Vector acceleration) {
        vel.add(acceleration);
    }

    public void render(Graphics g) {
        g.setColor(color);
        ((Graphics2D) g).fillPolygon(shape);
    }

    public void update(int delta) {}

    public void update(long delta) {
        BigDecimal bigDelta = BigDecimal.valueOf(delta/1000000000.0);
		BigDecimal x = vel.x.multiply (bigDelta, MathContext.DECIMAL32);
		BigDecimal y = vel.y.multiply (bigDelta, MathContext.DECIMAL32);

		shape.translate( x.intValue(), y.intValue() );
	}

    public void onCollide(PhisicsObject obj) {}
    public void pollinput(Key e) {}


    /*--------------------------- Getters y Setters --------------------------*/
    public BigDecimal[] getCenterOfMass() {
        BigDecimal res[] = { BigDecimal.valueOf(shape.xpoints[0]), BigDecimal.valueOf(shape.ypoints[0]) };
		return res;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }
    public int getLayer() {
        return layer;
    }

    public void setVel(Vector v) {
        this.vel = v;
    }

    public Vector getVel() {
        return vel;
    }

    public void setMass(BigDecimal mass) {
        this.mass = mass;
    }

    public BigDecimal getMass() {
        return mass;
    }

    public Shape getShape() {
        return shape;
    }

}
