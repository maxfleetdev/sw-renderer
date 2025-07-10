package graphics;

import maths.Vector3;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// Contains all necessary instructions for drawing shapes/triangles
public class Rasterizer {
    private final Renderer renderer;
    public Rasterizer(Renderer renderer) {
        this.renderer = renderer;
    }

    public void drawTriangle(Vector3 v0, Vector3 v1, Vector3 v2, Color color) {
        // stupid
        Vector3 p0 = v0, p1 = v1, p2 = v2;

        // Step 1: Sort the points by their y-coordinates
        if (p1.y < p0.y) { Vector3 temp = p0; p0 = p1; p1 = temp; }
        if (p2.y < p0.y) { Vector3 temp = p0; p0 = p2; p2 = temp; }
        if (p2.y < p1.y) { Vector3 temp = p1; p1 = p2; p2 = temp; }

        // Step 2: Compute the x-coordinates of the triangle's edges
        List<Float> x01 = interpolate(p0.y, p0.x, p1.y, p1.x);
        List<Float> x12 = interpolate(p1.y, p1.x, p2.y, p2.x);
        List<Float> x02 = interpolate(p0.y, p0.x, p2.y, p2.x);

        // Step 3: Concatenate the short sides to get the full edge from P0 to P2
        x01.remove(x01.size() - 1);
        List<Float> x012 = new ArrayList<>(x01);
        x012.addAll(x12);

        // Step 4: Determine which side is left and which is right
        List<Float> x_left, x_right;
        int m = x012.size() / 2;
        if (x02.get(m) < x012.get(m)) {
            x_left = x02;
            x_right = x012;
        } else {
            x_left = x012;
            x_right = x02;
        }

        // Step 5: Draw the horizontal segments to fill the triangle
        int startY = Math.round(p0.y);
        int endY = Math.round(p2.y);
        for (int y = startY; y < endY; y++) {
            int xStart = Math.round(x_left.get(y - startY));
            int xEnd = Math.round(x_right.get(y - startY));
            for (int x = xStart; x < xEnd; x++) {
                renderer.putPixel(x, y, color); // Draw pixel
            }
        }
    }

    // Interpolates between i0, d0 and i1, d1 and returns a list of values
    private List<Float> interpolate(float i0, float d0, float i1, float d1) {
        List<Float> values = new ArrayList<>();

        // No need to interpolate if the points are the same
        if (i0 == i1) {
            values.add(d0);
            return values;
        }

        float a = (d1 - d0) / (i1 - i0);
        float d = d0;
        for (float i = i0; i <= i1; i++) {
            values.add(d);
            d += a;
        }
        return values;
    }


        ///////// SIMPLE RASTER \\\\\\\\\\\


    public void drawWireTriangle(Vector3 v0, Vector3 v1, Vector3 v2) {
        drawLine(v1, v0);
        drawLine(v1, v2);
        drawLine(v2, v0);
    }

    public void drawLine(Vector3 p0, Vector3 p1) {
        // Determine if the line is more horizontal or vertical
        if (Math.abs(p1.x - p0.x) > Math.abs(p1.y - p0.y)) {
            if (p0.x > p1.x) {
                Vector3 temp = p0;
                p0 = p1;
                p1 = temp;
            }

            // Interpolate y-values between p0 and p1
            List<Float> ys = interpolate(p0.x, p0.y, p1.x, p1.y);

            // Draw the line
            int startX = Math.round(p0.x);
            int endX = Math.round(p1.x);
            for (int x = startX; x < endX; x++) {
                renderer.putPixel(x, ys.get(x - startX).intValue(), Color.red);
            }
        }

        else {
            if (p0.y > p1.y) {
                Vector3 temp = p0;
                p0 = p1;
                p1 = temp;
            }

            // Interpolate x-values between p0 and p1
            List<Float> xs = interpolate(p0.y, p0.x, p1.y, p1.x);
            int startY = Math.round(p0.y);
            int endY = Math.round(p1.y);

            // Draw the line
            for (int y = startY; y < endY; y++) {
                renderer.putPixel(xs.get(y - startY).intValue(), y, Color.red);
            }
        }
    }
}