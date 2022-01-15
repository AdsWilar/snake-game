package bo.wilar.snake.desktop;

import bo.wilar.snake.SnakeGame;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class SnakeGameRunner {

    private final String title;
    private final Integer resolution;

    public SnakeGameRunner(String title, Integer resolution) {
        this.title = title;
        this.resolution = resolution;
    }

    public void run() {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = this.title;
        config.height = this.resolution;
        config.width = this.resolution;
        new LwjglApplication(new SnakeGame(this.resolution), config);
    }

}