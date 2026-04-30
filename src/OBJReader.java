import java.io.*;
import java.util.*;

public class OBJReader {

    public static void load(
            String path,
            Scene scene,
            Vector3D color,
            double scale,
            Vector3D offset
    ) throws IOException {

        List<Vector3D> vertices = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;

        while ((line = br.readLine()) != null) {

            line = line.trim();

            if (line.isEmpty() || line.startsWith("#"))
                continue;

            // vertex
            if (line.startsWith("v ")) {

                String[] parts = line.split("\\s+");

                double x = Double.parseDouble(parts[1]) * scale;
                double y = Double.parseDouble(parts[2]) * scale;
                double z = Double.parseDouble(parts[3]) * scale;

                vertices.add(new Vector3D(x, y, z).add(offset));
            }

            // Face
            else if (line.startsWith("f ")) {

                String[] parts = line.split("\\s+");


                int[] idx = new int[parts.length - 1];

                for (int i = 1; i < parts.length; i++) {

                    String token = parts[i];
                    String[] sub = token.split("/");

                    idx[i - 1] =
                            Integer.parseInt(sub[0]) - 1;
                }

                // triangle
                if (idx.length == 3) {

                    scene.addObject(
                            new Triangle(
                                    vertices.get(idx[0]),
                                    vertices.get(idx[1]),
                                    vertices.get(idx[2]),
                                    color
                            )
                    );
                }

                // if quad, splits into 2 triangles
                else if (idx.length == 4) {

                    scene.addObject(
                            new Triangle(
                                    vertices.get(idx[0]),
                                    vertices.get(idx[1]),
                                    vertices.get(idx[2]),
                                    color
                            )
                    );

                    scene.addObject(
                            new Triangle(
                                    vertices.get(idx[2]),
                                    vertices.get(idx[0]),
                                    vertices.get(idx[3]),
                                    color
                            )
                    );
                }
            }
        }

        br.close();
    }
}