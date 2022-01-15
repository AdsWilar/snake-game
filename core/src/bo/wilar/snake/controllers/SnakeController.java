package bo.wilar.snake.controllers;

import bo.wilar.snake.enums.SnakeDirection;
import bo.wilar.snake.exceptions.CollisionException;
import bo.wilar.snake.models.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.*;

public class SnakeController {

    private final Integer resolution;
    private Snake snake;
    private Position food;
    private Integer levelIndex;
    private Level currentLevel;
    private Integer foodsAmount;
    private ShapeRenderer snakeRenderer;
    private ShapeRenderer foodRenderer;
    private ShapeRenderer obstaclesRenderer;

    private final Random random = new Random();
    private static final Integer LEVELS_AMOUNT = 10;
    private static final Integer FOOD_REQUIRED_AMOUNT = 3;
    private static final Integer DIMENSION = 50;

    public SnakeController(Integer resolution) {
        this.resolution = resolution;
        initGame();
    }

    private void initGame() {
        this.snake = initSnake();
        initFood();
        this.levelIndex = 0;
        initLevel();
        this.foodsAmount = 0;

        this.snakeRenderer = new ShapeRenderer();
        this.foodRenderer = new ShapeRenderer();
        this.obstaclesRenderer = new ShapeRenderer();
    }

    private Snake initSnake() {
        Float snakeSize = (float) (this.resolution / DIMENSION);
        Position initialHeadPosition = getRandomPosition();
        SnakeDirection initialDirection = getInitialDirection();
        SnakeHead snakeHead = new SnakeHead(initialHeadPosition, initialDirection);
        Position bodyInitialPosition = getBodyInitialPosition(snakeHead);
        LinkedList<Position> body = new LinkedList<>();
        body.add(bodyInitialPosition);
        return new Snake(snakeSize, snakeHead, body);
    }

    private Position getRandomPosition() {
        Integer row = this.random.nextInt(DIMENSION - 1);
        Integer column = this.random.nextInt(DIMENSION - 1);
        return new Position(row, column);
    }

    private SnakeDirection getInitialDirection() {
        List<SnakeDirection> snakeDirections = Collections.unmodifiableList(Arrays.asList(SnakeDirection.values()));
        return snakeDirections.get(this.random.nextInt(snakeDirections.size()));
    }

    private Position getBodyInitialPosition(SnakeHead snakeHead) {
        Integer row;
        Integer column;
        Position snakeHeadPosition = snakeHead.getPosition();
        Integer snakeHeadRow = snakeHeadPosition.getRowIndex();
        Integer snakeHeadColumn = snakeHeadPosition.getColumnIndex();
        switch (snakeHead.getCurrentDirection()) {
            case UP:
                row = snakeHeadRow + 1;
                column = snakeHeadColumn;
                break;
            case DOWN:
                row = snakeHeadRow - 1;
                column = snakeHeadColumn;
                break;
            case RIGHT:
                row = snakeHeadRow;
                column = snakeHeadColumn - 1;
                break;
            default: // LEFT
                row = snakeHeadRow;
                column = snakeHeadColumn + 1;
        }
        return new Position(row, column);
    }

    private void initFood() {
        boolean hasFood = false;
        while (!hasFood) {
            Position position = getRandomPosition();
            Integer rowPosition = position.getRowIndex();
            Integer columnPosition = position.getColumnIndex();
            Position snakeHeadPosition = this.snake.getHead().getPosition();
            Integer snakeHeadRow = snakeHeadPosition.getRowIndex();
            Integer snakeHeadColumn = snakeHeadPosition.getColumnIndex();
            if (!rowPosition.equals(snakeHeadRow) || !columnPosition.equals(snakeHeadColumn)) {
                boolean isBodyPosition = false;
                for (Position bodyPosition : this.snake.getBody()) {
                    if (rowPosition.equals(bodyPosition.getRowIndex()) &&
                            columnPosition.equals(bodyPosition.getColumnIndex())) {
                        isBodyPosition = true;
                        break;
                    }
                }
                if (!isBodyPosition) {
                    this.food = position;
                    hasFood = true;
                }
            }
        }
    }

    private void initLevel() {
        List<Obstacle> obstacles = new ArrayList<>();
        int obstaclesAmount = this.levelIndex * 2;
        for (int obstacleNumber = 1; obstacleNumber <= obstaclesAmount; obstacleNumber++) {
            boolean obstacleIsComplete = false;
            while (!obstacleIsComplete) {
                Position position = getRandomPosition();
                if (positionIsOccupied(position)) {
                    continue;
                }
                List<Position> positions = new ArrayList<>();
                positions.add(position);
                boolean anyPositionIdOccupied = false;
                for (int obstaclePiece = 0; obstaclePiece < 4; obstaclePiece++) {
                    Integer row = positions.get(positions.size() - 1).getRowIndex();
                    Integer column = positions.get(positions.size() - 1).getColumnIndex();
                    if (obstacleNumber % 2 == 0) {
                        column++;
                    } else {
                        row--;
                    }
                    Position nextPosition = new Position(row, column);
                    if (positionIsOccupied(nextPosition)) {
                        anyPositionIdOccupied = true;
                        break;
                    }
                    positions.add(nextPosition);
                }
                if (!anyPositionIdOccupied) {
                    obstacles.add(new Obstacle(positions));
                    obstacleIsComplete = true;
                }
            }
        }
        this.currentLevel = new Level(this.levelIndex + 1, FOOD_REQUIRED_AMOUNT, obstacles);
    }

    private Boolean positionIsOccupied(Position position) {
        Integer rowIndex = position.getRowIndex();
        Integer columnIndex = position.getColumnIndex();
        Position headSnakePosition = this.snake.getHead().getPosition();
        if (rowIndex.equals(headSnakePosition.getRowIndex()) &&
                columnIndex.equals(headSnakePosition.getColumnIndex())) {
            return true;
        }
        for (Position bodyPosition : this.snake.getBody()) {
            if (rowIndex.equals(bodyPosition.getRowIndex()) &&
                    columnIndex.equals(bodyPosition.getColumnIndex())) {
                return true;
            }
        }
        return rowIndex.equals(this.food.getRowIndex()) && columnIndex.equals(this.food.getColumnIndex());
    }

    private Coordinate getCoordinateByRowAndColumn(Integer row, Integer column) {
        Float snakeSize = this.snake.getSize();
        Float abscissa = column * snakeSize;
        Float ordinate = (this.resolution - snakeSize) - (row * snakeSize);
        return new Coordinate(abscissa, ordinate);
    }

    public void play() throws InterruptedException, CollisionException {
        drawSnake();
        drawFood();
        drawObstacles();
        Thread.sleep(100);
        advanceSnake();
        if (this.currentLevel.getIndex() > LEVELS_AMOUNT) {
            initGame();
            throw new CollisionException("Ya pasó todos los niveles.");
        }
    }

    private void drawSnake() {
        snakeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        snakeRenderer.setColor(0.2f, 0.2f, 0.2f, 1);
        drawSnakeHead();
        drawSnakeBody();
        snakeRenderer.end();
    }

    private void drawSnakeHead() {
        Position snakeHeadPosition = this.snake.getHead().getPosition();
        Coordinate coordinate =
                getCoordinateByRowAndColumn(snakeHeadPosition.getRowIndex(), snakeHeadPosition.getColumnIndex());
        Float snakeSize = this.snake.getSize();
        snakeRenderer.rect(coordinate.getAbscissa(), coordinate.getOrdinate(), snakeSize, snakeSize);
    }

    private void drawSnakeBody() {
        for (Position bodyPosition : this.snake.getBody()) {
            Coordinate coordinate =
                    getCoordinateByRowAndColumn(bodyPosition.getRowIndex(), bodyPosition.getColumnIndex());
            Float snakeSize = this.snake.getSize();
            snakeRenderer.rect(coordinate.getAbscissa(), coordinate.getOrdinate(), snakeSize, snakeSize);
        }
    }

    private void drawFood() {
        foodRenderer.begin(ShapeRenderer.ShapeType.Filled);
        foodRenderer.setColor(1.0f, 0.2f, 0.2f, 1);
        Coordinate foodCoordinate = getCoordinateByRowAndColumn(this.food.getRowIndex(), this.food.getColumnIndex());
        Float snakeSize = this.snake.getSize();
        foodRenderer.rect(foodCoordinate.getAbscissa(), foodCoordinate.getOrdinate(), snakeSize, snakeSize);
        foodRenderer.end();
    }

    private void drawObstacles() {
        obstaclesRenderer.begin(ShapeRenderer.ShapeType.Filled);
        obstaclesRenderer.setColor(0.2f, 0.5f, 0.2f, 1);
        for (Obstacle obstacle : this.currentLevel.getObstacles()) {
            for (Position position : obstacle.getPositions()) {
                Coordinate coordinate = getCoordinateByRowAndColumn(position.getRowIndex(), position.getColumnIndex());
                Float snakeSize = this.snake.getSize();
                obstaclesRenderer.rect(coordinate.getAbscissa(), coordinate.getOrdinate(), snakeSize, snakeSize);
            }
        }
        obstaclesRenderer.end();
    }

    private void advanceSnake() throws CollisionException {
        SnakeHead snakeHead = this.snake.getHead();
        Position snakeHeadCurrentPosition = snakeHead.getPosition();
        Integer currentHeadRowPosition = snakeHeadCurrentPosition.getRowIndex();
        Integer currentHeadColumnPosition = snakeHeadCurrentPosition.getColumnIndex();
        Integer newHeadRowPosition;
        Integer newHeadColumnPosition;
        switch (snakeHead.getCurrentDirection()) {
            case UP:
                newHeadRowPosition = currentHeadRowPosition - 1;
                newHeadColumnPosition = currentHeadColumnPosition;
                break;
            case DOWN:
                newHeadRowPosition = currentHeadRowPosition + 1;
                newHeadColumnPosition = currentHeadColumnPosition;
                break;
            case RIGHT:
                newHeadRowPosition = currentHeadRowPosition;
                newHeadColumnPosition = currentHeadColumnPosition + 1;
                break;
            default: // LEFT
                newHeadRowPosition = currentHeadRowPosition;
                newHeadColumnPosition = currentHeadColumnPosition - 1;
                break;
        }
        Position newHeadPosition = new Position(newHeadRowPosition, newHeadColumnPosition);
        verifyCollision(newHeadPosition);
        this.snake.getBody().addFirst(snakeHeadCurrentPosition);
        snakeHead.setPosition(newHeadPosition);
        verifyFood();
    }

    private void verifyCollision(Position newHeadPosition) throws CollisionException {
        Integer newHeadPositionRow = newHeadPosition.getRowIndex();
        Integer newHeadPositionColumn = newHeadPosition.getColumnIndex();
        if (newHeadPositionRow < 0 || newHeadPositionRow >= DIMENSION ||
                newHeadPositionColumn < 0 || newHeadPositionColumn >= DIMENSION) {
            Integer halfDimension = DIMENSION / 2;
            switch (this.snake.getHead().getCurrentDirection()) {
                case UP:
                    SnakeDirection newDirectionUp;
                    newHeadPosition.setRowIndex(newHeadPositionRow + 1);
                    if (newHeadPositionColumn < halfDimension) {
                        newDirectionUp = SnakeDirection.RIGHT;
                        newHeadPosition.setColumnIndex(newHeadPositionColumn + 1);
                    } else {
                        newDirectionUp = SnakeDirection.LEFT;
                        newHeadPosition.setColumnIndex(newHeadPositionColumn - 1);
                    }
                    this.snake.getHead().setCurrentDirection(newDirectionUp);
                    break;
                case DOWN:
                    SnakeDirection newDirectionDown;
                    newHeadPosition.setRowIndex(newHeadPositionRow - 1);
                    if (newHeadPositionColumn < halfDimension) {
                        newDirectionDown = SnakeDirection.RIGHT;
                        newHeadPosition.setColumnIndex(newHeadPositionColumn + 1);
                    } else {
                        newDirectionDown = SnakeDirection.LEFT;
                        newHeadPosition.setColumnIndex(newHeadPositionColumn - 1);
                    }
                    this.snake.getHead().setCurrentDirection(newDirectionDown);
                    break;
                case RIGHT:
                    SnakeDirection newDirectionRight;
                    if (newHeadPositionRow < halfDimension) {
                        newDirectionRight = SnakeDirection.DOWN;
                        newHeadPosition.setRowIndex(newHeadPositionRow + 1);
                    } else {
                        newDirectionRight = SnakeDirection.UP;
                        newHeadPosition.setRowIndex(newHeadPositionRow - 1);
                    }
                    newHeadPosition.setColumnIndex(newHeadPositionColumn - 1);
                    this.snake.getHead().setCurrentDirection(newDirectionRight);
                    break;
                default:
                    SnakeDirection newDirectionLeft;
                    if (newHeadPositionRow < halfDimension) {
                        newDirectionLeft = SnakeDirection.DOWN;
                        newHeadPosition.setRowIndex(newHeadPositionRow + 1);
                    } else {
                        newDirectionLeft = SnakeDirection.UP;
                        newHeadPosition.setRowIndex(newHeadPositionRow - 1);
                    }
                    newHeadPosition.setColumnIndex(newHeadPositionColumn + 1);
                    this.snake.getHead().setCurrentDirection(newDirectionLeft);
            }
        }
        for (Position bodyPosition : this.snake.getBody()) {
            if (newHeadPositionRow.equals(bodyPosition.getRowIndex()) &&
                    newHeadPositionColumn.equals(bodyPosition.getColumnIndex())) {
                throw new CollisionException("La serpiente se estrelló con su propio cuerpo.");
            }
        }
        for (Obstacle obstacle : this.currentLevel.getObstacles()) {
            for (Position position : obstacle.getPositions()) {
                if (newHeadPositionRow.equals(position.getRowIndex()) &&
                        newHeadPositionColumn.equals(position.getColumnIndex())) {
                    throw new CollisionException("La serpiente se estrelló contra un obstáculo.");
                }
            }
        }
    }

    private void verifyFood() {
        Position headPosition = this.snake.getHead().getPosition();
        if (!headPosition.getRowIndex().equals(this.food.getRowIndex()) ||
                !headPosition.getColumnIndex().equals(this.food.getColumnIndex())) {
            this.snake.getBody().removeLast();
            return;
        }
        this.foodsAmount++;
        initFood();
        if (this.foodsAmount >= this.currentLevel.getFoodRequiredAmount()) {
            this.levelIndex++;
            this.foodsAmount = 0;
            initLevel();
        }
    }

    public void changeDirection(SnakeDirection newDirection) {
        SnakeHead head = this.snake.getHead();
        SnakeDirection currentDirection = head.getCurrentDirection();
        if ((currentDirection.equals(newDirection)) ||
                (currentDirection.equals(SnakeDirection.UP) && newDirection.equals(SnakeDirection.DOWN)) ||
                (currentDirection.equals(SnakeDirection.DOWN) && newDirection.equals(SnakeDirection.UP)) ||
                (currentDirection.equals(SnakeDirection.RIGHT) && newDirection.equals(SnakeDirection.LEFT)) ||
                (currentDirection.equals(SnakeDirection.LEFT) && newDirection.equals(SnakeDirection.RIGHT))) {
            return;
        }
        head.setCurrentDirection(newDirection);
    }

}