package commands;

import arguments.Argument;
import content.Worker;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;

public class HelpCommand extends Command<Integer> implements Serializable {
    private final HashMap<String, String> commands = new HashMap<>();

    {
        commands.put("help", "Print info about commands");
        commands.put("info", "Print info about the collection");
        commands.put("show", "Print collection's elements");
        commands.put("add", "Add a new elements to the collection");
        commands.put("update", "Update an element with the given id");
        commands.put("remove_by_id", "Remove an elements with the given id");
        commands.put("clear", "Remove all the elements of the collection");
        commands.put("execute_script", "Read the file and do the script");
        commands.put("exit", "Finish the program");
        commands.put("add_if_max", "Add a new element if it has the highest value");
        commands.put("add_if_min", "Add a new element if it has the lowest value");
        commands.put("remove_greater", "Remove all the elements that are higher than this one");
        commands.put("remove_all_by_position", "Remove all the elements with the same position");
        commands.put("remove_any_by_salary", "Remove the element due to the salary");
        commands.put("print_descending", "Print the elements from max to min");
    }

    public HelpCommand(Argument<Integer> argument) {
        super(argument);
    }

    @Override
    public void execute(LinkedList<Worker> collection) {
        StringBuilder message = new StringBuilder("Available commands: ");
        String[] keys = commands.keySet().toArray(new String[0]);
        for (int i = 0; i < commands.size(); i++) {
            message.append('\n').append(keys[i]).append(" - ").append(commands.get(keys[i]));
        }
        this.setMessage(message.toString());
    }

    @Override
    public String toString() {
        return "commands.HelpCommand{" +
                "commands=" + commands +
                '}';
    }
}
