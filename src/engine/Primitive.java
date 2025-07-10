package engine;

import graphics.Mesh;
import graphics.Triangle;
import graphics.Vertex;
import maths.Vector3;
import java.util.ArrayList;
import java.util.List;

// Returns a primitive mesh
public class Primitive {

    // Create a cube with given measurements
    public static Mesh Cube(float width, float height, float depth) {
        List<Vertex> vertices = new ArrayList<>();
        List<Triangle> triangles = new ArrayList<>();

        // Vertices for the cube (front and back faces)
        Vertex v0 = new Vertex(new Vector3(-width / 2, -height / 2, depth / 2), 0);  // front face vertices
        Vertex v1 = new Vertex(new Vector3(-width / 2, height / 2, depth / 2), 1);
        Vertex v2 = new Vertex(new Vector3(width / 2, height / 2, depth / 2), 2);
        Vertex v3 = new Vertex(new Vector3(width / 2, -height / 2, depth / 2), 3);
        Vertex v4 = new Vertex(new Vector3(-width / 2, -height / 2, -depth / 2), 4);  // back face vertices
        Vertex v5 = new Vertex(new Vector3(-width / 2, height / 2, -depth / 2), 5);
        Vertex v6 = new Vertex(new Vector3(width / 2, height / 2, -depth / 2), 6);
        Vertex v7 = new Vertex(new Vector3(width / 2, -height / 2, -depth / 2), 7);

        vertices.add(v0); vertices.add(v1); vertices.add(v2); vertices.add(v3);
        vertices.add(v4); vertices.add(v5); vertices.add(v6); vertices.add(v7);

        // Triangles (12 total, 2 per face)
        triangles.add(new Triangle(v0, v1, v2)); triangles.add(new Triangle(v0, v2, v3)); // Front face
        triangles.add(new Triangle(v4, v0, v3)); triangles.add(new Triangle(v4, v3, v7)); // Left face
        triangles.add(new Triangle(v5, v4, v7)); triangles.add(new Triangle(v5, v7, v6)); // Back face
        triangles.add(new Triangle(v1, v5, v6)); triangles.add(new Triangle(v1, v6, v2)); // Right face
        triangles.add(new Triangle(v4, v5, v1)); triangles.add(new Triangle(v4, v1, v0)); // Top face
        triangles.add(new Triangle(v2, v6, v7)); triangles.add(new Triangle(v2, v7, v3)); // Bottom face

        // Return a new Cube with these vertices and triangles
        return new Mesh(triangles, vertices);
    }

    public static Mesh Sphere(float radius, int segments, int rings) {
        List<Vertex> vertices = new ArrayList<>();
        List<Triangle> triangles = new ArrayList<>();

        // Create vertices using latitude and longitude angles
        for (int i = 0; i <= rings; i++) {
            float lat = (float) (Math.PI * i / rings);
            float y = radius * (float) Math.cos(lat);
            float ringRadius = radius * (float) Math.sin(lat);

            for (int j = 0; j <= segments; j++) {
                float lon = (float) (2 * Math.PI * j / segments);
                float x = ringRadius * (float) Math.cos(lon);
                float z = ringRadius * (float) Math.sin(lon);

                vertices.add(new Vertex(new Vector3(x, y, z), i * (segments + 1) + j));
            }
        }

        // Create triangles for the sphere
        for (int i = 0; i < rings; i++) {
            for (int j = 0; j < segments; j++) {
                int first = i * (segments + 1) + j;
                int second = first + segments + 1;

                triangles.add(new Triangle(vertices.get(first), vertices.get(second), vertices.get(first + 1)));
                triangles.add(new Triangle(vertices.get(second), vertices.get(second + 1), vertices.get(first + 1)));
            }
        }

        return new Mesh(triangles, vertices);
    }
}
