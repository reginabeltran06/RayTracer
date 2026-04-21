public class Vector3D {

    public double x, y, z;

    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public Vector3D add(Vector3D v) {
        return new Vector3D(x + v.x, y + v.y, z + v.z);
    }

    public Vector3D subtract(Vector3D v) {
        return new Vector3D(x - v.x, y - v.y, z - v.z);
    }

    public Vector3D scale(double t) {
        return new Vector3D(x * t, y * t, z * t);
    }

    public double dot(Vector3D v) {
        return x * v.x + y * v.y + z * v.z;
    }

    public Vector3D cross(Vector3D v) {
        return new Vector3D(
                y * v.z - z * v.y,
                z * v.x - x * v.z,
                x * v.y - y * v.x
        );
    }

    public double magnitude(){
        return Math.sqrt(dot(this));
    }

    public Vector3D normalize(){
        double mag = magnitude();
        return new Vector3D(x / mag, y / mag, z / mag);
    }

}