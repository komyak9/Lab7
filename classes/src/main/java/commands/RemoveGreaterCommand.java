package commands;

import arguments.Argument;
import content.Worker;
import db.DBInteraction;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class RemoveGreaterCommand extends Command<Worker> implements Serializable {
    public RemoveGreaterCommand(Argument<Worker> argument) {
        super(argument);
    }

    @Override
    public void execute(LinkedList<Worker> collection, DBInteraction dbInteraction) {
        try {
            if (collection.stream().noneMatch(worker -> argument.getArgument().compareTo(worker) < 0))
                throw new Exception("There is no workers greater. Nothing to remove.");

            dbInteraction.removeGreater(argument.getArgument().getName());
            Collection<?> toRemove = collection.stream().filter(workerToUpdate -> workerToUpdate.compareTo(argument.getArgument()) > 0).collect(Collectors.toList());
            collection.removeAll(toRemove);
            idGenerator.clear();   //////////////////////////////////////////////////////
            collection.forEach(worker -> idGenerator.addId(worker.getId()));
            this.setMessage("The elements are removed.");
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }
}
