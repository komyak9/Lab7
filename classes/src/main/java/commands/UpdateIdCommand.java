package commands;

import arguments.Argument;
import arguments.IdArgument;
import content.Worker;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class UpdateIdCommand extends Command<Integer> implements Serializable {
    public UpdateIdCommand(Argument<Integer> argument) {
        super(argument);
    }

    @Override
    public void execute(LinkedList<Worker> collection) {
        try {
            checkAuthorization(user.isAuthorized());

            dbInteractionCommands.updateElement(0, ((IdArgument) argument).getElement().getArgument().getName(),
                    (int) ((IdArgument) argument).getElement().getArgument().getCoordinates().getX(),
                    ((IdArgument) argument).getElement().getArgument().getCoordinates().getY(),
                    ((IdArgument) argument).getElement().getArgument().getCreationDate(),
                    ((IdArgument) argument).getElement().getArgument().getSalary(),
                    ((IdArgument) argument).getElement().getArgument().getStartDate(),
                    ((IdArgument) argument).getElement().getArgument().getEndDate(),
                    ((IdArgument) argument).getElement().getArgument().getPosition(),
                    ((IdArgument) argument).getElement().getArgument().getOrganization().getAnnualTurnover(),
                    ((IdArgument) argument).getElement().getArgument().getOrganization().getType(),
                    ((IdArgument) argument).getElement().getArgument().getOrganization().getOfficialAddress().getZipCode(),
                    ((IdArgument) argument).getElement().getArgument().getOrganization().getOfficialAddress().getTown().getX(),
                    ((IdArgument) argument).getElement().getArgument().getOrganization().getOfficialAddress().getTown().getY(),
                    ((IdArgument) argument).getElement().getArgument().getOrganization().getOfficialAddress().getTown().getName());

            if (collection.stream().noneMatch(worker -> worker.getId() == argument.getArgument()))
                throw new Exception("There is no worker with such id. Nothing to remove.");

            Worker worker = new Worker(argument.getArgument(), ((IdArgument) argument).getElement().getArgument().getCreationDate(),
                    ((IdArgument) argument).getElement().getArgument().getName(),
                    ((IdArgument) argument).getElement().getArgument().getCoordinates(),
                    ((IdArgument) argument).getElement().getArgument().getSalary(),
                    ((IdArgument) argument).getElement().getArgument().getStartDate(),
                    ((IdArgument) argument).getElement().getArgument().getEndDate(),
                    ((IdArgument) argument).getElement().getArgument().getPosition(),
                    ((IdArgument) argument).getElement().getArgument().getOrganization());

            Collection<?> toRemove = collection.stream().filter(workerToUpdate -> workerToUpdate.getId() == argument.getArgument()).collect(Collectors.toList());
            collection.removeAll(toRemove);
            collection.add(worker);
            this.setMessage("The worker is replaced successfully.");
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
}
