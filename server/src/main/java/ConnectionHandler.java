import commands.Command;
import commands.ExitCommand;

import java.io.ObjectInputStream;
import java.net.Socket;

public class ConnectionHandler implements Runnable {
    private final Server server;
    private final Socket socket;
    private final CollectionManager collectionManager;

    public ConnectionHandler(Server server, Socket socket, CollectionManager collectionManager) {
        this.server = server;
        this.socket = socket;
        this.collectionManager = collectionManager;
    }

    @Override
    public void run() {
        Command<?> command;
        try {
            do {
                ObjectInputStream clientReader = new ObjectInputStream(socket.getInputStream());
                command = (Command<?>) clientReader.readObject();
                System.out.println("The command was read.");
                new Thread(new TaskHandler(command, collectionManager, socket)).start();
            } while (command.getClass() != ExitCommand.class);
        } catch (Exception exception) {
            Server.logger.error(exception.getMessage());
        }
    }
}
