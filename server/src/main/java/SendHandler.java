import commands.Command;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SendHandler implements Runnable{
    private Command<?> command;
    private Socket socket;

    public SendHandler(Command<?> command, Socket socket){
        this.command = command;
        this.socket = socket;
    }

    @Override
    public void run() {
        try{
            OutputStream out = socket.getOutputStream();
            out.write(command.getMessage().getBytes());
            out.flush();
            System.out.println("Result to send:\n" + command.getMessage());
        }catch(Exception ex){
            Server.logger.error(ex.getMessage());
        }
    }
}
