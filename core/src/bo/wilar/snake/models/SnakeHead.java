package bo.wilar.snake.models;

import bo.wilar.snake.enums.SnakeDirection;

public class SnakeHead {

    private Position position;
    private SnakeDirection currentDirection;

    public SnakeHead(Position position, SnakeDirection currentDirection) {
        this.position = position;
        this.currentDirection = currentDirection;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public SnakeDirection getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(SnakeDirection currentDirection) {
        this.currentDirection = currentDirection;
    }

}