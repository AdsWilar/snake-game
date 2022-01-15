package bo.wilar.snake.models;

public class Coordinate {

    private Float abscissa;
    private Float ordinate;

    public Coordinate(Float abscissa, Float ordinate) {
        this.abscissa = abscissa;
        this.ordinate = ordinate;
    }

    public Float getAbscissa() {
        return abscissa;
    }

    public void setAbscissa(Float abscissa) {
        this.abscissa = abscissa;
    }

    public Float getOrdinate() {
        return ordinate;
    }

    public void setOrdinate(Float ordinate) {
        this.ordinate = ordinate;
    }

}