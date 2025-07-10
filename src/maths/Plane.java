package maths;

public class Plane {
    public Vector3 normal;
    public float distance;

    public Plane(Vector3 normal, Vector3 point) {
        this.normal = normal.normalize();
        this.distance = -Vector3.Dot(this.normal, point);
    }

    public boolean isPointInside(Vector3 point) {
        float distanceToPlane = Vector3.Dot(normal, point) + distance;
        return distanceToPlane >= 0;
    }
}