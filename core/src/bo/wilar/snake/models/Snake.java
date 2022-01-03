package bo.wilar.snake.models;

public class Snake {

    private Float x;
    private Float y;
    private final Float height;
    private final Float width;

    public Snake(Float x, Float y, Float height, Float width) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }

    public Float getX() {
        return x;
    }

    public Float getY() {
        return y;
    }

    public Float getHeight() {
        return height;
    }

    public Float getWidth() {
        return width;
    }

    public void setX(Float x) {
        this.x = x;
    }

    public void setY(Float y) {
        this.y = y;
    }
}
