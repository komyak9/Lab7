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
            else {
                dbInteractionCommands.updateCollection(collection);
                for (Worker w : collection)
                    message += "creator: " + dbInteractionCommands.getCreator(w.getId()) + "\t\t" + w + "\n";

                //AtomicReference<String> message = new AtomicReference<>("");
                //collection.forEach(worker -> message.updateAndGet(v -> v + worker));
                //this.setMessage(message.get());
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

}
