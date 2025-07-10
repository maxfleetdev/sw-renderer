package maths;

import engine.Camera;
import java.util.List;

public class Frustum {
    private final Plane near, far, top, bottom, right, left;

    public Frustum(Camera camera, float vpW, float vpH, float nearClip, float farClip) {
        Vector3 position = camera.transform.position;
        Vector3 dir_forward = camera.transform.forward();
        Vector3 dir_up = camera.transform.up();
        Vector3 dir_right = camera.transform.right();

        // Calculate centers of near and far planes
        Vector3 nearCenter = position.add(dir_forward.scale(nearClip));
        Vector3 farCenter = position.add(dir_forward.scale(farClip));

        // Define near and far planes
        near = new Plane(dir_forward, nearCenter);
        far = new Plane(dir_forward.negate(), farCenter);

        // Calculate the four boundary points on the near plane
        Vector3 nearTop = nearCenter.add(dir_up.scale(vpH / 2));
        Vector3 nearBottom = nearCenter.add(dir_up.scale(-vpH / 2));
        Vector3 nearRight = nearCenter.add(dir_right.scale(vpW / 2));
        Vector3 nearLeft = nearCenter.add(dir_right.scale(-vpW / 2));

        // Define the top plane (ensure correct order for cross product)
        Vector3 topNormal = dir_right.cross(nearTop.minus(position)).normalize();
        top = new Plane(topNormal, nearTop);

        // Define the bottom plane
        Vector3 bottomNormal = nearBottom.minus(position).cross(dir_right).normalize();
        bottom = new Plane(bottomNormal, nearBottom);

        // Define the right plane
        Vector3 rightNormal = nearRight.minus(position).cross(dir_up).normalize();
        right = new Plane(rightNormal, nearRight);

        // Define the left plane
        Vector3 leftNormal = dir_up.cross(nearLeft.minus(position)).normalize();
        left = new Plane(leftNormal, nearLeft);
    }

    public List<Plane> getPlanes() {
        return List.of(near, far, top, bottom, right, left);
    }
}