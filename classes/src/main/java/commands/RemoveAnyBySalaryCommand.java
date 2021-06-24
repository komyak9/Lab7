package commands;

import arguments.Argument;
import content.Worker;

import java.io.Serializable;
import java.util.LinkedList;

public class RemoveAnyBySalaryCommand extends Command<Float> implements Serializable {
    public RemoveAnyBySalaryCommand(Argument<Float> argument) {
        super(argument);
    }

    @Override
    public void execute(LinkedList<Worker> collection) {
        try {
            checkAuthorization(user.isAuthorized());
            int count_before = collection.size();
            //if (collection.stream().noneMatch(worker -> worker.getSalary() == argument.getArgument()))
            //    throw new Exception("There is no worker with such salary. Nothing to remove.");

            dbInteractionCommands.removeSalary(argument.getArgument());
            dbInteractionCommands.updateCollection(collection);
            int count_after = collection.size();
            if (count_after < count_before)
                this.setMessage("The elements are removed.");
            else
                this.setMessage("There is no worker with such salary. Nothing to remove.");
            this.setMessage("The elements are removed.");
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }
}
