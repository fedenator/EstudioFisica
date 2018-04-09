
package org.fpalacios.engine.gobjects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Polygon;

import java.math.BigDecimal;
import java.math.MathContext;

import org.fpalacios.engine.Engine.Key;

import org.fpalacios.flibs.util.Vector;
import org.fpalacios.flibs.geo.BigPolygon;

public class RealObject implements GameObject, PhisicsObject, GraphicsObject {

    /*------------------------------ Propiedades -----------------------------*/
    protected BigDecimal mass;
    protected BigPolygon shape;
    protected Color      color;
    protected int        layer;
    protected Vector     vel = new Vector(BigDecimal.ZERO, BigDecimal.ZERO);

    /*---------------------------- Constructores -----------------------------*/
    public RealObject(BigDecimal mass, Color color, int layer, Vector... vertices) {
        this.color = color;
        this.mass  = mass;

        shape = new BigPolygon(vertices);
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
        Polygon p = new Polygon();
        for (Vector vertex : shape)
            p.addPoint(vertex.x.intValue(), vertex.y.intValue());
        ((Graphics2D) g).fillPolygon(p);
    }

    public void update(int delta) {}

    public void update(long delta) {
        BigDecimal bigDelta = BigDecimal.valueOf(delta/1000000000.0);
		BigDecimal x = vel.x.multiply (bigDelta, MathContext.DECIMAL32);
		BigDecimal y = vel.y.multiply (bigDelta, MathContext.DECIMAL32);

		shape.translate( new Vector(x, y) );
	}

    public String toString() {
        return "X:"+shape.vertices[0].x+"||Y:"+shape.vertices[0].y;
    }

    public void onCollide(PhisicsObject obj) {
        System.out.println("Collide [" +getClass().getSimpleName() + " || "+ obj.getClass().getSimpleName() + "]" );
    }
    public void pollinput(Key e) {}

    /*--------------------------- Getters y Setters --------------------------*/
    public BigDecimal[] getCenterOfMass() {
        for (Vector vertex : shape) System.out.println(vertex);
        BigDecimal res[] = { shape.vertices[0].x, shape.vertices[0].y };
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

    public BigPolygon getShape() {
        return shape;
    }

    public boolean isGravitational() {
        return true;
    }

}
