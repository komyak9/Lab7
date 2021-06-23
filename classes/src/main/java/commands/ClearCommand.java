package commands;

import arguments.Argument;
import content.Worker;
import db.DBInteraction;

import java.io.Serializable;
import java.util.LinkedList;

public class ClearCommand extends Command<Integer> implements Serializable {
    public ClearCommand(Argument<Integer> argument) {
        super(argument);
    }

    @Override
    public void execute(LinkedList<Worker> collection, DBInteraction dbInteraction) {
        try {
            if (dbInteraction.isEmpty())
                throw new Exception("Collection is already empty. Nothing to clear.");
            else{
                dbInteraction.clear();
                collection.clear();
            }
            //getIdGenerator().getIdSet().clear();
            this.setMessage("Collection cleared. It's empty now.");
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
}
