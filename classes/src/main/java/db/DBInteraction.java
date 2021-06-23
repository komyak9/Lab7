package db;

import content.*;
import creation.FieldsWrapper;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;

public class DBInteraction {
    final String URL;
    final String USER;
    final String PASS;
    Connection connection = null;
    Statement statement = null;
    private String creatorName = null;

    public DBInteraction(String URL, String user, String password) {
        this.URL = URL;
        this.USER = user;
        this.PASS = password;
        connect();
        createWorkerTable();
        createUserTable();
    }

    public void connect() {
        try {
            System.out.println("Trying to connect the database...");
            connection = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("You successfully connected to the database!");
        } catch (SQLException e) {
            System.out.println("Failed to make connection to the database. Try to execute the program again.\n" +
                    e.getMessage());
            System.exit(0);
        }
    }

    public void createWorkerTable() {
        try {
            if (!checkTableExistence("workers")) {
                System.out.println("Trying to create the table for data...");
                statement = connection.createStatement();
                String sql = "CREATE TABLE WORKERS" +
                        "(creator VARCHAR(255), " +
                        "id INTEGER not NULL, " +
                        "name VARCHAR(255), " +
                        "coordinatesX INTEGER not NULL, " +
                        "coordinatesY INTEGER not NULL, " +
                        "creationDate VARCHAR(255), " +
                        "salary FLOAT not NULL, " +
                        "startDate VARCHAR(255), " +
                        "endDate VARCHAR(255), " +
                        "position VARCHAR(255), " +
                        "organizationAnnualTurnover INTEGER not NULL, " +
                        "organizationType VARCHAR(255) not NULL, " +
                        "addressZipCode VARCHAR(255), " +
                        "locationX INTEGER not NULL, " +
                        "locationY INTEGER not NULL, " +
                        "locationName VARCHAR(255))";
                statement.executeUpdate(sql);
                statement.close();
                System.out.println("The table created successfully!");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void createUserTable() {
        try {
            if (!checkTableExistence("users")) {
                System.out.println("Trying to create the table for users...");
                statement = connection.createStatement();
                String sql = "CREATE TABLE USERS" +
                        "(logins VARCHAR(255), " +
                        "passwords VARCHAR(255))";
                statement.executeUpdate(sql);
                statement.close();
                System.out.println("The table created successfully!");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void addElement(String creator, int id, String name, int coordinatesX, int coordinatesY,
                           ZonedDateTime creationDate, float salary, LocalDateTime startDate, Date endDate,
                           Position position, int organizationAnnualTurnover, OrganizationType organizationType,
                           String addressZipCode, int locationX, int locationY, String locationName) {

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("INSERT INTO WORKERS VALUES('" + creator + "', " + id + ", '" + name + "', " +
                    coordinatesX + ", " + coordinatesY + ", '" + creationDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a z")) +
                    "', " + salary + ", '" + startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + "', '" +
                    new SimpleDateFormat("dd-MM-yyyy").format(endDate) + "', '" + position + "', " +
                    organizationAnnualTurnover + ", '" + organizationType + "', '" + addressZipCode + "', " + locationX +
                    ", " + locationY + ", '" + locationName + "')");

            System.out.println("New element was added.");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public LinkedList<Worker> readIntoList() {
        LinkedList<Worker> workersToUpdate = new LinkedList<>();
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery("SELECT creator, id, name, coordinatesX, coordinatesY," +
                     "creationDate, salary, startDate, endDate, position, organizationAnnualTurnover," +
                     "organizationType, addressZipCode, locationX, locationY, locationName from WORKERS")) {
            System.out.println("Reading data from the table...");
            FieldsWrapper wrapper = new FieldsWrapper();

            while (rs.next()) {
                if (creatorName.equals(rs.getString("creator"))) {
                    wrapper.setId(rs.getInt("id"));
                    wrapper.setWorkerName(rs.getString("name"));
                    wrapper.setCoordinatesX(rs.getInt("coordinatesX"));
                    wrapper.setCoordinatesY(rs.getInt("coordinatesY"));
                    wrapper.setCreationDate(ZonedDateTime.parse(rs.getString("creationDate"),
                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a z")));
                    wrapper.setSalary(rs.getFloat("salary"));
                    wrapper.setStartDate(LocalDateTime.parse(rs.getString("startDate"),
                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
                    wrapper.setPosition(Position.valueOf(rs.getString("position")));
                    wrapper.setAnnualTurnover(rs.getInt("organizationAnnualTurnover"));
                    wrapper.setOrganizationType(OrganizationType.valueOf(rs.getString("organizationType")));
                    wrapper.setZipCode(rs.getString("addressZipCode"));
                    wrapper.setLocationX(rs.getInt("locationX"));
                    wrapper.setLocationY(rs.getInt("locationY"));
                    wrapper.setLocationName(rs.getString("locationName"));
                    if (!rs.getString("endDate").equals(""))
                        wrapper.setEndDate(new SimpleDateFormat("dd-MM-yyyy").parse(rs.getString("endDate")));
                    else
                        wrapper.setEndDate(null);

                    Location location = new Location(wrapper.getLocationX(), wrapper.getLocationY(), wrapper.getLocationName());
                    Address address = new Address(wrapper.getZipCode(), location);
                    Organization organization = new Organization(wrapper.getAnnualTurnover(), wrapper.getOrganizationType(), address);
                    Coordinates coordinates = new Coordinates(wrapper.getCoordinatesX(), wrapper.getCoordinatesY());

                    workersToUpdate.add(new Worker(wrapper.getId(), wrapper.getCreationDate(), wrapper.getWorkerName(),
                            coordinates, wrapper.getSalary(), wrapper.getStartDate(), wrapper.getEndDate(),
                            wrapper.getPosition(), organization));
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return workersToUpdate;
    }

    public boolean checkTableExistence(String tableName) {
        try {
            DatabaseMetaData meta = connection.getMetaData();
            ResultSet rs = meta.getTables(null, null, tableName,
                    new String[]{"TABLE"});
            System.out.println("Checking existence of the " + tableName.toUpperCase() + " table...");

            while (rs.next()) {
                if (rs.getString("TABLE_NAME").equals(tableName)) {
                    System.out.println("The " + tableName.toUpperCase() + " table exists.");
                    return true;
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    public void removeTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE WORKERS");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public boolean authorization(String user, String password) {
        boolean isFound = false;
        System.out.println("You input username: " + user + " and password: " + password);
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery("SELECT logins, passwords from USERS")) {
            while (rs.next()) {
                if (user.equals(rs.getString("logins"))) {
                    if (password.equals(rs.getString("passwords"))) {
                        isFound = true;
                        creatorName = user;
                        System.out.println("You successfully logged in.");
                        break;
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        if (!isFound) {
            System.out.println("Wrong username or password. Do you want to register? " +
                    "\"yes\" for YES and anything for NO ");
            Scanner sc = new Scanner(System.in);
            if (sc.nextLine().equals("yes"))
                register();
            else
                System.out.println("You can continue, but you can't execute commands. Or you can re-execute the program with new username and password.");
        }
        return isFound;
    }

    public boolean register() {
        boolean isRegistered = false;
        boolean toContinue = true;
        String login;
        String pwd;

        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery("SELECT logins, passwords from USERS");
             Scanner sc = new Scanner(System.in)) {

            LinkedList<String> userNames = new LinkedList<>();
            while (rs.next()) {
                userNames.add(rs.getString("logins"));
            }

            while (toContinue) {
                System.out.print("Please, enter your new username: ");
                login = sc.nextLine();
                if (userNames.contains(login)) {
                    System.out.println("Sorry, such user is already exists. Do you want to continue? \"yes\" for YES and anything for NO");
                    if (sc.nextLine().equals("yes"))
                        continue;
                    else
                        break;
                }
                System.out.print("Please, enter your new password: ");
                pwd = sc.nextLine();

                statement.executeUpdate("INSERT INTO users VALUES('" + login + "', '" + pwd + "')");
                System.out.println("New user created successfully!");
                toContinue = false;
                isRegistered = true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return isRegistered;
    }

    public String getCreatorName(){
        return creatorName;
    }

    public String readCollection(){
        String result = "";
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery("SELECT creator, id, name, coordinatesX, coordinatesY," +
                     "creationDate, salary, startDate, endDate, position, organizationAnnualTurnover," +
                     "organizationType, addressZipCode, locationX, locationY, locationName from WORKERS")) {
            System.out.println("Reading data from the table...");

            while (rs.next()) {
                result += "id: " + rs.getInt("id") + "\t\tname: " + rs.getString("name") +
                        "\t\tsalary: " + rs.getFloat("salary") + "\t\tstartDate: " +
                        rs.getString("startDate") + "\t\tendDate: " + rs.getString("endDate") +
                        "\t\tposition: " + rs.getString("position") + "\t\tcity: " +
                        rs.getString("locationName") + "\n";
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return result;
    }

    public void clear(){
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate("DELETE FROM workers WHERE id > 0");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public boolean isEmpty(){
        boolean isEmpty = false;
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery("SELECT creator, id, name, coordinatesX, coordinatesY," +
                     "creationDate, salary, startDate, endDate, position, organizationAnnualTurnover," +
                     "organizationType, addressZipCode, locationX, locationY, locationName from WORKERS")) {
            System.out.println("Reading data from the table...");

            if (rs.next())
                isEmpty = true;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return isEmpty;
    }

    public String readCollectionDescensing(){
        String result = "";
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery("SELECT creator, id, name, coordinatesX, coordinatesY," +
                     "creationDate, salary, startDate, endDate, position, organizationAnnualTurnover," +
                     "organizationType, addressZipCode, locationX, locationY, locationName from WORKERS " +
                     "ORDER BY name DESC")) {
            System.out.println("Reading data from the table...");

            while (rs.next()) {
                result += "id: " + rs.getInt("id") + "\t\tname: " + rs.getString("name") +
                        "\t\tsalary: " + rs.getFloat("salary") + "\t\tstartDate: " +
                        rs.getString("startDate") + "\t\tendDate: " + rs.getString("endDate") +
                        "\t\tposition: " + rs.getString("position") + "\t\tcity: " +
                        rs.getString("locationName") + "\n";
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

    public void removePosition(Position position){
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate("DELETE FROM workers WHERE position = '" + position.toString() + "'");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void removeSalary(float salary){
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate("DELETE FROM workers WHERE salary = " + salary);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void removeId(int id){
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate("DELETE FROM workers WHERE id = " + id);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void removeGreater(String name){
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate("DELETE FROM workers WHERE name = '" + name + "'");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void updateElement(int id, String name, int coordinatesX, int coordinatesY,
                              ZonedDateTime creationDate, float salary, LocalDateTime startDate, Date endDate,
                              Position position, int organizationAnnualTurnover, OrganizationType organizationType,
                              String addressZipCode, int locationX, int locationY, String locationName){
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate("UPDATE workers SET name = '" + name + "', coordinatesX = " + coordinatesX +
                    ", coordinatesY = " + coordinatesY + ", creationDate = '" + creationDate
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a z")) + "', salary = " + salary +
                    ", startDate = '" + startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                    "', endDate = '" + new SimpleDateFormat("dd-MM-yyyy").format(endDate) + "', position = '" +
                    position + "', organizationAnnualTurnover = " + organizationAnnualTurnover + ", organizationType = '" +
                    organizationType + "', addressZipCode = '" + addressZipCode + "', locationX = " + locationX +
                    ", locationY = " + locationY + ", locationName = '" + locationName +"WHERE id = " + id);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}