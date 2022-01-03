package bo.wilar.snake;

import bo.wilar.snake.controllers.SnakeController;
import bo.wilar.snake.enums.SnakeCommand;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

public class SnakeGame extends ApplicationAdapter {

//    ShapeRenderer shapeRenderer;
//    Float xAxis;
//    Float yAxis;
    private SnakeController snakeController;

    @Override
    public void create() {

//        shapeRenderer = new ShapeRenderer();
//        xAxis = 650f;
//        yAxis = 650f;
        this.snakeController = new SnakeController();
    }

    @Override
    public void render() {
//        System.out.println("1  " + Gdx.graphics.getWidth());
//        System.out.println("2  " + Gdx.graphics.getHeight());
//
        ScreenUtils.clear(0.5f, 0.5f, 0, 1);
        verifyKeys();
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//        shapeRenderer.setColor(0.2f, 0.2f, 0.2f, 1);
//        shapeRenderer.rect(xAxis, yAxis, 50, 50);
//        shapeRenderer.end();

        snakeController.play();


    }


    @Override
    public void dispose() {

    }

    private void verifyKeys() {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            snakeController.setSnakeCommand(SnakeCommand.UP);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            snakeController.setSnakeCommand(SnakeCommand.DOWN);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            snakeController.setSnakeCommand(SnakeCommand.LEFT);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            snakeController.setSnakeCommand(SnakeCommand.RIGHT);
        }

    }
}
