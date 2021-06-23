package commands;

import arguments.Argument;
import content.Worker;

import java.io.Serializable;
import java.util.LinkedList;

public class ShowCommand extends Command<Integer> implements Serializable {
    public ShowCommand(Argument<Integer> argument) {
        super(argument);
    }

    @Override
    public void execute(LinkedList<Worker> collection) {
        try {
            if (dbInteractionCommands.isEmpty())
                throw new Exception("Collection is empty. Nothing to show.");
            else
                this.setMessage(dbInteractionCommands.readCollection());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

}
