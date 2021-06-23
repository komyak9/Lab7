package commands;

import arguments.Argument;
import content.Worker;

import java.io.Serializable;
import java.util.LinkedList;

public class InfoCommand extends Command<Integer> implements Serializable {
    public InfoCommand(Argument<Integer> argument) {
        super(argument);
    }

    @Override
    public void execute(LinkedList<Worker> collection) {
        try {
            checkAuthorization(user.isAuthorized());

            String message = "Type of the collection: LinkedList\n";
            message += "Number of elements: " + collection.size();
            this.setMessage(message);
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
}