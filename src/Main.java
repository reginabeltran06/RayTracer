import lights.DirectionalLight;
import lights.Light;
import lights.PointLight;
import objects.Camera;
import objects.Sphere;
import scene.Scene;
import tools.OBJReader;
import tools.Raytracer;
import tools.Vector3D;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        Camera camera = new Camera(
                new Vector3D(0, 0, 0),
                1200,
                1200,
                60,
                4,
                30
        );

        Scene scene = new Scene();


        scene.addLight(new DirectionalLight(
                new Vector3D(0, 0, -1),
                new Vector3D(1,1,1),
                .5
        ));

        scene.addLight(new PointLight(
                new Vector3D(4,-5,-10),
                new Vector3D(1,1,1),
                1

        ));

        scene.addLight(new PointLight(
                new Vector3D(-3, 0, -5),
                new Vector3D(1,0,0),
                .5
        ));


        scene.addLight(new PointLight(
                new Vector3D(5, 5, -13),
                new Vector3D(0,0,1),
                1.2
        ));

        scene.addObject(new Sphere(
                new Vector3D(4, -5, -17),
                2,
                new Vector3D(0, 0, 1)
        ));


        OBJReader.load(
                "src/models/teapot.obj",
                scene,
                new Vector3D(-2,-1,-9),
                2,
                new Vector3D(0, 1, 1)
        );


        OBJReader.load(
                "src/models/bunny.obj",
                scene,
                new Vector3D(3,.5,-10),
                25,
                new Vector3D(1, 0, 1)
        );

        OBJReader.load(
                "src/models/cube.obj",
                scene,
                new Vector3D(-2,-4, -12),
                1,
                new Vector3D(1,0,0)
        );


        Raytracer rt = new Raytracer(camera, scene);
        rt.setBackgroundColor(new Vector3D(0, 0, 0));

        rt.renderToFile("outputRaytracerV06.png");
    }
}