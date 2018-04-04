package org.fpalacios.phisicsimulator.gobjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;

import org.fpalacios.engine.Engine.Key;
import org.fpalacios.engine.gobjects.GameObject;
import org.fpalacios.engine.gobjects.GraphicsObject;
// import org.fpalacios.engine.gobjects.PhisicsObject;

public class Bullet implements GraphicsObject, GameObject/*, PhisicsObject*/ {

	public enum Direction { UP, DOWN, RIGHT, LEFT }

	private int layer = 2;

	public final static int WIDTH = 10, HEIGHT = 10;
	public final static double vel = 0.5;
	public final Direction dir;
	public final static Color color = Color.red;
	private Rectangle shape;

	public Bullet(Direction dir, int x, int y) {
		this.dir = dir;
		this.shape = new Rectangle(x, y, WIDTH, HEIGHT);
	}

	public void render(Graphics g) {
		g.setColor(color);
		g.fillRect(shape.x, shape.y, shape.width, shape.height);
	}

	public void update(int delta) {
		if (dir == Direction.UP)    shape.translate(0, (int)(-delta*vel));
		if (dir == Direction.DOWN)  shape.translate(0, (int)(delta*vel));
		if (dir == Direction.RIGHT) shape.translate((int)(delta*vel), 0);
		if (dir == Direction.LEFT)  shape.translate((int)(-delta*vel), 0);
	}

	public void update(long delta) {}
	public void pollinput(Key e) {}

	public void setLayer(int layer) {
		this.layer = layer;
	}
	public int getLayer() {
		return this.layer;
	}

	public Shape getShape() {
		return shape;
	}
}
