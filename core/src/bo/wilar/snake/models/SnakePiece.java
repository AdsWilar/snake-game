package bo.wilar.snake.models;

import bo.wilar.snake.enums.SnakeDirection;

public class SnakePiece {

    private Coordinate coordinate;
    private Position position;
    private SnakeDirection currentDirection;

    public SnakePiece(Coordinate coordinate, Position position, SnakeDirection currentDirection) {
        this.coordinate = coordinate;
        this.position = position;
        this.currentDirection = currentDirection;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
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