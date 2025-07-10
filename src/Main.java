import engine.*;
import graphics.*;
import maths.Vector3;
import java.util.List;

public class Main {
    private static final int SCREEN_WIDTH = 1920/2, SCREEN_HEIGHT = 1080/2;
    private static Window window;
    private static Renderer renderer;
    private static Scene scene;
    private static InputHandler inputHandler;
    private static Camera mainCamera;

    public static void main(String[] args) {
        init();
        makeScene();
        while(true) {
            update();
            render();
            limitFrames();
        }
    }

    // Initialise Program
    public static void init() {
        // Create engine.Window & graphics.Renderer
        window = new Window(SCREEN_WIDTH, SCREEN_HEIGHT, "Software Renderer");
        renderer = window.getRenderer();
        inputHandler = renderer.inputHandler;
    }

    // simple test
    public static void makeScene() {
        // Make Mesh
        Mesh cube = Primitive.Cube(1, 1, 1);
        Mesh sphere = Primitive.Sphere(1, 8, 8);

        // Make Objects
        GameObject obj = new GameObject(cube);
        obj.name = "Cube LEFT";
        GameObject obj3 = new GameObject(cube);
        obj3.name = "Cube RIGHT";
        GameObject obj2 = new GameObject(sphere);
        obj2.name = "Sphere BACK";
        GameObject obj4 = new GameObject(sphere);
        obj4.name = "Sphere FRONT";
        obj.transform.position = new Vector3(-3, 0, 0);
        obj2.transform.position = new Vector3(0, 0, -3);
        obj3.transform.position = new Vector3(3, 0, 0);
        obj4.transform.position = new Vector3(0, 0, 3);

        // Make Camera
        Camera camera = new Camera(60, (float) SCREEN_WIDTH / SCREEN_HEIGHT, 0.1f, 100f);
        camera.transform.position = new Vector3(0, 0, 0);
        mainCamera = camera;

        // Add to scene
        scene = new Scene(List.of(obj, obj2, obj3, obj4));
        scene.camera = camera;
    }

    public static void render() {
        // Clear the renderers' current buffer
        renderer.clearBuffer();
        // Render Scene
        renderer.drawScene(scene);
        // Draw PixelBuffer
        renderer.render();
    }

    public static void limitFrames() {
        try {
            Thread.sleep(6);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public static float moveSpeed = 0.04f, rotateSpeed = 0.01f;

    public static void update() {
        if (inputHandler.isKeyPressed()) {
            char key = inputHandler.getCurrentKeyPressed();
            switch (key) {
                case 'a':
                    // Rotate left
                    mainCamera.transform.rotation.y += rotateSpeed;
                    break;

                case 'd':
                    // Rotate right
                    mainCamera.transform.rotation.y -= rotateSpeed;
                    break;
            }

            Vector3 forward = mainCamera.transform.forward().normalize();

            switch (key) {
                case 'w':
                    // Move forward
                    mainCamera.transform.position = mainCamera.transform.position.add(forward.scale(moveSpeed));
                    break;

                case 's':
                    // Move backward
                    mainCamera.transform.position = mainCamera.transform.position.minus(forward.scale(moveSpeed));
                    break;

                case 'q':
                    // Move up
                    mainCamera.transform.position.y += moveSpeed;
                    break;

                case 'e':
                    // Move down
                    mainCamera.transform.position.y -= moveSpeed;
                    break;

                default:
                    break;
            }
        }
    }
}