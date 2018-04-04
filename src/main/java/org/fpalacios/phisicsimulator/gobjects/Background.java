package org.fpalacios.phisicsimulator.gobjects;

import java.awt.Color;
import java.awt.Graphics;

import org.fpalacios.engine.gobjects.GraphicsObject;

import org.fpalacios.engine.Engine;

public class Background implements GraphicsObject {

	private final Engine engine = Engine.getInstance();
	private int layer = 0;

	public void render(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, engine.width, engine.height);
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}
	public int getLayer() {
		return layer;
	}
}
