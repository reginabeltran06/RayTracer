public class Triangle extends Object3D {

    private final Vector3D v0;
    private final Vector3D v1;
    private final Vector3D v2;

    private static final double epsilon = 0.00001;

    public Triangle(Vector3D v0, Vector3D v1, Vector3D v2, Vector3D color) {
        super(color);
        this.v0 = v0;
        this.v1 = v1;
        this.v2 = v2;
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

        Vector3D normal = edge1.cross(edge2).normalize();

        if (normal.dot(ray.getDirection()) > 0) {
            normal = normal.scale(-1);
        }

        return new Intersection(t, point, normal, this);
    }
}