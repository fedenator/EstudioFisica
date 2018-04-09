package org.fpalacios.engine.gobjects;

import org.fpalacios.flibs.util.Vector;

import java.awt.Color;

public class ImmovableObject extends RealObject {

    public ImmovableObject (Color color, int layer, Vector... vertices) {
        super(null, color, layer, vertices);
    }

    public void update(int delta) {
        super.update(delta);
    }

    public boolean isGravitational() {
        return false;
    }

    public void applyForce (Vector v){}
}
