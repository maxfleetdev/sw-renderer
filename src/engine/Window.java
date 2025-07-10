package engine;

import graphics.Renderer;
import javax.swing.*;

public class Window extends JFrame {
    private final Renderer renderer;

    public Window(int width, int height, String title) {
        // Define new engine.Window
        setSize(width, height);
        setLocationRelativeTo(null);
        setTitle(title);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Create and Add Renderer
        renderer = new graphics.Renderer(getWidth(), getHeight());
        add(renderer);

        // Finalise Window
        pack();
        setVisible(true);
    }

    public graphics.Renderer getRenderer() {
        return renderer;
    }
}