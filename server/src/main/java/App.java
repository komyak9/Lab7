import db.DBInteraction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
    public static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        boolean isAuthorized = true;
        DBInteraction dbInteraction = new DBInteraction("jdbc:postgresql://localhost:5432/postgres",
                "postgres", "labpass");

        if (args.length == 2)
            isAuthorized = dbInteraction.authorization(args[0], args[1]);
        else
            isAuthorized = dbInteraction.register();

        CollectionManager collectionManager = new CollectionManager(dbInteraction);


        /*
        try {
            final int PORT = 3175;
            Server.run(PORT);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            System.exit(0);
        }


        boolean toContinue = true;
        try {
            while (toContinue) {
                long start = System.currentTimeMillis();
                try {
                    toContinue = Server.processRequest(collectionManager, start);
                } catch (IOException | ClassNotFoundException e) {
                    logger.warn("Connection is aborted! Trying to reconnect...");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException ie) {
                        logger.error(ie.getMessage());
                    }
                }
            }
        } finally {
            try {
                new Save().save(collectionManager);
            } catch (IllegalAccessException | IOException e) {
                System.out.println(e.getMessage());
            }
        }
        */
    }

    /*
    statement.executeUpdate("INSERT INTO WORKERS VALUES(2, 'Ron', 2343, '01/04/2011', '', 'HUMAN_RESOURCES', 'New-York')");
    statement.executeUpdate("INSERT INTO WORKERS VALUES(3, 'Mona', 3004.40, '03/02/2007', '', 'COOK', 'Delaware')");
    statement.executeUpdate("INSERT INTO WORKERS VALUES(4, 'Richard', 1840.23, '11/01/2017', '', 'CLEANER', 'Toronto')");
    */
}
