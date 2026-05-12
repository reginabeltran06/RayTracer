package lights;

import tools.*;

public class DirectionalLight extends Light{

    private Vector3D direction;

    public DirectionalLight(Vector3D direction, Vector3D color, double intensity) {
        super(color, intensity);
        this.direction = direction.normalize();

    }

    @Override
    public Vector3D getDirection(Vector3D point) {
        return direction.scale(-1).normalize();
    }

}
