import commands.Command;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class Server {
    public static Selector selector;
    private static SocketAddress socketAddress;
    private static ServerSocketChannel serverSocketChannel;

    public static void run(int port) throws IOException, ClassNotFoundException {
        socketAddress = new InetSocketAddress(port);
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(socketAddress);
        serverSocketChannel.configureBlocking(false);
    }

    public static boolean processRequest(CollectionManager collectionManager, long start) throws IOException, ClassNotFoundException {
        boolean toContinue = true;
        Command<?> command;
        selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            long current = System.currentTimeMillis();
            if (current - start >= 100000) {
                Scanner sc = new Scanner(System.in);
                System.out.println("Do you want to continue waiting? \"no\" for no, anything for yes");
                String decision = sc.nextLine();
                if (decision.equals("no"))
                    toContinue = false;
                return toContinue;
            }

            System.out.println("Waiting for select...");
            selector.select(50000);
            Set<SelectionKey> selectedKeys = selector.selectedKeys();

            for (Iterator iter = selectedKeys.iterator(); iter.hasNext(); ) {
                SelectionKey key = (SelectionKey) iter.next();

                if (key.isValid()) {
                    if (key.isAcceptable()) {
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ);
                        System.out.println("Connection accepted.");
                    }
                    if (key.isReadable()) {
                        SocketChannel socketChannel = (SocketChannel) key.channel();

                        ByteBuffer buffer = ByteBuffer.allocate(1000000);
                        socketChannel.read(buffer);
                        buffer.flip();
                        command = (Command<?>) (new Serializer().deserialize(buffer.array()));


                        String result = collectionManager.execute(command);
                        System.out.println("A string to send: " + result);
                        socketChannel.write(ByteBuffer.wrap(result.getBytes()));
                    }
                }
                iter.remove();
            }
        }
        //return toContinue;
    }
}