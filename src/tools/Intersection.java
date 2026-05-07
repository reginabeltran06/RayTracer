package tools;

import objects.*;

public class Intersection {

    private final double t;
    private final Vector3D point;
    private final Vector3D normal;
    private final Object3D object;

    public Intersection(double t, Vector3D point, Vector3D normal, Object3D object) {
        this.t = t;
        this.point = point;
        this.normal = normal.normalize();
        this.object = object;
    }

    public double getT() {
        return t;
    }

    public Vector3D getPoint() {
        return point;
    }

    public Vector3D getNormal() {
        return normal;
    }

    public Object3D getObject() {
        return object;
    }

    public boolean isValid() {
        return t > 0;
    }

}