import arguments.ElementArgument;
import arguments.IdArgument;
import arguments.PositionArgument;
import arguments.SalaryArgument;
import commands.*;
import content.Position;

import java.util.LinkedList;
import java.util.Scanner;

public class CommandValidator {
    LinkedList<String> filesList = new LinkedList<>();
    private final ReadCommands readCommands = new ReadCommands(this);

    public Command<?> validateData(String line, Scanner scanner) {
        String[] input = line.trim().split(" ", 2);
        Command<?> command = null;
        String inputCommand = input[0];

        try {
            switch (inputCommand) {
                case "help":
                    command = new HelpCommand(null);
                    break;
                case "info":
                    command = new InfoCommand(null);
                    break;
                case "show":
                    command = new ShowCommand(null);
                    break;
                case "add":
                    command = new AddElementCommand(new ElementArgument(scanner));
                    break;
                case "update":
                    IdArgument idArgument = new IdArgument(Integer.parseInt(input[1]));
                    idArgument.setElement(new ElementArgument(scanner));
                    command = new UpdateIdCommand(idArgument);
                    break;
                case "remove_by_id":
                    command = new RemoveByIdCommand(new IdArgument(Integer.parseInt(input[1])));
                    break;
                case "clear":
                    command = new ClearCommand(null);
                    break;
                case "execute_script":
                    if (filesList.contains(input[1]))
                        throw new Exception("Such script is currently being executed.");
                    filesList.add(input[1]);
                    readCommands.readCommands(input[1]);
                    filesList.remove(input[1]);
                    break;
                case "exit":
                    command = new ExitCommand(null);
                    break;
                case "add_if_max":
                    command = new AddIfMaxCommand(new ElementArgument(scanner));
                    break;
                case "add_if_min":
                    command = new AddIfMinCommand(new ElementArgument(scanner));
                    break;
                case "remove_greater":
                    command = new RemoveGreaterCommand(new ElementArgument(scanner));
                    break;
                case "remove_all_by_position":
                    command = new RemoveAllByPositionCommand(new PositionArgument(Position.valueOf(input[1])));
                    break;
                case "remove_any_by_salary":
                    command = new RemoveAnyBySalaryCommand(new SalaryArgument(Float.parseFloat(input[1])));
                    break;
                case "print_descending":
                    command = new PrintDescendingCommand(null);
                    break;
                default:
                    throw new Exception("There is no such command.");
            }
        } catch (Exception ex) {
            App.logger.error(ex.getMessage());
        }
        return command;
    }
}
