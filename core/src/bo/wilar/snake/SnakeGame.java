package bo.wilar.snake;

import bo.wilar.snake.controllers.StageController;
import bo.wilar.snake.enums.SnakeDirection;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.ScreenUtils;

public class SnakeGame extends ApplicationAdapter {

    private final Integer resolution;
    private StageController stageController;

    public SnakeGame(Integer resolution) {
        this.resolution = resolution;
    }

    @Override
    public void create() {
        this.stageController = new StageController(this.resolution);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.5f, 0.5f, 0, 1);
        verifyKeys();
        try {
            stageController.play();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void dispose() {
    }

    private void verifyKeys() {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            stageController.changeDirection(SnakeDirection.UP);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            stageController.changeDirection(SnakeDirection.DOWN);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            stageController.changeDirection(SnakeDirection.LEFT);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            stageController.changeDirection(SnakeDirection.RIGHT);
        }
    }

}