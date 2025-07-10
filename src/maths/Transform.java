package maths;

// Contains all world information for any object in a scene
public class Transform {
    public Vector3 position, rotation, scale;
    public Transform() {
        position = Vector3.Zero();
        rotation = Vector3.Zero();
        scale = new Vector3(1,1,1);
    }

    public Matrix4x4 getTransformMatrix() {
        Matrix4x4 scaleMatrix = Matrix4x4.Scale(scale.x, scale.y, scale.z);
        Matrix4x4 rotationMatrixX = Matrix4x4.RotationX(rotation.x);
        Matrix4x4 rotationMatrixY = Matrix4x4.RotationY(rotation.y);
        Matrix4x4 rotationMatrixZ = Matrix4x4.RotationZ(rotation.z);
        Matrix4x4 translationMatrix = Matrix4x4.Translation(position.x, position.y, position.z);

        // Combine transformations in the correct order: Scale -> Rotate -> Translate
        Matrix4x4 transformMatrix = Matrix4x4.Multiply(rotationMatrixZ, rotationMatrixY);
        transformMatrix = Matrix4x4.Multiply(transformMatrix, rotationMatrixX);
        transformMatrix = Matrix4x4.Multiply(transformMatrix, scaleMatrix);
        transformMatrix = Matrix4x4.Multiply(transformMatrix, translationMatrix);

        return transformMatrix;
    }

    public static Vector3 Scale(Vector3 vertex, Vector3 scale) {
        return new Vector3(
                vertex.x * scale.x,
                vertex.y * scale.y,
                vertex.z * scale.z);
    }

    public static Vector3 Rotate(Vector3 vertex, Vector3 rotation) {
        // Step 1: Rotate around the X-axis
        float sinX = (float) Math.sin(rotation.x);
        float cosX = (float) Math.cos(rotation.x);
        float y1 = vertex.y * cosX - vertex.z * sinX;
        float z1 = vertex.y * sinX + vertex.z * cosX;

        // Step 2: Rotate around the Y-axis
        float sinY = (float) Math.sin(rotation.y);
        float cosY = (float) Math.cos(rotation.y);
        float x2 = vertex.x * cosY + z1 * sinY;
        float z2 = z1 * cosY - vertex.x * sinY;

        // Step 3: Rotate around the Z-axis
        float sinZ = (float) Math.sin(rotation.z);
        float cosZ = (float) Math.cos(rotation.z);
        float x3 = x2 * cosZ - y1 * sinZ;
        float y3 = x2 * sinZ + y1 * cosZ;

        // Return the final rotated vector
        return new Vector3(x3, y3, z2);
    }

    public static Vector3 Translate(Vector3 vertex, Vector3 translation) {
        return Vector3.Add(vertex, translation);
    }

    public Vector3 applyTransform(Vector3 vertex) {
        Vector3 scaled = Scale(vertex, this.scale);
        Vector3 rotated = Rotate(scaled, this.rotation);
        return Translate(rotated, this.position);
    }

    // Transform Directions //

    public Vector3 forward() {
        Vector3 forward = Rotate(Vector3.Forward(), rotation);
        return forward.normalize();
    }

    public Vector3 up() {
        Vector3 up = Rotate(Vector3.Up(), rotation);
        return up.normalize();
    }

    public Vector3 right() {
        Vector3 right = Rotate(Vector3.Right(), rotation);
        return right.normalize();
    }
}