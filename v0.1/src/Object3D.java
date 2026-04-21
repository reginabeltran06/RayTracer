public abstract class Object3D {

    protected Vector3D color;

    public Object3D(Vector3D color) {
        this.color = color;
    }

    public abstract Intersection intersect(Ray ray);

    public Vector3D getColor() {
        return color;
    }

    public void setColor(Vector3D color) {
        this.color = color;
    }
}