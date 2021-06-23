package commands;

import arguments.Argument;
import content.Worker;
import creation.IdGenerator;
import db.DBInteraction;

import java.io.Serializable;
import java.util.LinkedList;

abstract public class Command<T> implements Serializable {
    protected Argument<T> argument;
    protected String message = null;
    protected IdGenerator idGenerator = null;
    public Command(Argument<T> argument) {
        this.argument = argument;
    }

    abstract public void execute(LinkedList<Worker> collection, DBInteraction dbInteraction);

    public IdGenerator getIdGenerator() {
        return idGenerator;
    }

    public void setIdGenerator(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Argument<T> getArgument() {
        return argument;
    }

    public void setArgument(Argument<T> argument) {
        this.argument = argument;
    }

    @Override
    public String toString() {
        return "commands.Command{" +
                "argument=" + argument +
                '}';
    }
}