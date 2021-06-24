import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static String host;
    public static int port;
    private static Socket socket;
    private static OutputStream out;
    private static InputStream in;

    public static boolean connect(String hostage, int socketPort) {
        host = hostage;
        port = socketPort;
        boolean scanning = true;
        boolean toContinue = true;
        long start = System.currentTimeMillis();
        while (scanning && toContinue) {
            if (System.currentTimeMillis() - start >= 100000) {
                Scanner sc = new Scanner(System.in);
                System.out.println("Do you want to continue waiting? \"no\" for no, anything for yes");
                String decision = sc.nextLine();
                if (decision.equals("no")) {
                    toContinue = false;
                    return toContinue;
                }
                start = System.currentTimeMillis();
            }

            try {
                socket = new Socket(host, port);
                scanning = false;
                AppClient.logger.info("Connection with the server is established!");
                out = socket.getOutputStream();
                in = socket.getInputStream();
            } catch (Exception e) {
                AppClient.logger.warn("Connection with the server is failed, waiting and trying again...");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ie) {
                    AppClient.logger.warn(ie.getMessage());
                }
            }
        }
        return true;
    }

    public static void sendData(Object object) throws IOException {
        if (!socket.isOutputShutdown())
            out.write(new Serializer().serialize((Serializable) object));
        out.flush();
        AppClient.logger.info("Data to the server was successfully sent.");
    }

    public static String receiveData() throws IOException, ClassNotFoundException {
        byte[] bytes = new byte[1000000];
        in.read(bytes);
        return new String(bytes).replaceAll("\u0000.*", "");
    }

    public static void close() throws IOException {
        System.out.println("Closing the client...");
        in.close();
        out.close();
        socket.close();
    }

    public static Socket getSocket() {
        return socket;
    }

    public static OutputStream getOut() {
        return out;
    }
}
