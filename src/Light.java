public class Light {

    private Vector3D direction;
    private Vector3D color;
    private double intensity;

    public Light(Vector3D direction, Vector3D color, double intensity) {
        this.direction = direction.normalize();
        this.color = color;
        this.intensity = intensity;
    }

    public Vector3D getDirection() {
        return direction;
    }

    public Vector3D getColor() {
        return color;
    }

    public double getIntensity() {
        return intensity;
    }
}