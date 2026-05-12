package lights;

import tools.Vector3D;

public abstract class Light {

    private Vector3D color;
    private double intensity;

    public Light(Vector3D color, double intensity) {
        this.color = color;
        this.intensity = intensity;
    }

    public Vector3D getColor() {
        return color;
    }

    public double getIntensity() {
        return intensity;
    }

    public abstract Vector3D getDirection(Vector3D point);
}