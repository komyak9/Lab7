package arguments;

import java.io.Serializable;

public class IdArgument extends Argument<Integer> implements Serializable {
    private ElementArgument element;

    public IdArgument(Integer id) {
        super(id);
    }

    public ElementArgument getElement() {
        return element;
    }

    public void setElement(ElementArgument element) {
        this.element = element;
    }
}