package graphics;

import maths.Vector3;

public class Vertex {
    public Vector3 position;
    public float w;
    public int index;
    public Vertex(Vector3 pos, int index) {
        this.position = pos;
        this.index = index;
        this.w = 1f;
    }
}