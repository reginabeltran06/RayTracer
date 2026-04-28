import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Raytracer {

    private final Camera camera;
    private final Scene  scene;

    private Vector3D backgroundColor = new Vector3D(1, 1, 1);


    public Raytracer(Camera camera, Scene scene) {
        this.camera = camera;
        this.scene = scene;
    }

    public BufferedImage render() {
        int width = camera.getWidth();
        int height = camera.getHeight();

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                Ray ray = camera.generateRay(x, y);
                Intersection hit = scene.closestIntersection(ray, camera.getNearPlane(), camera.getFarPlane());

                Vector3D pixelColor = (hit != null) ? hit.getObject().getColor() : backgroundColor;

                image.setRGB(x, y, toRGB(pixelColor));
            }
        }
        return image;
    }

    public void renderToFile(String outputPath) throws IOException {
        BufferedImage image = render();
        ImageIO.write(image, "PNG", new File(outputPath));
        System.out.println("Rendered to: " + outputPath);
    }

    private int toRGB(Vector3D color) {
        int r = clamp((int)(color.x * 255), 0, 255);
        int g = clamp((int)(color.y * 255), 0, 255);
        int b = clamp((int)(color.z * 255), 0, 255);
        return new Color(r, g, b).getRGB();
    }

    private int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    public void setBackgroundColor(Vector3D c) {
        this.backgroundColor = c;
    }

    public Vector3D getBackgroundColor(){
        return backgroundColor;
    }

    public Camera getCamera() {
        return camera;
    }

    public Scene getScene() {
        return scene;
    }
}
