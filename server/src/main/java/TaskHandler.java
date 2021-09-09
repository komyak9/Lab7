import commands.Command;

import java.net.Socket;

public class TaskHandler implements Runnable {
    private final Command<?> command;
    private final CollectionManager collectionManager;
    private final Socket socket;

    public TaskHandler(Command<?> command, CollectionManager collectionManager, Socket socket) {
        this.command = command;
        this.collectionManager = collectionManager;
        this.socket = socket;
    }


    @Override
    public void run() {
        collectionManager.execute(command);
        System.out.println("The command was executed.");
        new Thread(new SendHandler(command, socket)).start();
    }
}
