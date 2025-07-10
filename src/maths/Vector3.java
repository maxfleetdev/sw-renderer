package maths;

import java.util.ArrayList;
import java.util.List;

public class Vector3 {
    public float x, y, z;
    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }



                // Instance Vector Math //
    public Vector3 minus(Vector3 b) {
        return new Vector3(x - b.x, y - b.y, z - b.z);
    }
    public Vector3 add(Vector3 b) {
        return new Vector3(x + b.x, y + b.y, z + b.z);
    }
    // Normalizes this Vector
    public Vector3 normalize() {
        return Normalized(this);
    }
    // Returns opposite sign of this vector
    public Vector3 negate() {
        return new Vector3(-x, -y, -z);
    }
    public float dot(Vector3 b) {
        return (this.x * b.x) + (this.y * b.y) + (this.z * b.z);
    }
    public Vector3 scale(float scalar) {
        return new Vector3(this.x * scalar, this.y * scalar, this.z * scalar);
    }
    public Vector3 cross(Vector3 b) {
        return new Vector3(
                this.y * b.z - this.z * b.y,
                this.z * b.x - this.x * b.z,
                this.x * b.y - this.y * b.x
        );
    }



                // Static Vector Math //
    public static Vector3 Add(Vector3 a, Vector3 b) {
        return new Vector3(a.x + b.x, a.y + b.y, a.z + b.z);
    }
    public static float Dot(Vector3 a, Vector3 b) {
        return (a.x * b.x) + (a.y * b.y) + (a.z * b.z);
    }
    public static Vector3 Scale(Vector3 a, Vector3 b) {
        return new Vector3(a.x * b.x, a.y * b.y, a.z * b.z);
    }
    public static Vector3 Cross(Vector3 a, Vector3 b) {
        return new Vector3(
                (a.y * b.z) - (a.z * b.y),
                (a.z * b.x) - (a.x * b.z),
                (a.x * b.y) - (a.y * b.x)
        );
    }
    public static float Magnitude(Vector3 a) {
        return (float)Math.sqrt((a.x * a.x) + (a.y * a.y) + (a.z * a.z));
    }
    public static Vector3 Divide(Vector3 a, float d) {
        return new Vector3(a.x / d, a.y / d, a.z / d);
    }
    public static Vector3 Normalized(Vector3 a) {
        float m = Magnitude(a);
        // Cannot divide by 0
        if (m == 0) {
            return Vector3.Zero();
        }
        return Divide(a, m);
    }
    public static List<Vector3> Swap(Vector3 a, Vector3 b) {
        List<Vector3> v = new ArrayList<>();
        Vector3 temp = a;
        a = b;
        b = temp;
        v.add(a);
        v.add(b);
        return v;
    }



                // Static Declarations //
    public static Vector3 Forward() {
        return new Vector3(0, 0, 1);
    }
    public static Vector3 Right() {
        return new Vector3(1, 0, 0);
    }
    public static Vector3 Up() {
        return new Vector3(0, 1, 0);
    }
    public static Vector3 Zero() {
        return new Vector3(0, 0, 0);
    }
}