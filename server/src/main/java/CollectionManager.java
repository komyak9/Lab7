import commands.Command;
import content.Worker;
import creation.IdGenerator;
import db.DBInteraction;

import java.util.LinkedList;

public class CollectionManager {
    private final IdGenerator idGenerator = new IdGenerator();  /////////////////////////////////////
    private final DBInteraction dbInteraction;
    private LinkedList<Worker> workersList;

    public CollectionManager(DBInteraction dbInteraction) {
        this.dbInteraction = dbInteraction;
        fillCollection();
    }

    public String execute(Command<?> command) {
        command.setIdGenerator(idGenerator);  ////////////////////////////////////////////////////////
        command.execute(workersList);
        return command.getMessage();
    }

    private void fillCollection() {
        try {
            workersList = dbInteraction.readIntoList();
            System.out.println("Data from the file were successfully downloaded.");
        } catch (Exception ex) {
            App.logger.warn(ex.getMessage());
        }
    }

    public LinkedList<Worker> getWorkersList() {
        return workersList;
    }
}