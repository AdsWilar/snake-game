package bo.wilar.snake.models;

import java.util.LinkedList;

public class Snake {

    private final Float size;
    private final SnakeHead head;
    private final LinkedList<Position> body;

    public Snake(Float size, SnakeHead head, LinkedList<Position> body) {
        this.size = size;
        this.head = head;
        this.body = body;
    }

    public Float getSize() {
        return size;
    }

    public SnakeHead getHead() {
        return head;
    }

    public LinkedList<Position> getBody() {
        return body;
    }

}