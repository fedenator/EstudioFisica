package org.fpalacios.phisicsimulator.gobjects;

import java.awt.Color;

import java.math.BigDecimal;

import org.fpalacios.phisicsimulator.gobjects.Bullet.Direction;
import org.fpalacios.engine.Engine.Key;
import org.fpalacios.flibs.util.Vector;

public class Player extends Tank {

	public final static double VEL = 0.25;

	public boolean upPressed    = false;
	public boolean downPressed  = false;
	public boolean rightPressed = false;
	public boolean leftPressed  = false;

	public boolean fireUpPressed     = false;
	public boolean fireDownPressed   = false;
	public boolean fireRightPressed  = false;
	public boolean fireLeftPressed   = false;


	public Player(BigDecimal x, BigDecimal y, Color color) {
		super(x, y, color);
	}

	public void pollinput(Key e) {
		char key = e.keyCode;
		if      (key == 'w') upPressed    = e.pressed;
		else if (key == 's') downPressed  = e.pressed;
		else if (key == 'd') rightPressed = e.pressed;
		else if (key == 'a') leftPressed  = e.pressed;

		else if (key == 'i') fireUpPressed    = e.pressed;
		else if (key == 'k') fireDownPressed  = e.pressed;
		else if (key == 'l') fireRightPressed = e.pressed;
		else if (key == 'j') fireLeftPressed  = e.pressed;
	}

	public void update(int delta) {
		super.update(delta);
		double dist = VEL * delta;
		Vector vector = new Vector();
		if (rightPressed) vector.x = BigDecimal.valueOf(dist);
		if (leftPressed)  vector.x = BigDecimal.valueOf(-dist);
		if (upPressed)    vector.y = BigDecimal.valueOf(-dist);
		if (downPressed)  vector.y = BigDecimal.valueOf(dist);
		applyForce(vector);

		if (fireUpPressed)    simpleShot.fire(Direction.UP);
		if (fireDownPressed)  simpleShot.fire(Direction.DOWN);
		if (fireRightPressed) simpleShot.fire(Direction.RIGHT);
		if (fireLeftPressed)  simpleShot.fire(Direction.LEFT);
	}
}
