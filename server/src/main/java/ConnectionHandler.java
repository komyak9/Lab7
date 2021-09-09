import commands.Command;
import commands.ExitCommand;

import java.io.ObjectInputStream;
import java.net.Socket;

public class ConnectionHandler implements Runnable {
    private final Socket socket;
    private final CollectionManager collectionManager;

    public ConnectionHandler(Socket socket, CollectionManager collectionManager) {
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
            Server.logger.info("A client is closed. Waiting for other clients...");
        }
    }
}
