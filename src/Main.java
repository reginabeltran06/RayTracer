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
                new Vector3D(0, 1, -1),         //light coming from "the camera" and from below
                new Vector3D(1,1,1),
                1.0
        );


        Scene scene = new Scene();


        scene.addObject(new Sphere(
                new Vector3D(4, -5, -27),
                2,
                new Vector3D(1, 0, 0)
        ));


        OBJReader.load(
                "models/teapot.obj",
                scene,
                new Vector3D(0, 1, 0),
                2,
                new Vector3D(-2,0,-10)

        );

        OBJReader.load(
                "models/cube.obj",
                scene,
                new Vector3D(0, 0, 1),
                1,
                new Vector3D(-3,-4,-11)

        );

        OBJReader.load(
                "models/bunny.obj",
                scene,
                new Vector3D(1, 0, 1),
                25.0,
                new Vector3D(3,.5,-10)
        );


//
//        scene.addObject(new Sphere(
//                new Vector3D(-12, 0, -30),
//                1.5,
//                new Vector3D(0, 1, 0)
//        ));
//
//
//        scene.addObject(new Triangle(
//                new Vector3D(1, -1, -7),
//                new Vector3D(4, -1, -7),
//                new Vector3D(2.5, 2, -7),
//                new Vector3D(0, 0, 1)
//        ));


        Raytracer rt = new Raytracer(camera, scene, light);
        rt.setBackgroundColor(new Vector3D(1, 1, 1));

        rt.renderToFile("outputRaytracerV04.png");
    }
}