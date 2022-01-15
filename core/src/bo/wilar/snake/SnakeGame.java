package bo.wilar.snake;

import bo.wilar.snake.controllers.SnakeController;
import bo.wilar.snake.enums.SnakeDirection;
import bo.wilar.snake.exceptions.CollisionException;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.ScreenUtils;

public class SnakeGame extends ApplicationAdapter {

    private final Integer resolution;
    private SnakeController snakeController;

    public SnakeGame(Integer resolution) {
        this.resolution = resolution;
    }

    @Override
    public void create() {
        this.snakeController = new SnakeController(this.resolution);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.5f, 0.5f, 0, 1);
        verifyKeys();
        try {
            snakeController.play();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (CollisionException e) {
            // DO SOMETHING
            System.out.println("Game Over");
        }
    }


    @Override
    public void dispose() {
    }

    private void verifyKeys() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            snakeController.changeDirection(SnakeDirection.UP);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            snakeController.changeDirection(SnakeDirection.DOWN);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            snakeController.changeDirection(SnakeDirection.LEFT);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            snakeController.changeDirection(SnakeDirection.RIGHT);
        }
    }

}