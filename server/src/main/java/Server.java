import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Time;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Server {
    public static final Logger logger = LoggerFactory.getLogger(Server.class);
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);
    private final Semaphore semaphore = new Semaphore(1000);
    private ServerSocket serverSocket;
    private final int PORT = 3175;
    //DBInteraction dbInteraction = new DBInteraction("jdbc:postgresql://localhost:5432/postgres", "postgres", "labpass");
    DBInteraction dbInteraction = new DBInteraction("jdbc:postgresql://pg:5432/studs", "s312715", "dyc664");
    CollectionManager collectionManager = new CollectionManager(dbInteraction);

    public Server() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server is running.");
        } catch (Exception e) {
            Server.logger.error(e.getMessage());
        }
    }


    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }


    public void run() {
        try {
            while (true) {
                semaphore.acquire();
                Socket socket = serverSocket.accept();
                System.out.println("Connection accepted.");
                executorService.submit(new ConnectionHandler(this, socket, collectionManager));
            }
        } catch (Exception ex) {
            logger.warn(ex.getMessage());

        }
    }
}