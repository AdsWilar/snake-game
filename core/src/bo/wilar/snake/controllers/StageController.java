package bo.wilar.snake.controllers;

import bo.wilar.snake.enums.SnakeDirection;
import bo.wilar.snake.models.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

public class StageController {

    private final Integer resolution;
    private final Boolean[][] snakeMatrix;
    private final Boolean[][] foodMatrix;
    private final Snake snake;
    private Coordinate foodCoordinate;
    private final ShapeRenderer snakeRenderer;
    private final ShapeRenderer foodRenderer;
    private final LinkedList<DirectionChange> directionChanges;


    public StageController(Integer resolution) {
        this.resolution = resolution;
        Integer dimension = 50;
        this.snakeMatrix = new Boolean[dimension][dimension];
        this.foodMatrix = new Boolean[dimension][dimension];
        initArrays();
        Float snakeSize = (float) (this.resolution / dimension);
        Float ordinate = this.resolution - snakeSize;
        Coordinate headCoordinate = new Coordinate(snakeSize, ordinate);

        Position headPosition = new Position(0, 1);
        this.snakeMatrix[headPosition.getRowIndex()][headPosition.getColumnIndex()] = true;
        SnakePiece snakeHead = new SnakePiece(headCoordinate, headPosition, SnakeDirection.RIGHT);
        Coordinate tailCoordinate = new Coordinate(headCoordinate.getAbscissa() - snakeSize, ordinate);
        Position tailPosition = new Position(0, headPosition.getColumnIndex() - 1);
        this.snakeMatrix[tailPosition.getRowIndex()][tailPosition.getColumnIndex()] = true;
        SnakePiece snakeTail = new SnakePiece(tailCoordinate, tailPosition, SnakeDirection.RIGHT);
        this.snake = new Snake(snakeSize, snakeHead, snakeTail);
        initFood();

        this.snakeRenderer = new ShapeRenderer();
        this.foodRenderer = new ShapeRenderer();
        this.directionChanges = new LinkedList<>();
    }

    private void initArrays() {
        for (Boolean[] matrix : this.snakeMatrix) {
            Arrays.fill(matrix, false);
        }
        for (Boolean[] matrix : this.foodMatrix) {
            Arrays.fill(matrix, false);
        }
    }

    private void initFood() {
        Random random = new Random();
        boolean hasFood = false;
        int number = this.foodMatrix.length - 1;
        while (!hasFood) {
            int row = random.nextInt(number);
            int column = random.nextInt(number);
            if (!this.snakeMatrix[row][column]) {
                this.foodMatrix[row][column] = true;
                this.foodCoordinate = getCoordinateByRowAndColumn(row, column);
                hasFood = true;
            }
        }
    }

    private Coordinate getCoordinateByRowAndColumn(Integer row, Integer column) {
        Float snakeSize = this.snake.getSize();
        Float abscissa = column * snakeSize;
        Float ordinate = (this.resolution - snakeSize) - (row * snakeSize);
        return new Coordinate(abscissa, ordinate);
    }

    public void play() throws InterruptedException {

        snakeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        Float snakeSize = this.snake.getSize();
        snakeRenderer.setColor(0.2f, 0.2f, 0.2f, 1);
//        Coordinate snakeHeadCoordinate = this.snake.getHead().getCoordinate();
//        snakeRenderer.rect(snakeHeadCoordinate.getAbscissa(), snakeHeadCoordinate.getOrdinate(), snakeSize, snakeSize);
//        Coordinate snakeTailCoordinate = this.snake.getTail().getCoordinate();
//        snakeRenderer.rect(snakeTailCoordinate.getAbscissa(), snakeTailCoordinate.getOrdinate(), snakeSize, snakeSize);
        drawSnake();
        snakeRenderer.end();

        foodRenderer.begin(ShapeRenderer.ShapeType.Filled);
        foodRenderer.setColor(1.0f, 0.2f, 0.2f, 1);
        foodRenderer.rect(this.foodCoordinate.getAbscissa(), this.foodCoordinate.getOrdinate(), snakeSize, snakeSize);
        foodRenderer.end();

        Thread.sleep(100);
        advanceSnake();
    }

    private void drawSnake() {
        for (int row = 0; row < this.snakeMatrix.length; row++) {
            for (int column = 0; column < this.snakeMatrix[row].length; column++) {
                if (this.snakeMatrix[row][column]) {
                    Coordinate coordinate = getCoordinateByRowAndColumn(row, column);
                    Float snakeSize = this.snake.getSize();
                    snakeRenderer.rect(coordinate.getAbscissa(), coordinate.getOrdinate(), snakeSize, snakeSize);
                }
            }
        }
    }

    private void advanceSnake() {
        SnakePiece snakeHead = this.snake.getHead();
        Coordinate snakeHeadCoordinate = snakeHead.getCoordinate();
        Position snakeHeadPosition = snakeHead.getPosition();
        Float snakeSize = this.snake.getSize();
        switch (snakeHead.getCurrentDirection()) {
            case UP:
                snakeHeadCoordinate.setOrdinate(snakeHeadCoordinate.getOrdinate() + snakeSize);
                snakeHeadPosition.setRowIndex(snakeHeadPosition.getRowIndex() - 1);
                break;
            case DOWN:
                snakeHeadCoordinate.setOrdinate(snakeHeadCoordinate.getOrdinate() - snakeSize);
                snakeHeadPosition.setRowIndex(snakeHeadPosition.getRowIndex() + 1);
                break;
            case LEFT:
                snakeHeadCoordinate.setAbscissa(snakeHeadCoordinate.getAbscissa() - snakeSize);
                snakeHeadPosition.setColumnIndex(snakeHeadPosition.getColumnIndex() - 1);
                break;
            case RIGHT:
                snakeHeadCoordinate.setAbscissa(snakeHeadCoordinate.getAbscissa() + snakeSize);
                snakeHeadPosition.setColumnIndex(snakeHeadPosition.getColumnIndex() + 1);
                break;
        }
        this.snakeMatrix[snakeHeadPosition.getRowIndex()][snakeHeadPosition.getColumnIndex()] = true;

        SnakePiece snakeTail = this.snake.getTail();
        Coordinate snakeTailCoordinate = snakeTail.getCoordinate();
        Position snakeTailPosition = snakeTail.getPosition();
        this.snakeMatrix[snakeTailPosition.getRowIndex()][snakeTailPosition.getColumnIndex()] = false;
        switch (snakeTail.getCurrentDirection()) {
            case UP:
                snakeTailCoordinate.setOrdinate(snakeTailCoordinate.getOrdinate() + snakeSize);
                snakeTailPosition.setRowIndex(snakeTailPosition.getRowIndex() - 1);
                break;
            case DOWN:
                snakeTailCoordinate.setOrdinate(snakeTailCoordinate.getOrdinate() - snakeSize);
                snakeTailPosition.setRowIndex(snakeTailPosition.getRowIndex() + 1);
                break;
            case LEFT:
                snakeTailCoordinate.setAbscissa(snakeTailCoordinate.getAbscissa() - snakeSize);
                snakeTailPosition.setColumnIndex(snakeTailPosition.getColumnIndex() - 1);
                break;
            case RIGHT:
                snakeTailCoordinate.setAbscissa(snakeTailCoordinate.getAbscissa() + snakeSize);
                snakeTailPosition.setColumnIndex(snakeTailPosition.getColumnIndex() + 1);
                break;
        }
        if (this.directionChanges.size() > 0) {
            DirectionChange directionChange = this.directionChanges.getFirst();
            Position directionChangePosition = directionChange.getPosition();
            if (snakeTailPosition.getRowIndex().equals(directionChangePosition.getRowIndex()) &&
                    snakeTailPosition.getColumnIndex().equals(directionChangePosition.getColumnIndex())) {
                snakeTail.setCurrentDirection(directionChange.getNewDirection());
                this.directionChanges.removeFirst();
            }
        }
        verifyFood();
    }

    public void changeDirection(SnakeDirection newDirection) {
        SnakePiece head = this.snake.getHead();
        SnakeDirection currentDirection = head.getCurrentDirection();
        if ((currentDirection.equals(newDirection)) ||
                (currentDirection.equals(SnakeDirection.UP) && newDirection.equals(SnakeDirection.DOWN)) ||
                (currentDirection.equals(SnakeDirection.DOWN) && newDirection.equals(SnakeDirection.UP)) ||
                (currentDirection.equals(SnakeDirection.RIGHT) && newDirection.equals(SnakeDirection.LEFT)) ||
                (currentDirection.equals(SnakeDirection.LEFT) && newDirection.equals(SnakeDirection.RIGHT))) {
            return;
        }

        head.setCurrentDirection(newDirection);
        Position headPosition = head.getPosition();
        this.directionChanges.add(new DirectionChange(
                newDirection, new Position(headPosition.getRowIndex(), headPosition.getColumnIndex())
        ));
    }

    private void verifyFood() {
        Position headPosition = this.snake.getHead().getPosition();
        if (this.foodMatrix[headPosition.getRowIndex()][headPosition.getColumnIndex()]) {
            this.foodMatrix[headPosition.getRowIndex()][headPosition.getColumnIndex()] = false;
            initFood();
            SnakePiece tail = this.snake.getTail();
            Coordinate snakeTailCoordinate = tail.getCoordinate();
            Position snakeTailPosition = tail.getPosition();
            Float snakeSize = this.snake.getSize();
            switch (tail.getCurrentDirection()) {
                case UP:
                    snakeTailCoordinate.setOrdinate(snakeTailCoordinate.getOrdinate() - snakeSize);
                    snakeTailPosition.setRowIndex(snakeTailPosition.getRowIndex() + 1);
                    break;
                case DOWN:
                    snakeTailCoordinate.setOrdinate(snakeTailCoordinate.getOrdinate() + snakeSize);
                    snakeTailPosition.setRowIndex(snakeTailPosition.getRowIndex() - 1);
                    break;
                case LEFT:
                    snakeTailCoordinate.setAbscissa(snakeTailCoordinate.getAbscissa() + snakeSize);
                    snakeTailPosition.setColumnIndex(snakeTailPosition.getColumnIndex() + 1);
                    break;
                case RIGHT:
                    snakeTailCoordinate.setAbscissa(snakeTailCoordinate.getAbscissa() - snakeSize);
                    snakeTailPosition.setColumnIndex(snakeTailPosition.getColumnIndex() - 1);
                    break;
            }
            this.snakeMatrix[snakeTailPosition.getRowIndex()][snakeTailPosition.getColumnIndex()] = true;
        }
    }

}