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
        List<Face> faces = new ArrayList<>();


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
                    faces.add(
                        new Face(
                            vertexIdx,
                            normalIdx
                        )
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

                    faces.add(new Face(tri1v, tri1n));
                    faces.add(new Face(tri2v, tri2n));

                }
            }
        }

        boolean useOBJNormals = !normals.isEmpty();
        List<Vector3D> generatedNormals = new ArrayList<>();

        for (int i = 0; i < vertices.size(); i++) {
            generatedNormals.add(new Vector3D(0,0,0));
        }

        if (!useOBJNormals) {

            for (Face face : faces) {

                int i0 = face.vertexIdx[0];
                int i1 = face.vertexIdx[1];
                int i2 = face.vertexIdx[2];

                Vector3D v0 = vertices.get(i0);
                Vector3D v1 = vertices.get(i1);
                Vector3D v2 = vertices.get(i2);

                Vector3D edge1 = v1.subtract(v0);
                Vector3D edge2 = v2.subtract(v0);

                Vector3D faceNormal = edge1.cross(edge2).normalize();

                generatedNormals.set(
                    i0,
                    generatedNormals.get(i0).add(faceNormal)
                );

                generatedNormals.set(
                    i1,
                    generatedNormals.get(i1).add(faceNormal)
                );

                generatedNormals.set(
                    i2,
                    generatedNormals.get(i2).add(faceNormal)
                );
            }
        }

        if (!useOBJNormals) {
            for (int i = 0; i < generatedNormals.size(); i++) {
                generatedNormals.set(
                    i,
                    generatedNormals.get(i).normalize()
                );
            }
        }

        for (Face face : faces) {

            int[] v = face.vertexIdx;
            int[] n = face.normalIdx;

            if (useOBJNormals) {

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

            } else {

                scene.addObject(
                    new Triangle(

                        vertices.get(v[0]),
                        vertices.get(v[1]),
                        vertices.get(v[2]),

                        generatedNormals.get(v[0]),
                        generatedNormals.get(v[1]),
                        generatedNormals.get(v[2]),

                        color
                    )
                );
            }
        }


        br.close();
    }
}