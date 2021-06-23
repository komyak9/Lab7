import commands.Command;
import commands.ExitCommand;

import java.io.File;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.Scanner;

public class ReadCommands {
    private final CommandValidator commandValidator;
    private File file;

    public ReadCommands(CommandValidator commandValidator) {
        this.commandValidator = commandValidator;
    }

    public void readCommands(String fileName) {
        isAvailable(fileName);
        Command<?> command;
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine() && !Client.getSocket().isOutputShutdown()) {
                command = commandValidator.validateData(scanner.nextLine(), scanner);
                if (command == null)
                    continue;
                if (command.getClass() == ExitCommand.class)
                    break;
                Client.sendData(command);
                System.out.println(Client.receiveData());
            }
        } catch (IOException | ClassNotFoundException e) {
            App.logger.warn(e.getMessage());
        }
    }

    private void isAvailable(String fileName) {
        try {
            file = new File(fileName);
            if (!file.exists())
                throw new NoSuchFileException("Sorry," + fileName + " file doesn't exist.");
        } catch (NoSuchFileException e) {
            App.logger.error(e.getMessage());
        }
    }
}
