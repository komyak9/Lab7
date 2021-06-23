package commands;

import arguments.Argument;
import content.Worker;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class RemoveByIdCommand extends Command<Integer> implements Serializable {
    public RemoveByIdCommand(Argument<Integer> argument) {
        super(argument);
    }

    @Override
    public void execute(LinkedList<Worker> collection) {
        try {
            checkAuthorization(user.isAuthorized());

            dbInteractionCommands.removeId(argument.getArgument());

            for (Worker worker : collection)
                System.out.println(worker);

            if (collection.stream().noneMatch(worker -> worker.getId() == argument.getArgument()))
                throw new Exception("There is no worker with such id. Nothing to remove.");

            Collection<?> toRemove = collection.stream().filter(workerToUpdate -> workerToUpdate.getId() == argument.getArgument()).collect(Collectors.toList());
            collection.removeAll(toRemove);
            idGenerator.remove(argument.getArgument());
            this.setMessage("The elements are removed.");
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }
}
