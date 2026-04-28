import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        Camera camera = new Camera(
                new Vector3D(0, 0, 0),
                1200,
                1200,
                90,
                5,
                30
        );

        Scene scene = new Scene();

        scene.addObject(new Sphere(
                new Vector3D(-2, 0, -8),
                1.5,
                new Vector3D(1, 0, 0)
        ));

        scene.addObject(new Sphere(
                new Vector3D(-12, 0, -30),      //not rendered because it's out of fov
                1.5,
                new Vector3D(0, 1, 0)
        ));


        scene.addObject(new Triangle(
                new Vector3D(1, -1, -7),
                new Vector3D(4, -1, -7),
                new Vector3D(2.5, 2, -7),
                new Vector3D(0, 0, 1)
        ));


        Raytracer rt = new Raytracer(camera, scene);
        rt.setBackgroundColor(new Vector3D(1, 1, 1));

        rt.renderToFile("outputRaytracerV02.png");
    }
}