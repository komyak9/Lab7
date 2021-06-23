package commands;

import arguments.Argument;
import content.Worker;

import java.io.Serializable;
import java.util.LinkedList;

public class PrintDescendingCommand extends Command<Integer> implements Serializable {
    public PrintDescendingCommand(Argument<Integer> argument) {
        super(argument);
    }

    @Override
    public void execute(LinkedList<Worker> collection) {
        try {
            checkAuthorization(user.isAuthorized());

            if (dbInteractionCommands.isEmpty())
                throw new Exception("Collection is empty. Nothing to show.");
            else
                this.setMessage(dbInteractionCommands.readCollectionDescending());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
}
