package commands;

import arguments.Argument;
import content.Worker;

import java.io.Serializable;
import java.util.LinkedList;

public class AddIfMinCommand extends Command<Worker> implements Serializable {
    public AddIfMinCommand(Argument<Worker> argument) {
        super(argument);
    }

    @Override
    public void execute(LinkedList<Worker> collection) {
        try {
            checkAuthorization(user.isAuthorized());
            int count_before = collection.size();
            dbInteractionCommands.addMinElement(dbInteractionCommands.getCreatorName(), argument.getArgument().getName(),
                    (int) argument.getArgument().getCoordinates().getX(), argument.getArgument().getCoordinates().getY(),
                    argument.getArgument().getCreationDate(), argument.getArgument().getSalary(),
                    argument.getArgument().getStartDate(), argument.getArgument().getEndDate(),
                    argument.getArgument().getPosition(), argument.getArgument().getOrganization().getAnnualTurnover(),
                    argument.getArgument().getOrganization().getType(),
                    argument.getArgument().getOrganization().getOfficialAddress().getZipCode(),
                    argument.getArgument().getOrganization().getOfficialAddress().getTown().getX(),
                    argument.getArgument().getOrganization().getOfficialAddress().getTown().getY(),
                    argument.getArgument().getOrganization().getOfficialAddress().getTown().getName());

            dbInteractionCommands.updateCollection(collection);
            int count_after = collection.size();
            if (count_after > count_before)
                this.setMessage("The new element is the min. It's added to the collection.");
            else
                this.setMessage("The new element is not the min. Nothing changed.");
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
}
