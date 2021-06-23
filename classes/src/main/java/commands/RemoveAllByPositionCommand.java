package commands;

import arguments.Argument;
import content.Position;
import content.Worker;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class RemoveAllByPositionCommand extends Command<Position> implements Serializable {
    public RemoveAllByPositionCommand(Argument<Position> argument) {
        super(argument);
    }

    @Override
    public void execute(LinkedList<Worker> collection) {
        try {
            checkAuthorization(user.isAuthorized());

            dbInteractionCommands.removePosition(argument.getArgument());

            if (collection.stream().noneMatch(worker -> worker.getPosition().equals(argument.getArgument())))
                throw new Exception("There is no worker with such position. Nothing to remove.");

            Collection<?> toRemove = collection.stream().filter(worker -> worker.getPosition() == argument.
                    getArgument()).collect(Collectors.toList());
            collection.removeAll(toRemove);
            idGenerator.clear();
            collection.forEach(worker -> idGenerator.addId(worker.getId()));
            this.setMessage("The elements are removed.");
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }
}
