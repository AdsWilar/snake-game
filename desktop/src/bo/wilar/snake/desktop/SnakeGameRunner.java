package bo.wilar.snake.desktop;

import bo.wilar.snake.SnakeGame;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class SnakeGameRunner {

    private final String title;
    private final Integer height;
    private final Integer width;

    public SnakeGameRunner(String title, Integer height, Integer width) {
        this.title = title;
        this.height = height;
        this.width = width;
    }

    public void run() {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = this.title;
        config.height = this.height;
        config.width = this.width;
        new LwjglApplication(new SnakeGame(), config);
    }

}