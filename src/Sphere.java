public class Sphere extends Object3D {

    private Vector3D center;
    private double radius;

    public Sphere(Vector3D center, double radius, Vector3D color) {
        super(color);
        this.center = center;
        this.radius = radius;
    }

    @Override public Intersection intersect(Ray ray) {

        Vector3D O = ray.getOrigin();
        Vector3D D = ray.getDirection();

        Vector3D L = center.subtract(O);

        double tca = L.dot(D);

        if (tca < 0) return null; // object is behind the ray

        double d2 = L.dot(L) - tca * tca;
        double r2 = radius * radius;

        if (d2 > r2) return null; // No intersection

        double thc = Math.sqrt(r2 - d2);

        // t0 and t1
        double t0 = tca - thc;
        double t1 = tca + thc;

        // Choose closest positive t
        double t = (t0 > 0) ? t0 : t1;
        if (t <= 0) return null;

        Vector3D hitPoint = ray.at(t);
        Vector3D normal = hitPoint.subtract(center).normalize();

        return new Intersection(t, hitPoint, normal, this);
    }

    public Vector3D getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    public void setCenter(Vector3D center) {
        this.center = center;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

}