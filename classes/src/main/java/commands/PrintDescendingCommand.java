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
            String message = "";
            checkAuthorization(user.isAuthorized());
            if (dbInteractionCommands.isEmpty())
                throw new Exception("Collection is empty. Nothing to show.");
            else {
                dbInteractionCommands.updateCollection(collection);

                for (Worker w : collection)
                    message += "creator: " + dbInteractionCommands.getCreator(w.getId()) + "\t\t" + w;

                ///AtomicReference<String> message = new AtomicReference<>("");
                ///LinkedList<Worker> printList = new LinkedList<>(collection);
                ///printList.stream().sorted(Comparator.reverseOrder()).forEach(worker -> message.updateAndGet(v -> v + worker));
                this.setMessage(message);
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
}
