import commands.Command;

import java.io.ObjectOutputStream;
import java.net.Socket;

public class TaskHandler implements Runnable{
    private Command<?> command;
    private CollectionManager collectionManager;
    private Socket socket;

    public TaskHandler(Command<?> command, CollectionManager collectionManager, Socket socket){
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
