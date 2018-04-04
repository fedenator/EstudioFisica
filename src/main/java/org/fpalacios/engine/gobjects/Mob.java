package org.fpalacios.engine.gobjects;

import java.awt.Color;

import java.util.ArrayList;

import java.math.BigDecimal;

import org.fpalacios.engine.Engine;
import org.fpalacios.engine.abilities.Ability;

public abstract class Mob extends SimpleObject {

	protected final static Engine ENGINE = Engine.getInstance();

	private ArrayList<Ability> abilities = new ArrayList<>();

	public Mob(BigDecimal x, BigDecimal y, int[][] vertices, BigDecimal mass, Color color, int layer) {
		super(x, y, vertices, mass, color, layer);
	}

	public void addAbilitie(Ability ability) {
		abilities.add(ability);
	}

	public void update(long delta) {
		super.update(delta);
		for (Ability abilitie : abilities)
			abilitie.update(delta);
	}
}
