import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Server {
    public static final Logger logger = LoggerFactory.getLogger(Server.class);
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);
    private final int PORT = 3175;
    DBInteraction dbInteraction = new DBInteraction("jdbc:postgresql://localhost:5432/postgres",
            "postgres", "labpass");
    CollectionManager collectionManager = new CollectionManager(dbInteraction);
    private ServerSocket serverSocket;
    private Socket socket;
    private final boolean isStopped = false;
    private final Semaphore semaphore = new Semaphore(1000);

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
            while (!isStopped) {
                semaphore.acquire();
                if (isStopped)
                    throw new Exception("Connection exception.");
                Socket socket = serverSocket.accept();
                System.out.println("Connection accepted.");
                executorService.submit(new ConnectionHandler(this, socket, collectionManager));
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }
}