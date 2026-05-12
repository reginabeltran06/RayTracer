package tools;
import scene.*;
import objects.*;
import java.io.*;
import java.util.*;

public class OBJReader {

    public static void load(
            String path,
            Scene scene,
            Vector3D offset,
            double scale,
            Vector3D color
    ) throws IOException {

        List<Vector3D> vertices = new ArrayList<>();
        List<Vector3D> normals = new ArrayList<>();
        List<Face> faces = new ArrayList<>();


        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;

        int currentSmoothingGroup = 1;

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

            else if (line.startsWith("s ")) {

                String[] parts = line.split("\\s+");

                if (parts[1].equalsIgnoreCase("off")) {
                    currentSmoothingGroup = Integer.parseInt(parts[1]);
                }
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
                            normalIdx,
                            currentSmoothingGroup,
                            hasNormals
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

                    faces.add(new Face(tri1v, tri1n,currentSmoothingGroup, hasNormals));
                    faces.add(new Face(tri2v, tri2n,currentSmoothingGroup, hasNormals));

                }
            }
        }

        Map<String, Vector3D> generatedNormals = new HashMap<>();


        if (normals.isEmpty()) {

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


                String k0 = i0 + "_" + face.smoothingGroup;
                String k1 = i1 + "_" + face.smoothingGroup;
                String k2 = i2 + "_" + face.smoothingGroup;

                generatedNormals.put(
                    k0,
                    generatedNormals
                        .getOrDefault(k0,
                            new Vector3D(0,0,0))
                        .add(faceNormal)
                );

                generatedNormals.put(
                    k1,
                    generatedNormals
                        .getOrDefault(k1,
                            new Vector3D(0,0,0))
                        .add(faceNormal)
                );

                generatedNormals.put(
                    k2,
                    generatedNormals
                        .getOrDefault(k2,
                            new Vector3D(0,0,0))
                        .add(faceNormal)
                );


            }
        }

        if (normals.isEmpty()) {
            for (String key : generatedNormals.keySet()) {
                generatedNormals.put(
                    key,
                    generatedNormals.get(key).normalize()
                );
            }
        }

        for (Face face : faces) {

            int[] v = face.vertexIdx;
            int[] n = face.normalIdx;

            if (face.hasNormals) {

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

                String k0 = v[0] + "_" + face.smoothingGroup;
                    String k1 = v[1] + "_" + face.smoothingGroup;
                    String k2 = v[2] + "_" + face.smoothingGroup;

                    scene.addObject(
                        new Triangle(

                            vertices.get(v[0]),
                            vertices.get(v[1]),
                            vertices.get(v[2]),

                            generatedNormals.get(k0),
                            generatedNormals.get(k1),
                            generatedNormals.get(k2),

                            color
                        )
                    );

            }


        }


        br.close();
    }
}