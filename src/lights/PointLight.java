package lights;

import tools.Vector3D;

public class PointLight extends Light {

    private Vector3D position;

    public PointLight(Vector3D position, Vector3D color, double intensity) {

        super(color, intensity);
        this.position = position;
    }

    @Override
    public Vector3D getDirection(Vector3D point) {
        return position.subtract(point).normalize();
    }
}
