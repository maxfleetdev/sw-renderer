package engine;

import java.util.ArrayList;
import java.util.List;

public class Scene {
    public List<GameObject> gameObjects;
    public Camera camera;
    public Scene() {
        this.gameObjects = new ArrayList<>();
    }
    public Scene(List<GameObject> objects) {
        this.gameObjects = objects;
    }

    public void addObject(GameObject obj) {
        // Needed?
        if (gameObjects.contains(obj)) {
            return;
        }
        gameObjects.add(obj);
    }

    public void removeObject(GameObject obj) {
        gameObjects.remove(obj);
    }
}