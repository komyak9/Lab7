package arguments;

import java.io.Serializable;

public class SalaryArgument extends Argument<Float> implements Serializable {
    public SalaryArgument(Float salary) {
        super(salary);
    }
}