package bo.wilar.snake.models;

public class Snake {

    private final Float size;
    private SnakePiece head;
    private SnakePiece tail;

    public Snake(Float size, SnakePiece head, SnakePiece tail) {
        this.size = size;
        this.head = head;
        this.tail = tail;
    }

    public Float getSize() {
        return size;
    }

    public SnakePiece getHead() {
        return head;
    }

    public void setHead(SnakePiece head) {
        this.head = head;
    }

    public SnakePiece getTail() {
        return tail;
    }

    public void setTail(SnakePiece tail) {
        this.tail = tail;
    }

}
