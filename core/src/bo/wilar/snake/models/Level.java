package bo.wilar.snake.models;

import java.util.List;

public class Level {

    private final Integer index;
    private final Integer foodRequiredAmount;
    private final List<Obstacle> obstacles;

    public Level(Integer index, Integer foodRequiredAmount, List<Obstacle> obstacles) {
        this.index = index;
        this.foodRequiredAmount = foodRequiredAmount;
        this.obstacles = obstacles;
    }

    public Integer getFoodRequiredAmount() {
        return foodRequiredAmount;
    }

    public Integer getIndex() {
        return index;
    }

    public List<Obstacle> getObstacles() {
        return obstacles;
    }

}