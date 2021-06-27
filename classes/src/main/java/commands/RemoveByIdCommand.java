package commands;

import arguments.Argument;
import content.Worker;

import java.io.Serializable;
import java.util.LinkedList;

public class RemoveByIdCommand extends Command<Integer> implements Serializable {
    public RemoveByIdCommand(Argument<Integer> argument) {
        super(argument);
    }

    @Override
    public void execute(LinkedList<Worker> collection) {
        try {
            checkAuthorization(user.isAuthorized());
            int count_before = collection.size();
            if (collection.stream().noneMatch(worker -> worker.getId() == argument.getArgument()))
                throw new Exception("There is no worker with such id. Nothing to remove.");

            dbInteractionCommands.removeId(argument.getArgument());
            dbInteractionCommands.updateCollection(collection);
            int count_after = collection.size();
            if (count_after < count_before)
                this.setMessage("The elements are removed.");
            else
                this.setMessage("There is no worker with such id. Nothing to remove.");
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }
}
