package objects;

import tools.*;

public class Triangle extends Object3D {

    private final Vector3D v0;
    private final Vector3D v1;
    private final Vector3D v2;

    private final Vector3D n0;
    private final Vector3D n1;
    private final Vector3D n2;


    private static final double epsilon = 0.00001;

    public Triangle(Vector3D v0, Vector3D v1, Vector3D v2, Vector3D n0, Vector3D n1, Vector3D n2, Vector3D color) {
        super(color);
        this.v0 = v0;
        this.v1 = v1;
        this.v2 = v2;

        this.n0 = n0.normalize();
        this.n1 = n1.normalize();
        this.n2 = n2.normalize();
    }

    public Triangle(Vector3D v0, Vector3D v1, Vector3D v2, Vector3D color) {
        super(color);
        this.v0 = v0;
        this.v1 = v1;
        this.v2 = v2;

        Vector3D edge1 = v1.subtract(v0);
        Vector3D edge2 = v2.subtract(v0);

        Vector3D normal = edge1.cross(edge2).normalize();

        this.n0 = normal;
        this.n1 = normal;
        this.n2 = normal;
    }

    @Override
    public Intersection intersect(Ray ray) {

        Vector3D edge1 = v1.subtract(v0);
        Vector3D edge2 = v2.subtract(v0);

        Vector3D h = ray.getDirection().cross(edge2);
        double a = edge1.dot(h);

        if (Math.abs(a) < epsilon)
            return null;

        double f = 1.0 / a;

        Vector3D s = ray.getOrigin().subtract(v0);
        double u = f * s.dot(h);

        if (u < 0.0 || u > 1.0)
            return null;

        Vector3D q = s.cross(edge1);
        double v = f * ray.getDirection().dot(q);

        if (v < 0.0 || u + v > 1.0)
            return null;

        double t = f * edge2.dot(q);

        if (t <= epsilon)
            return null;

        Vector3D point = ray.at(t);

        //phong
        double w = 1.0 - u - v;

        Vector3D normal = n0.scale(w).add(n1.scale(u)).add(n2.scale(v)).normalize();

//        if (normal.dot(ray.getDirection()) > 0) {
//            normal = normal.scale(-1);
//        }

        return new Intersection(t, point, normal, this);
    }
}