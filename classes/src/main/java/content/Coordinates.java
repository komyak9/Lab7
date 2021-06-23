package content;

import java.io.Serializable;

/**
 * Класс для хранения места нахождения работника.
 *
 * @autor komyak9
 */
public class Coordinates implements Serializable {
    private long x; //Значение поля должно быть больше -690
    private int y; //Значение поля должно быть больше -247

    public Coordinates(long x, int y) throws Exception {
        setX(x);
        setY(y);
    }

    public long getX() {
        return x;
    }

    public void setX(long x) throws Exception {
        if (x > -690)
            this.x = x;
        else
            throw new Exception("Wrong value. Coordinate \"x\" must be > -690.");
    }

    public int getY() {
        return y;
    }

    public void setY(int y) throws Exception {
        if (y > -247)
            this.y = y;
        else
            throw new Exception("Wrong value. Coordinate \"y\" must be > -247.");
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
