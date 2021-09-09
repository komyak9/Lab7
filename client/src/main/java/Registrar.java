import db.User;

import java.util.Scanner;

public class Registrar {
    Scanner sc = new Scanner(System.in);
    private User user;

    public Registrar() throws Exception {
        System.out.print("Do you want to register? \"yes\" for yes and anything for no: ");
        if (sc.nextLine().equals("yes"))
            register();
        else
            throw new Exception("You canceled authorization. Program's execution will be finished.");
    }

    public Registrar(String userName, String password) throws Exception {
        System.out.print("Do you want to log in or to register with your username and password? \"register\" for " +
                "register and \"log in\" for log in: ");
        String decision = sc.nextLine();
        if (decision.equals("log in"))
            user = new User(userName, password, true);
        else if (decision.equals("register"))
            user = new User(userName, password, false);
        else
            throw new Exception("You canceled authorization. Program's execution will be finished.");
    }

    public void register() throws Exception {
        System.out.print("Please, write your new username and password with a space (ex.: \"username password\"): ");
        if (!sc.hasNextLine())
            throw new Exception("There is no line. Program's going to be finished.");

        String[] input = sc.nextLine().trim().split(" ", 2);
        user = new User(input[0], input[1], false);
    }

    public User getUser() {
        return user;
    }
}
