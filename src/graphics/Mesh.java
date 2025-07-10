package graphics;

import java.util.List;

// Contains Triangles, Verts, UV and textures for rendering
// Essentially an instruction manual for rendering a 3D object
public class Mesh {
    public List<Triangle> triangles;
    public List<Vertex> vertices;
    public Mesh(List<Triangle> triangles, List<Vertex> vertices) {
        this.triangles = triangles;
        this.vertices = vertices;
    }
}