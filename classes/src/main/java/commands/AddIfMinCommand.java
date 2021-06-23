package commands;

import arguments.Argument;
import content.Worker;

import java.io.Serializable;
import java.util.Comparator;
import java.util.LinkedList;

public class AddIfMinCommand extends Command<Worker> implements Serializable {
    public AddIfMinCommand(Argument<Worker> argument) {
        super(argument);
    }

    @Override
    public void execute(LinkedList<Worker> collection) {
        try {
            checkAuthorization(user.isAuthorized());

            dbInteractionCommands.addMinElement(dbInteractionCommands.getCreatorName(), 0, argument.getArgument().getName(),
                    (int) argument.getArgument().getCoordinates().getX(), argument.getArgument().getCoordinates().getY(),
                    argument.getArgument().getCreationDate(), argument.getArgument().getSalary(),
                    argument.getArgument().getStartDate(), argument.getArgument().getEndDate(),
                    argument.getArgument().getPosition(), argument.getArgument().getOrganization().getAnnualTurnover(),
                    argument.getArgument().getOrganization().getType(),
                    argument.getArgument().getOrganization().getOfficialAddress().getZipCode(),
                    argument.getArgument().getOrganization().getOfficialAddress().getTown().getX(),
                    argument.getArgument().getOrganization().getOfficialAddress().getTown().getY(),
                    argument.getArgument().getOrganization().getOfficialAddress().getTown().getName());

            Worker worker = new Worker(idGenerator.generateId(), argument.getArgument().getCreationDate(),
                    argument.getArgument().getName(), argument.getArgument().getCoordinates(),
                    argument.getArgument().getSalary(), argument.getArgument().getStartDate(),
                    argument.getArgument().getEndDate(), argument.getArgument().getPosition(),
                    argument.getArgument().getOrganization());

            if (worker.compareTo(collection.stream().min(Comparator.comparing(Worker::getName)).get()) < 0) {
                collection.add(worker);
                this.setMessage("The new element is the min. It's added to the collection.");
            } else {
                this.setMessage("The new element is not the min. Nothing changed.");
                idGenerator.remove(worker.getId());
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
}
