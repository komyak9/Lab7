package content;

import java.io.Serializable;

/**
 * Класс для хранения места нахождения компании/рабочего места.
 *
 * @autor komyak9
 */
public class Location implements Serializable {
    private Integer x; //Поле не может быть null
    private int y;
    private String name; //Длина строки не должна быть больше 429, Поле может быть null

    public Location(Integer x, int y, String name) throws Exception {
        if (x == null)
            throw new Exception("Error. \"x\" can't be null.");

        setX(x);
        setY(y);
        setName(name);
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws Exception {
        if (name == null || name.length() <= 429) {
            this.name = name;
        } else {
            throw new Exception("Wrong length. Name length must me <= 429.");
        }
    }

    @Override
    public String toString() {
        return "Location{" +
                "x=" + x +
                ", y=" + y +
                ", name=" + name +
                '}';
    }
}

