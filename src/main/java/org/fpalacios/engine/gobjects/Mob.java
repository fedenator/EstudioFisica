package org.fpalacios.engine.gobjects;

import java.awt.Color;

import java.util.ArrayList;

import java.math.BigDecimal;

import org.fpalacios.engine.Engine;
import org.fpalacios.engine.abilities.Ability;

import org.fpalacios.flibs.util.Vector;

public abstract class Mob extends RealObject {

	protected final static Engine ENGINE = Engine.getInstance();

	private ArrayList<Ability> abilities = new ArrayList<>();

	public Mob(BigDecimal mass, Color color, int layer, Vector... vertices) {
		super(mass, color, layer, vertices);
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
