package commands;

import arguments.Argument;
import content.Worker;

import java.io.Serializable;
import java.util.LinkedList;

public class AddElementCommand extends Command<Worker> implements Serializable {
    public AddElementCommand(Argument<Worker> argument) {
        super(argument);
    }

    @Override
    public void execute(LinkedList<Worker> collection) {
        try {
            checkAuthorization(user.isAuthorized());

            dbInteractionCommands.addElement(dbInteractionCommands.getCreatorName(), 0, argument.getArgument().getName(),
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
            collection.add(worker);

            this.setMessage("New worker was successfully added to the collection.");
        } catch (Exception ex) {
            this.setMessage(ex.getMessage() + "\n New element wasn't added.");
        }
    }
}
