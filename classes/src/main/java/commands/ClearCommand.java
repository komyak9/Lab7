package commands;

import arguments.Argument;
import content.Worker;

import java.io.Serializable;
import java.util.LinkedList;

public class ClearCommand extends Command<Integer> implements Serializable {
    public ClearCommand(Argument<Integer> argument) {
        super(argument);
    }

    @Override
    public void execute(LinkedList<Worker> collection) {
        try {
            checkAuthorization(user.isAuthorized());

            if (dbInteractionCommands.isEmpty())
                throw new Exception("Collection is already empty. Nothing to clear.");
            else {
                dbInteractionCommands.clear();
                collection.clear();
            }
            //getIdGenerator().getIdSet().clear();
            this.setMessage("Collection cleared. It's empty now.");
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
}
