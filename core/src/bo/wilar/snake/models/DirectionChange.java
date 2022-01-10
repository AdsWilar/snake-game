package bo.wilar.snake.models;

import bo.wilar.snake.enums.SnakeDirection;

public class DirectionChange {

    private SnakeDirection newDirection;
    private Position position;

    public DirectionChange(SnakeDirection newDirection, Position position) {
        this.newDirection = newDirection;
        this.position = position;
    }

    public SnakeDirection getNewDirection() {
        return newDirection;
    }

    public void setNewDirection(SnakeDirection newDirection) {
        this.newDirection = newDirection;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

}