package org.fpalacios;

import org.fpalacios.engine.Engine;
import org.fpalacios.engine.PhisicsEngine;

import org.fpalacios.flibs.util.Vector;

import org.fpalacios.phisicsimulator.gobjects.Background;
import org.fpalacios.phisicsimulator.gobjects.Tank;

import org.fpalacios.phisicsimulator.Window;

import java.awt.Color;

import java.math.BigDecimal;

public class Application {

    public static void main(String[] args) {
        new Window();

        Engine engine = Engine.getInstance();

        PhisicsEngine.getInstance().add( PhisicsEngine.EARTH_GRAVITY );

        Tank tank1 = new Tank(BigDecimal.ZERO, BigDecimal.valueOf(289), Color.blue);
        Tank tank2 = new Tank(BigDecimal.ZERO, BigDecimal.valueOf(500), Color.green);
        tank2.setMass( BigDecimal.valueOf(1000) );

        engine.addObject( new Background() );
        engine.addObject(tank1);
        engine.addObject(tank2);

        tank1.applyForce( new Vector(BigDecimal.ZERO, BigDecimal.valueOf(100)) );
        tank2.setVel( new Vector(BigDecimal.ZERO, BigDecimal.valueOf(-100)) );

        engine.start();
    }
}
