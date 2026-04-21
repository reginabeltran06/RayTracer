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

    public Intersection closestIntersection(Ray ray) {
        Intersection closest = null;

        for (Object3D obj : objects) {
            Intersection hit = obj.intersect(ray);

            if (hit != null && hit.isValid()) {
                if (closest == null || hit.getT() < closest.getT()) {
                    closest = hit;
                }
            }
        }

        return closest;
    }
}