package org.fpalacios.phisicsimulator.gobjects;


import java.math.BigDecimal;

import java.awt.Rectangle;
import java.awt.Color;

import org.fpalacios.flibs.util.Vector;
import org.fpalacios.engine.gobjects.ImmovableObject;


public class Wall extends ImmovableObject {

	public Rectangle rect;

	public Wall(BigDecimal x, BigDecimal y, BigDecimal w, BigDecimal h) {
		super( Color.GREEN, 1, new Vector(x, y), new Vector(x.add(w), y), new Vector(x.add(w), y.add(h)), new Vector(x, y.add(h)) );
	}

}
