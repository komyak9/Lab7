package commands;

import arguments.Argument;
import content.Worker;
import db.DBInteraction;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicReference;

public class ShowCommand extends Command<Integer> implements Serializable {
    public ShowCommand(Argument<Integer> argument) {
        super(argument);
    }

    @Override
    public void execute(LinkedList<Worker> collection, DBInteraction dbInteraction) {
        try {
            if (dbInteraction.isEmpty())
                throw new Exception("Collection is empty. Nothing to show.");
            else
                this.setMessage(dbInteraction.readCollection());

        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

}
