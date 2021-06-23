package arguments;

import java.io.Serializable;

abstract public class Argument<T> implements Serializable {
    private final T t;

    public Argument(T t) {
        this.t = t;
    }


    public T getArgument() {
        return t;
    }

    @Override
    public String toString() {
        return "Argument{" +
                "t=" + t +
                '}';
    }
}