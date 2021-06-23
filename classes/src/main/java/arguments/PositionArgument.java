package arguments;

import content.Position;

import java.io.Serializable;

public class PositionArgument extends Argument<Position> implements Serializable {
    public PositionArgument(Position position) {
        super(position);
    }
}