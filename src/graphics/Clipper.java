package graphics;

import engine.GameObject;
import maths.Frustum;
import maths.Plane;
import maths.Vector3;
import java.util.List;

// Used to clip objects from the view-frustum
public class Clipper {

    // Discards and dissects meshes to improve efficiency
    public static boolean clipObject(Frustum frustum, GameObject object) {
        Vector3 center = object.transform.position;
        float radius = 5f;      // test for bounding circle
        for(Plane plane : frustum.getPlanes()) {
            float distance = Vector3.Dot(plane.normal, center) + plane.distance;
            if (distance < -radius) {
                return false;
            }
        }
        return true;
    }

    public static List<Vector3> clipTriangle(Frustum frustum, List<Vector3> vertices) {
        return vertices;
    }
}
