import commands.Command;
import commands.ExitCommand;

import java.io.IOException;
import java.util.Scanner;

public class ConsoleManager {
    private final Scanner scanner = new Scanner(System.in);
    private final CommandValidator commandValidator = new CommandValidator();

    public Command<?> readCommand() {
        Command<?> command = null;
        try {
            do {
                System.out.println("\nPLease, enter a command. You can inspect the list of available commands by \"help\".");
                try {
                    if (!scanner.hasNextLine())
                        throw new Exception("There is no line. Program's going to be finished.");
                } catch (Exception ex) {
                    App.logger.error(ex.getMessage());
                    System.exit(0);
                }
            } while ((command = commandValidator.validateData(scanner.nextLine(), scanner)) == null);
        } catch (Exception e) {
            App.logger.warn(e.getMessage());
        }
        return command;
    }

    public boolean communicate(String host, int port) throws IOException, ClassNotFoundException {
        Command<?> command;
        while (!Client.getSocket().isOutputShutdown()) {
            command = this.readCommand();
            if (command.getClass() == ExitCommand.class)
                return false;
            Client.sendData(command);
            System.out.println("Server's response:" + Client.receiveData());
        }
        return true;
    }
}