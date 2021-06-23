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

            if (collection.stream().noneMatch(worker -> worker.getSalary() == argument.getArgument()))
                throw new Exception("There is no worker with such salary. Nothing to remove.");

            dbInteractionCommands.removeSalary(argument.getArgument());
            Worker worker = collection.stream().filter(worker1 -> worker1.getSalary() == argument.getArgument()).findFirst().get();
            collection.remove(worker);
            idGenerator.remove(worker.getId());
            this.setMessage("The elements are removed.");
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }
}
