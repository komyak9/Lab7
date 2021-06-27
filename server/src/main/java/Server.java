import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import commands.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jnlp.FileContents;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Server {
    private ServerSocket serverSocket;
    private Socket socket;
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);
    private final int PORT = 3175;
    private boolean isStopped = false;
    DBInteraction dbInteraction = new DBInteraction("jdbc:postgresql://localhost:5432/postgres",
            "postgres", "labpass");
    CollectionManager collectionManager = new CollectionManager(dbInteraction);
    public static final Logger logger = LoggerFactory.getLogger(Server.class);
    private Semaphore semaphore = new Semaphore(1000);

    public Server() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server is running.");
        }catch (Exception e){
            Server.logger.error(e.getMessage());
        }
    }



    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }



    public void run(){
        try {
            while (!isStopped) {
                semaphore.acquire();
                if (isStopped)
                    throw new Exception("Connection exception.");
                Socket socket = serverSocket.accept();
                System.out.println("Connection accepted.");
                executorService.submit(new ConnectionHandler(this, socket, collectionManager));
            }
        }catch(Exception ex){
            logger.error(ex.getMessage());
        }
    }
}