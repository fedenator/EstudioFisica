package org.fpalacios.engine.gobjects;

import java.awt.Graphics;
import java.util.ArrayList;

import org.fpalacios.engine.Engine;
import org.fpalacios.engine.Engine.Key;
import org.fpalacios.engine.abilities.Ability;

public abstract class Mob implements GraphicsObject, GameObject, PhisicsObject {

	protected final static Engine ENGINE = Engine.getInstance();

	private ArrayList<Ability> abilities = new ArrayList<>();
	private int layer = 1;

	public void addAbilitie(Ability ability) {
		abilities.add(ability);
	}

	public void update(long delta) {
		for (Ability abilitie : abilities)
			abilitie.update(delta);
	}

	public void render(Graphics g) {}
	public void update(int delta) {}
	public void pollinput(Key e) {}

	public void setLayer(int layer) {
		this.layer = layer;
	}
	public int getLayer() {
		return layer;
	}
}
