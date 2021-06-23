import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
    public static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        String host = "localhost";
        int port = 3175;
        Client.connect(host, port);

        ConsoleManager consoleManager = new ConsoleManager();
        boolean toContinue = true;
        try {
            while (toContinue) {
                try {
                    toContinue = consoleManager.communicate(host, port);
                } catch (Exception e) {
                    logger.warn("Connection is aborted! Trying to reconnect...");
                    try {
                        Client.getOut().flush();
                        toContinue = Client.connect(host, port);
                    } catch (Exception ex) {
                        logger.warn(ex.getMessage());
                    }
                }
            }
        }finally {
            try {
                Client.close();
            } catch (Exception e) {
                logger.warn(e.getMessage());
            }
        }
    }
}
