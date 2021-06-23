package arguments;

import content.Worker;
import creation.WorkerCreator;

import java.io.Serializable;
import java.util.Scanner;


public class ElementArgument extends Argument<Worker> implements Serializable {
    public ElementArgument(Scanner scanner) throws Exception {
        super(new WorkerCreator().createWorker(scanner));
    }
}