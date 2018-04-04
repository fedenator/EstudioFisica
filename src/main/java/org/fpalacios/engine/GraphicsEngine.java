package org.fpalacios.engine;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.function.Consumer;

import org.fpalacios.engine.gobjects.GraphicsObject;
import org.fpalacios.flibs.util.FlinkedList;

public class GraphicsEngine {
	private FlinkedList<GraphicsObject> gobjects = new FlinkedList<>();

	private Consumer<BufferedImage> paintFunction;

	private BufferedImage buffer;

	public GraphicsEngine(int resW, int resH, Consumer<BufferedImage> paintFuction) {
		this.paintFunction = paintFuction;
		buffer = new BufferedImage(resW, resH, BufferedImage.TYPE_INT_ARGB);
	}

	public void render() {
		Graphics g = buffer.getGraphics();
		RenderingHints rh = new RenderingHints(
	             RenderingHints.KEY_TEXT_ANTIALIASING,
	             RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		 rh.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		((Graphics2D)g).setRenderingHints(rh);
		for(GraphicsObject gobject : gobjects) gobject.render(g);
		paintFunction.accept(buffer);
	}

	public void add(GraphicsObject graphicsObject) {
		gobjects.add(graphicsObject);
	}
}
