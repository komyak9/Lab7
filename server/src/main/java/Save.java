import content.Worker;
import parser.WorkerToXMLParser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Save {
    public void save(CollectionManager collectionManager) throws IOException, IllegalAccessException {
        File file = new File("worker.xml");
        FileOutputStream fstream = new FileOutputStream(file);
        String line = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n<workers>\n";

        WorkerToXMLParser parser = new WorkerToXMLParser();
        for (Worker worker : collectionManager.getWorkersList()) {
            line += parser.parse(worker);
        }
        line += "</workers>";

        byte[] buffer = line.getBytes();
        fstream.write(buffer);
    }
}
