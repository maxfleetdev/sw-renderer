package engine;

import maths.Matrix4x4;
import maths.Transform;

public class Camera {
    public final Transform transform;
    public float fieldOfView;
    public float aspectRatio;
    public float nearClip;
    public float farClip;
    public Camera(float fov, float aspect, float near, float far) {
        transform = new Transform();
        this.fieldOfView = fov;
        this.aspectRatio = aspect;
        this.nearClip = near;
        this.farClip = far;
    }

    public Matrix4x4 getViewMatrix() {
        // Compute rotation matrices for camera orientation
        Matrix4x4 rotationMatrixX = Matrix4x4.RotationX(transform.rotation.x);
        Matrix4x4 rotationMatrixY = Matrix4x4.RotationY(transform.rotation.y);
        Matrix4x4 rotationMatrixZ = Matrix4x4.RotationZ(transform.rotation.z);

        // Combine the rotation matrices in the order: Z -> Y -> X
        Matrix4x4 cameraMatrix = Matrix4x4.Multiply(rotationMatrixZ, Matrix4x4.Multiply(rotationMatrixY, rotationMatrixX));

        // Construct translation matrix (moving the world in the opposite direction of the camera)
        Matrix4x4 translationMatrix = Matrix4x4.Translation(-transform.position.x, -transform.position.y, -transform.position.z);
        cameraMatrix = Matrix4x4.Multiply(cameraMatrix, translationMatrix);

        return cameraMatrix;
    }
}