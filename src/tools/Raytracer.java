package tools;

import lights.DirectionalLight;
import lights.Light;
import objects.Camera;
import scene.Scene;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Raytracer {

    private final Camera camera;
    private final Scene scene;

    private Vector3D backgroundColor = new Vector3D(0, 0, 0);


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


                Vector3D pixelColor;

                if (hit != null) {
                    Vector3D baseColor = hit.getObject().getColor();
                    Vector3D normal = hit.getNormal();

                    Vector3D finalColor = new Vector3D(0,0,0);

                    for (Light light: scene.getLights()) {

                        Vector3D lightDir = light.getDirection(hit.getPoint());

                        double ndotl = Math.max(0, normal.dot(lightDir));

                        Vector3D calculateColor = new Vector3D(
                                baseColor.x * light.getColor().x * light.getIntensity() * ndotl,
                                baseColor.y * light.getColor().y * light.getIntensity() * ndotl,
                                baseColor.z * light.getColor().z * light.getIntensity() * ndotl
                        );

                        finalColor = finalColor.add(calculateColor);
                    }

                    pixelColor = finalColor;
                }else{
                    pixelColor = backgroundColor;
                }

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
