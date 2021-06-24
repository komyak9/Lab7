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
            int count_before = collection.size();
            //if (collection.stream().noneMatch(worker -> worker.getPosition().equals(argument.getArgument())))
            //    throw new Exception("There is no worker with such position. Nothing to remove.");

            dbInteractionCommands.removePosition(argument.getArgument());
            dbInteractionCommands.updateCollection(collection);
            int count_after = collection.size();
            if (count_after < count_before)
                this.setMessage("The elements are removed.");
            else
                this.setMessage("There is no worker with such salary. Nothing to remove.");
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }
}
