package bo.wilar.snake.models;

public class Stage {

    private final Boolean[][] matrix;

    public Stage(Integer dimension) {
        this.matrix = new Boolean[dimension][dimension];
    }

}
