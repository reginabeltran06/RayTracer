package tools;
import objects.*;

public class Ray {

    private final Vector3D origin;     // O
    private final Vector3D direction;  // D (normalized)


    public Ray(Vector3D origin, Vector3D direction) {
        this.origin    = origin;
        this.direction = direction.normalize();
    }

    public Vector3D at(double t) {
        return origin.add(direction.scale(t));
    }

    public Vector3D getOrigin() {
        return origin;
    }

    public Vector3D getDirection() {
        return direction;
    }

}