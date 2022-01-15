package bo.wilar.snake.models;

import java.util.List;

public class Obstacle {

    private final List<Position> positions;

    public Obstacle(List<Position> positions) {
        this.positions = positions;
    }

    public List<Position> getPositions() {
        return positions;
    }
}