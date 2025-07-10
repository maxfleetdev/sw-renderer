package engine;

import graphics.Mesh;
import maths.Transform;

// Contains information for such as mesh, collision, transform etc.
public class GameObject {
    public Mesh mesh;
    public final Transform transform;
    public String name;

    public GameObject() {
        transform = new Transform();
        mesh = null;
        name = "";
    }
    public GameObject(Mesh mesh) {
        transform = new Transform();
        this.mesh = mesh;
        name = "";
    }
}