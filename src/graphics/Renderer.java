package graphics;

import engine.Camera;
import engine.GameObject;
import engine.InputHandler;
import engine.Scene;
import maths.Frustum;
import maths.Matrix4x4;
import maths.Vector3;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Renderer extends Canvas {
    private BufferStrategy bufferStrategy;
    private final BufferedImage pixelBuffer;
    private final Rasterizer rasterizer;
    public final InputHandler inputHandler = new InputHandler();

    private final float vpD = 1f;
    private final float vpW = 1f;
    private final float vpH;
    private final float nearClip = 0.5f;

    public Renderer(int width, int height) {
        setSize(width, height);
        pixelBuffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        rasterizer = new Rasterizer(this);

        // Define Viewport
        float aspectRatio = (float) getWidth() / getHeight();
        vpH = vpW / aspectRatio;

        // Input Handler
        this.addKeyListener(inputHandler);
        this.setFocusable(true);
        this.requestFocusInWindow();
    }

    public void drawScene(Scene scene) {
        Camera camera = scene.camera;
        Frustum viewFrustum = new Frustum(scene.camera, vpW, vpH, nearClip, 100f);
        for(GameObject obj : scene.gameObjects) {
            if (obj.mesh == null) {
                continue;
            }

            // 1. Check if objects are inside view frustum - otherwise continue
            if (!Clipper.clipObject(viewFrustum, obj)) {
                System.out.println(obj.name + " not in frustum");
                continue;
            }

            // 2. Apply Model and View projections to vertices
            List<Vector3> projected = new ArrayList<>();
            for(Vertex vertex : obj.mesh.vertices) {
                // Model Space -> World Space
                Vector3 transformed = obj.transform.applyTransform(vertex.position);

                // World Space -> View Space
                Vector3 transVertex = Matrix4x4.Multiply(camera.getViewMatrix(), transformed);

                // Vertices to cull-check
                projected.add(transVertex);
            }

            // 3. Apply culling to projected vertices
            for(Triangle tri : obj.mesh.triangles) {
                Vector3 p0 = projected.get(tri.v0.index);
                Vector3 p1 = projected.get(tri.v1.index);
                Vector3 p2 = projected.get(tri.v2.index);

                // 3.1 Clip Triangle
                List<Vector3> clippedVertices = Clipper.clipTriangle(viewFrustum, List.of(p0, p1, p2));
                if (clippedVertices.isEmpty()) {
                    continue;
                }

                // 3.2 Project Clipped Triangles
                List<Vector3> screenVertices = new ArrayList<>();
                for(Vector3 v : clippedVertices) {
                    screenVertices.add(projectVertex(v));
                }

                // 4. Rasterize Triangle
                if (screenVertices.get(0) != null && screenVertices.get(1) != null && screenVertices.get(2) != null) {
                    rasterizer.drawTriangle(screenVertices.get(0), screenVertices.get(1), screenVertices.get(2), Color.red);
                }
            }
        }
    }

    // Project a 3D vertex onto the 2D canvas using perspective projection
    private Vector3 projectVertex(Vector3 v) {
        if (v.z < nearClip) {
            return null;
        }
        float x = v.x * vpD / v.z;
        float y = v.y * vpD / v.z;
        return viewportToCanvas(x, y);
    }

    // Convert viewport coordinates (3D space) to canvas (2D screen) coordinates
    private Vector3 viewportToCanvas(float x, float y) {
        // Canvas Width & Height
        float Cw = (float)getWidth();
        float Ch = (float)getHeight();

        // Map viewport coordinates to canvas coordinates
        int canvasX = (int) (x * (Cw / vpW));
        int canvasY = (int) (y * (Ch / vpH));
        return new Vector3(canvasX, canvasY, 0);
    }

    // Draw pixel at a certain XY position on PixelBuffer
    public void putPixel(int x, int y, Color color) {
        // Convert coordinates back (0,0 = Top Left)
        int sx = (pixelBuffer.getWidth() / 2) + x;
        int sy = (pixelBuffer.getHeight() / 2) - y;

        // Check if in bounds
        if (sx >= 0 && sx < pixelBuffer.getWidth() &&
                sy >= 0 && sy < pixelBuffer.getHeight()) {
            pixelBuffer.setRGB(sx, sy, color.getRGB());
        }
    }

    // Clears the current frame buffer
    public void clearBuffer() {
        Graphics2D g = pixelBuffer.createGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.dispose();
    }

    // Render the final PixelBuffer
    public void render() {
        if (bufferStrategy == null) {
            createBuffer();
            return;
        }
        do {
            Graphics g = bufferStrategy.getDrawGraphics();
            g.drawImage(pixelBuffer, 0, 0, null);
            g.dispose();
            bufferStrategy.show();
        } while (bufferStrategy.contentsLost());
    }

    // Create a BufferStrategy if engine.Window is Visible
    private void createBuffer() {
        if (isDisplayable() && bufferStrategy == null) {
            createBufferStrategy(3);
            bufferStrategy = getBufferStrategy();
        }
    }
}