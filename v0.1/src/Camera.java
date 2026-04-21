public class Camera {

    private final Vector3D position;
    private final int width;
    private final int height;
    private final double fov;

    private final double aspectRatio;
    private final double halfWidthTan;

    public Camera(Vector3D position, int width, int height, double fov) {
        this.position = position;
        this.width = width;
        this.height = height;
        this.fov = fov;
        this.aspectRatio = (double) width / height;
        this.halfWidthTan = Math.tan(Math.toRadians(fov / 2.0));
    }

    public Ray generateRay(int px, int py) {
        double ndcX = (px + 0.5) / width;
        double ndcY = (py + 0.5) / height;

        double screenX = (2 * ndcX - 1) * aspectRatio * halfWidthTan;
        double screenY = -(2 * ndcY - 1) * halfWidthTan;

        Vector3D direction = new Vector3D(screenX, screenY, -1);
        return new Ray(position, direction);
    }

    public Vector3D getPosition() {
        return position;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public double getFov() {
        return fov;
    }

    public double getAspectRatio() {
        return aspectRatio;
    }

    public double getHalfWidthTan() {
        return halfWidthTan;
    }
}