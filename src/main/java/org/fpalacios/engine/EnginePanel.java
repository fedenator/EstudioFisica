package org.fpalacios.engine;

import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import java.awt.image.BufferedImage;

public class EnginePanel extends JPanel {

    private static final long serialVersionUID = 1L;
	private Engine engine;

	public BufferedImage image;

    public EnginePanel() {
        setPreferredSize( new Dimension(1200, 768) );

		engine = Engine.getInstance();
		engine.setup(1200, 768, (img) -> {
			this.image = img;
			this.paintImmediately(0, 0, getWidth(), getHeight());
		});

		this.addKeyListener(engine);
    }

    protected void paintComponent(Graphics g) {
		((Graphics2D)g).setRenderingHint(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
	}
}
