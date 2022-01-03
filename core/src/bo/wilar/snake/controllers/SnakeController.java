package bo.wilar.snake.controllers;

import bo.wilar.snake.enums.SnakeCommand;
import bo.wilar.snake.models.Snake;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class SnakeController {

    private final ShapeRenderer shapeRenderer;
    private final Snake snake;
    private SnakeCommand snakeCommand;

    public SnakeController() {
        this.shapeRenderer = new ShapeRenderer();
        this.snake = new Snake(100f, 100f, 50f, 50f);
        this.snakeCommand = SnakeCommand.RIGHT;
    }

    public void play() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.2f, 0.2f, 0.2f, 1);
        shapeRenderer.rect(this.snake.getX(), this.snake.getY(), this.snake.getWidth(), this.snake.getHeight());
        shapeRenderer.end();
        advanceSnake();
    }

    private void advanceSnake() {
        switch (this.snakeCommand) {
            case UP:
                this.snake.setY(this.snake.getY() + 1);
                break;
            case DOWN:
                this.snake.setY(this.snake.getY() - 1);
                break;
            case LEFT:
                this.snake.setX(this.snake.getX() - 1);
                break;
            case RIGHT:
                this.snake.setX(this.snake.getX() + 1);
                break;
        }
    }

    public void setSnakeCommand(SnakeCommand snakeCommand) {
        this.snakeCommand = snakeCommand;
    }
}
