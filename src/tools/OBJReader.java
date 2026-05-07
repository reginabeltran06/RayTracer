package tools;
import scene.*;
import objects.*;
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
        List<Vector3D> normals = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;

        while ((line = br.readLine()) != null) {

            line = line.trim();

            if (line.isEmpty() || line.startsWith("#"))
                continue;

            //vertex normals
            if (line.startsWith("vn ")) {

                String[] parts = line.split("\\s+");

                double x = Double.parseDouble(parts[1]);
                double y = Double.parseDouble(parts[2]);
                double z = Double.parseDouble(parts[3]);

                normals.add(new Vector3D(x, y, z).normalize());
            }

            // vertex
            else if (line.startsWith("v ")) {

                String[] parts = line.split("\\s+");

                double x = Double.parseDouble(parts[1]) * scale;
                double y = Double.parseDouble(parts[2]) * scale;
                double z = Double.parseDouble(parts[3]) * scale;

                vertices.add(new Vector3D(x, y, z).add(offset));
            }

            // face
            else if (line.startsWith("f ")) {

                String[] parts = line.split("\\s+");

                int[] vertexIdx = new int[parts.length - 1];
                int[] normalIdx = new int[parts.length - 1];

                Arrays.fill(normalIdx, -1);

                boolean hasNormals = true;


                for (int i = 1; i < parts.length; i++) {

                    String token = parts[i];
                    String[] sub = token.split("/");

                    // vertex index
                    vertexIdx[i - 1] = Integer.parseInt(sub[0]) - 1;

                    // normal index
                    if (sub.length >= 3 && !sub[2].isEmpty()) {

                        normalIdx[i - 1] = Integer.parseInt(sub[2]) - 1;
                    } else {
                        hasNormals = false;
                    }
                }


                // triangle
                if (vertexIdx.length == 3) {
                    addTriangle(
                        scene,
                        vertices,
                        normals,
                        vertexIdx,
                        normalIdx,
                        hasNormals,
                        color
                    );
                }

                // if quad, splits into 2 triangles
                else if (vertexIdx.length == 4) {
                    int[] tri1v = {
                            vertexIdx[0],
                            vertexIdx[1],
                            vertexIdx[2]
                    };

                    int[] tri2v = {
                            vertexIdx[0],
                            vertexIdx[2],
                            vertexIdx[3]
                    };

                    int[] tri1n = {
                            normalIdx[0],
                            normalIdx[1],
                            normalIdx[2]
                    };

                    int[] tri2n = {
                            normalIdx[0],
                            normalIdx[2],
                            normalIdx[3]
                    };

                    addTriangle(
                            scene,
                            vertices,
                            normals,
                            tri1v,
                            tri1n,
                            hasNormals,
                            color
                    );

                    addTriangle(
                            scene,
                            vertices,
                            normals,
                            tri2v,
                            tri2n,
                            hasNormals,
                            color
                    );
                }
            }
        }

        br.close();
    }

    private static void addTriangle(
        Scene scene,
        List<Vector3D> vertices,
        List<Vector3D> normals,
        int[] v,
        int[] n,
        boolean hasNormals,
        Vector3D color
        ) {

            if (hasNormals) {
                scene.addObject(
                    new Triangle(
                        vertices.get(v[0]),
                        vertices.get(v[1]),
                        vertices.get(v[2]),

                        normals.get(n[0]),
                        normals.get(n[1]),
                        normals.get(n[2]),

                        color
                    )
                );
            }

            else {
                scene.addObject(
                    new Triangle(
                        vertices.get(v[0]),
                        vertices.get(v[1]),
                        vertices.get(v[2]),

                        color
                    )
                );
            }
        }
}