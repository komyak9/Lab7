package commands;

import arguments.Argument;
import content.Worker;
import db.DBInteractionCommands;
import db.User;

import java.io.Serializable;
import java.sql.Connection;
import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

abstract public class Command<T> implements Serializable {
    public User user = null;
    protected Argument<T> argument;
    protected String message = null;
    protected DBInteractionCommands dbInteractionCommands = new DBInteractionCommands();

    public Command(Argument<T> argument) {
        this.argument = argument;
    }

    abstract public void execute(LinkedList<Worker> collection);

    public synchronized String getMessage() {
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

    protected void checkAuthorization(boolean isAuthorized) throws Exception {
        if (!isAuthorized)
            throw new Exception("Sorry, to execute this command you must log in or register.");
    }

    public void setDbInteractionCommands(Connection connection, String userName) {
        dbInteractionCommands.setConnection(connection);
        dbInteractionCommands.setCreatorName(userName);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "commands.Command{" +
                "argument=" + argument +
                '}';
    }
}