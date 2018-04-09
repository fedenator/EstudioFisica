package org.fpalacios.flibs.geo;

import org.fpalacios.flibs.util.ArrayIterator;
import org.fpalacios.flibs.util.Vector;

import java.math.BigDecimal;

import java.util.Iterator;
import java.util.ArrayList;

public class BigPolygon implements Iterable<Vector> {
    public Vector[] vertices;
    public ArrayList<Vector> edges = new ArrayList<>();

    public BigPolygon(Vector... vertices) {
        this.vertices = vertices;
        createEdges();
    }

    public Iterator<Vector> iterator() {
       return new ArrayIterator<Vector>(vertices);
    }

    public void createEdges() {
        edges.clear();

        for (int i = 0; i < vertices.length; i++) {
            Vector p1 = vertices[i];
            Vector p2 = (i+1 < vertices.length)? vertices[i+1] : vertices[0];
            Vector edge = p2.clone();
            edge.substract(p1);
            edges.add(edge);
        }
    }

    public void translate(Vector v) {
        for (Vector vertex : vertices)
            vertex.add(v);

        createEdges();
    }

    /**
     * @return array de dos pociciones donde el primer indice es el minimo de la proyeccion y el segundo el maximo
     */
    public BigDecimal[] projectOn(Vector axys) {
        BigDecimal dotProduct = vertices[0].dotProduct(axys);
        BigDecimal min = dotProduct;
        BigDecimal max = dotProduct;

        for (Vector vertex : vertices) {
            dotProduct = vertex.dotProduct(axys);
            if      (dotProduct.compareTo(min) < 0) min = dotProduct;
            else if (dotProduct.compareTo(max) > 0) max = dotProduct;
        }

        return new BigDecimal[]{min, max};
    }

    public static boolean polygonsOverlapsOnAxys(BigPolygon a, BigPolygon b, Vector axys) {
        BigDecimal[] projectionA = a.projectOn(axys);
        BigDecimal[] projectionB = b.projectOn(axys);
        BigDecimal minA = projectionA[0], maxA = projectionA[1];
        BigDecimal minB = projectionB[0], maxB = projectionB[1];

        if (minA.compareTo(minB) < 0) {
            return maxA.compareTo(minB) >= 0;
        } else {
            return maxB.compareTo(minA) >= 0;
        }
    }

    public static Boolean areColliding(BigPolygon a, BigPolygon b) {
        Vector axys;
        for (Vector edge : a.edges) {
            axys = edge.getPerpendicularVector();
            axys.normalize();
            if ( !BigPolygon.polygonsOverlapsOnAxys(a, b, axys) ) return false;
        }

        for (Vector edge : b.edges) {
            axys = edge.getPerpendicularVector();
            axys.normalize();
            if ( !BigPolygon.polygonsOverlapsOnAxys(a, b, axys) ) return false;
        }

        return true;
    }
}
