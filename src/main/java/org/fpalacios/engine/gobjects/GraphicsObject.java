package org.fpalacios.engine.gobjects;

import java.awt.Graphics;

public interface GraphicsObject {
	public void render(Graphics g);
	public int  getLayer();
	public void setLayer(int layer);
}
