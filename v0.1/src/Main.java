import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        Camera camera = new Camera(
                new Vector3D(0, 0, 0),
                1200,
                1200,
                90
        );

        Scene scene = new Scene();

        scene.addObject(new Sphere(
                new Vector3D(.8, 1, -9),
                1,
                new Vector3D(1, 0, 0)
        ));

        scene.addObject(new Sphere(
                new Vector3D(7, 1.5, -16),   //both spheres are the same size, the blue is just farther away
                1,
                new Vector3D(0, 0, 1)
        ));

        Raytracer rt = new Raytracer(camera, scene);
        rt.setBackgroundColor(new Vector3D(1, 1, 1));

        rt.renderToFile("outputRaytracerV01.png");
    }
}