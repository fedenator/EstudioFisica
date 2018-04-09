package org.fpalacios.phisicsimulator.abilities;

import org.fpalacios.phisicsimulator.gobjects.Tank;
// import org.fpalacios.phisicsimulator.gobjects.Bullet;
import org.fpalacios.phisicsimulator.gobjects.Bullet.Direction;

// import org.fpalacios.engine.Engine;

import org.fpalacios.engine.abilities.Casteable;

public class SimpleShoot extends Casteable {

	// private Tank tank;

	public SimpleShoot(Tank tank) {
		super(.25f);
		// this.tank = tank;
	}

	public void fire(Direction dir) {
		// if (!canCast) return;
		//
		// int x = 0;
		// int y = 0;
		// int tankx = tank.getShape().getBounds().x;
		// int tanky = tank.getShape().getBounds().y;
		// int tankw = tank.getShape().getBounds().width;
		// int tankh = tank.getShape().getBounds().height;
		//
		// switch (dir) {
		// 	case UP: {
		// 		x = tankx + (tankw/2) - (Bullet.WIDTH/2);
		// 		y = tanky - Bullet.HEIGHT + 1;
		// 		break;
		// 	}
		// 	case DOWN: {
		// 		x = tankx + (tankw/2) - (Bullet.WIDTH/2);
		// 		y = tanky + tankh + 1;
		// 		break;
		// 	}
		// 	case LEFT: {
		// 		x = tankx - Bullet.WIDTH + 1;
		// 		y = tanky + (tankh/2) - (Bullet.HEIGHT/2);
		// 		break;
		// 	}
		// 	case RIGHT: {
		// 		x = tankx + tankw + 1;
		// 		y = tanky + (tankh/2) - (Bullet.HEIGHT/2);
		// 		break;
		// 	}
		// }
		// Engine.getInstance().addObject( new Bullet(dir, x, y) );
		// cast();
	}

}
