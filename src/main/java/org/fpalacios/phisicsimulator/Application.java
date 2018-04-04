package org.fpalacios.phisicsimulator;

import org.fpalacios.engine.Engine;

import org.fpalacios.phisicsimulator.gobjects.Background;
import org.fpalacios.phisicsimulator.gobjects.Player;

import java.awt.Color;

import java.math.BigDecimal;
import java.math.MathContext;

public class Application {

    public static void main(String[] args) {
        new Window();

        Engine engine = Engine.getInstance();

        engine.addObject( new Background() );
        engine.addObject( new Player(BigDecimal.ZERO, new BigDecimal(289, MathContext.DECIMAL32), Color.blue) );
        engine.addObject( new Player(BigDecimal.ZERO, new BigDecimal(500, MathContext.DECIMAL32), Color.RED ) );

        engine.start();
    }
}
