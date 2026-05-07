import lights.Light;
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

        Light light = new Light(
                new Vector3D(0, -1, -1),         //light coming from "the camera" and from above
                new Vector3D(1,1,1),
                2
        );


        Scene scene = new Scene();


        scene.addObject(new Sphere(
                new Vector3D(4, -5, -27),
                2,
                new Vector3D(0, 0, 1)
        ));


        OBJReader.load(
                "src/models/teapot.obj",
                scene,
                new Vector3D(0, 1, 1),
                2,
                new Vector3D(-2,-1,-9)

        );


        OBJReader.load(
                "src/models/bunny.obj",
                scene,
                new Vector3D(1, 0, 1),
                25.0,
                new Vector3D(3,.5,-10)
        );


//
//        scene.addObject(new objects.Sphere(
//                new tools.Vector3D(-12, 0, -30),
//                1.5,
//                new tools.Vector3D(0, 1, 0)
//        ));
//
//
//        scene.addObject(new objects.Triangle(
//                new tools.Vector3D(1, -1, -7),
//                new tools.Vector3D(4, -1, -7),
//                new tools.Vector3D(2.5, 2, -7),
//                new tools.Vector3D(0, 0, 1)
//        ));


        Raytracer rt = new Raytracer(camera, scene, light);
        rt.setBackgroundColor(new Vector3D(1, 1, 1));

        rt.renderToFile("outputRaytracerV05.png");
    }
}