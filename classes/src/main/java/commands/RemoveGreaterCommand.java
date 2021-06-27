package commands;

import arguments.Argument;
import content.Worker;

import java.io.Serializable;
import java.util.LinkedList;

public class RemoveGreaterCommand extends Command<Worker> implements Serializable {
    public RemoveGreaterCommand(Argument<Worker> argument) {
        super(argument);
    }

    @Override
    public void execute(LinkedList<Worker> collection) {
        try {
            checkAuthorization(user.isAuthorized());
            int count_before = collection.size();
            //if (collection.stream().noneMatch(worker -> argument.getArgument().compareTo(worker) < 0))
            //    throw new Exception("There is no workers greater. Nothing to remove.");

            dbInteractionCommands.removeGreater(argument.getArgument().getName());
            dbInteractionCommands.updateCollection(collection);
            int count_after = collection.size();
            if (count_after < count_before)
                this.setMessage("The elements are removed.");
            else
                this.setMessage("There is no workers greater. Nothing to remove.");
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }
}
