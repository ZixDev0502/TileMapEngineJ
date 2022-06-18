package Model;

import java.util.LinkedList;
import java.util.Queue;

public class Level {

    public TileMap map;
    private final LinkedList<GameObject> objects = new LinkedList<>();
    private final Queue<GameObject> addQueue = new LinkedList<>();
    private final Queue<GameObject> removeQueue = new LinkedList<>();

    public Level(TileMap map) {
        this.map = map;
    }

    public void update() {
        for (GameObject o : objects) o.update();
        while (!removeQueue.isEmpty()) objects.remove(removeQueue.poll());
        while (!addQueue.isEmpty()) objects.add(addQueue.poll());
    }

    public void add(GameObject o) {
        if (o.levelContext != null) return;
        addQueue.add(o);
        o.levelContext = this;
    }

    public void remove(GameObject o) {
        addQueue.remove(o);
        o.levelContext = null;
    }

    public GameObject get(int i) {
        return objects.get(i);
    }

    public GameObject[] getAllObjects() {
        return objects.toArray(new GameObject[0]);
    }

    public boolean collides(GameObject o) {
        for (var o2 : map.colliders) {
            if (o.body.collides(o2))
                return true;
        }
        for (var o2 : objects) {
            if (o2 == o) continue;
            if (o.body.collides(o2.body))
                return true;
        }
        return false;
    }
}