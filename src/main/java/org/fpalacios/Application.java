package org.fpalacios;

import org.fpalacios.engine.Engine;
import org.fpalacios.engine.PhisicsEngine;

import org.fpalacios.flibs.util.Vector;

import org.fpalacios.phisicsimulator.gobjects.Background;
import org.fpalacios.phisicsimulator.gobjects.Tank;
import org.fpalacios.phisicsimulator.gobjects.Wall;

import org.fpalacios.phisicsimulator.Window;

import java.awt.Color;

import java.math.BigDecimal;


public class Application {

    public static void main(String[] args) {
        new Window();

        Engine engine = Engine.getInstance();

        PhisicsEngine.getInstance().add( PhisicsEngine.EARTH_GRAVITY );

        Tank tank  = new Tank(BigDecimal.valueOf(300), BigDecimal.valueOf(300), Color.red);
        Wall wall = new Wall(BigDecimal.ZERO, BigDecimal.valueOf(500), BigDecimal.valueOf(1500), BigDecimal.valueOf(10) );
        // Tank tankW = new Tank(BigDecimal.valueOf(500), BigDecimal.valueOf(500), Color.blue);

        engine.addObject( new Background() );
        engine.addObject(tank);
        engine.addObject(wall);
        // engine.addObject(tankW);

        // tank.setMass( BigDecimal.valueOf(2) );

        tank.setVel( new Vector(BigDecimal.valueOf(100), BigDecimal.valueOf(-100)) );
        // tankW.setVel(new Vector(BigDecimal.valueOf(100), BigDecimal.valueOf(0)));

        engine.start();
    }
}
