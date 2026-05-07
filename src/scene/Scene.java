package scene;

import objects.Object3D;
import tools.Intersection;
import tools.Ray;

import java.util.ArrayList;
import java.util.List;

public class Scene {

    private final List<Object3D> objects = new ArrayList<>();

    public void addObject(Object3D object) {
        objects.add(object);
    }

    public void removeObject(Object3D object) {
        objects.remove(object);
    }

    public List<Object3D> getObjects() {
        return objects;
    }

    public Intersection closestIntersection(Ray ray, double near, double far) {
        Intersection closest = null;

        for (Object3D obj : objects) {
            Intersection hit = obj.intersect(ray);

            if (hit != null && hit.isValid()) {
                double t = hit.getT();

                if ( t >= near && t <= far){

                    if (closest == null || t < closest.getT()) {
                        closest = hit;
                    }
                }
            }
        }

        return closest;
    }
}