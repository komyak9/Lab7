package db;

import content.*;
import creation.FieldsWrapper;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DBInteractionCommands implements Serializable {
    Connection connection = null;
    private String creatorName;
    private final Lock lock = new ReentrantLock();

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public void addElement(String creator, String name, int coordinatesX, int coordinatesY,
                           ZonedDateTime creationDate, float salary, LocalDateTime startDate, Date endDate,
                           Position position, int organizationAnnualTurnover, OrganizationType organizationType,
                           String addressZipCode, int locationX, int locationY, String locationName) throws SQLException {
        lock.lock();
        String endDateString = "";
        if (endDate != null)
            endDateString = new SimpleDateFormat("dd/MM/yyyy").format(endDate);
        Statement statement = connection.createStatement();

        ResultSet rs = statement.executeQuery("SELECT nextval('IDGENR') as idd");
        int idS = 0;
        if (rs.next())
            idS = rs.getInt("idd");

        statement.executeUpdate("INSERT INTO WORKERS VALUES('" + creator + "', " + idS + ", '" + name + "', " +
                coordinatesX + ", " + coordinatesY + ", '" + creationDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a z")) +
                "', " + salary + ", '" + startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + "', '" +
                endDateString + "', '" + position + "', " +
                organizationAnnualTurnover + ", '" + organizationType + "', '" + addressZipCode + "', " + locationX +
                ", " + locationY + ", '" + locationName + "')");
        lock.unlock();
    }

    public void addMaxElement(String creator, String name, int coordinatesX, int coordinatesY,
                              ZonedDateTime creationDate, float salary, LocalDateTime startDate, Date endDate,
                              Position position, int organizationAnnualTurnover, OrganizationType organizationType,
                              String addressZipCode, int locationX, int locationY, String locationName) throws SQLException {
        lock.lock();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT creator, id, name, coordinatesX, coordinatesY, creationDate, " +
                "salary, startDate, endDate, position, organizationAnnualTurnover, organizationType, addressZipCode, " +
                "locationX, locationY, locationName from WORKERS ORDER BY name DESC");

        if (rs.next()) {
            if (name.compareTo(rs.getString("name")) > 0) {
                addElement(creator, name, coordinatesX, coordinatesY, creationDate, salary, startDate, endDate, position,
                        organizationAnnualTurnover, organizationType, addressZipCode, locationX, locationY, locationName);
            }
        }
        lock.unlock();
    }

    public void addMinElement(String creator, String name, int coordinatesX, int coordinatesY,
                              ZonedDateTime creationDate, float salary, LocalDateTime startDate, Date endDate,
                              Position position, int organizationAnnualTurnover, OrganizationType organizationType,
                              String addressZipCode, int locationX, int locationY, String locationName) throws SQLException {

        lock.lock();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT creator, id, name, coordinatesX, coordinatesY, creationDate, " +
                "salary, startDate, endDate, position, organizationAnnualTurnover, organizationType, addressZipCode, " +
                "locationX, locationY, locationName from WORKERS ORDER BY name ASC");

        if (rs.next()) {
            if (name.compareTo(rs.getString("name")) < 0) {
                addElement(creator, name, coordinatesX, coordinatesY, creationDate, salary, startDate, endDate, position,
                        organizationAnnualTurnover, organizationType, addressZipCode, locationX, locationY, locationName);
            }
        }
        lock.unlock();
    }

    public boolean isEmpty() throws SQLException {
        lock.lock();
        boolean isEmpty = true;
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT creator, id, name, coordinatesX, coordinatesY," +
                "creationDate, salary, startDate, endDate, position, organizationAnnualTurnover," +
                "organizationType, addressZipCode, locationX, locationY, locationName from WORKERS");
        if (rs.next())
            isEmpty = false;

        lock.unlock();
        return isEmpty;
    }

    public void clear() throws SQLException {
        lock.lock();
        Statement statement = connection.createStatement();
        statement.executeUpdate("DELETE FROM workers WHERE creator = '" + creatorName + "'");
        lock.unlock();
    }

    public String readCollectionDescending() throws SQLException {
        lock.lock();
        String result = "";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT creator, id, name, coordinatesX, coordinatesY," +
                "creationDate, salary, startDate, endDate, position, organizationAnnualTurnover," +
                "organizationType, addressZipCode, locationX, locationY, locationName from WORKERS " +
                "ORDER BY name DESC");

        while (rs.next()) {
            result += "id: " + rs.getInt("id") + "\t\tname: " + rs.getString("name") +
                    "\t\tsalary: " + rs.getFloat("salary") + "\t\tstartDate: " +
                    rs.getString("startDate") + "\t\tendDate: " + rs.getString("endDate") +
                    "\t\tposition: " + rs.getString("position") + "\t\tcity: " +
                    rs.getString("locationName") + "\n";
        }

        lock.unlock();
        return result;
    }

    public void removePosition(Position position) throws SQLException {
        lock.lock();
        Statement statement = connection.createStatement();
        statement.executeUpdate("DELETE FROM workers WHERE position = '" + position.toString() + "' AND creator = '" + creatorName + "'");
        lock.unlock();
    }

    public void removeSalary(float salary) throws SQLException {
        lock.lock();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT creator, id, name, coordinatesX, coordinatesY, creationDate, " +
                "salary, startDate, endDate, position, organizationAnnualTurnover, organizationType, addressZipCode, " +
                "locationX, locationY, locationName from WORKERS WHERE salary = " + salary);

        if (rs.next())
            statement.executeUpdate("DELETE FROM workers WHERE id = " + rs.getInt("id") + " AND creator = '" + creatorName + "'");
        lock.unlock();
    }

    public void removeId(int id) throws SQLException {
        lock.lock();
        Statement statement = connection.createStatement();
        statement.executeUpdate("DELETE FROM workers WHERE id = " + id + " AND creator = '" + creatorName + "'");
        lock.unlock();
    }

    public void removeGreater(String name) throws SQLException {
        lock.lock();
        Statement statement = connection.createStatement();
        statement.executeUpdate("DELETE FROM workers WHERE name > '" + name + "' AND creator = '" + creatorName + "'");
        lock.unlock();
    }

    public String readCollection() throws SQLException {
        lock.lock();
        String result = "";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT creator, id, name, coordinatesX, coordinatesY," +
                "creationDate, salary, startDate, endDate, position, organizationAnnualTurnover," +
                "organizationType, addressZipCode, locationX, locationY, locationName from WORKERS");

        while (rs.next()) {
            result += "creator: " + rs.getString("creator") + "\tid: " + rs.getInt("id") +
                    "\t\tname: " + rs.getString("name") + "\t\tsalary: " + rs.getFloat("salary") +
                    "\t\tstartDate: " + rs.getString("startDate") + "\t\tendDate: " + rs.getString("endDate") +
                    "\t\tposition: " + rs.getString("position") + "\t\tcity: " + rs.getString("locationName") + "\n";
        }
        lock.unlock();
        return result;
    }

    public void updateElement(int id, String name, int coordinatesX, int coordinatesY,
                              ZonedDateTime creationDate, float salary, LocalDateTime startDate, Date endDate,
                              Position position, int organizationAnnualTurnover, OrganizationType organizationType,
                              String addressZipCode, int locationX, int locationY, String locationName) throws SQLException {
        lock.lock();
        Statement statement = connection.createStatement();

        String endDateString = "";
        if (endDate != null)
            endDateString = new SimpleDateFormat("dd/MM/yyyy").format(endDate);

        statement.executeUpdate("UPDATE workers SET name = '" + name + "', coordinatesX = " + coordinatesX +
                ", coordinatesY = " + coordinatesY + ", creationDate = '" + creationDate
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a z")) + "', salary = " + salary +
                ", startDate = '" + startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                "', endDate = '" + endDateString + "', position = '" + position + "', organizationAnnualTurnover = " +
                organizationAnnualTurnover + ", organizationType = '" + organizationType + "', addressZipCode = '" +
                addressZipCode + "', locationX = " + locationX + ", locationY = " + locationY + ", locationName = '" +
                locationName + "' WHERE id = " + id + " AND creator = '" + creatorName + "'");
        lock.unlock();
    }

    public void updateCollection(LinkedList<Worker> collection) throws Exception {
        lock.lock();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT creator, id, name, coordinatesX, coordinatesY," +
                "creationDate, salary, startDate, endDate, position, organizationAnnualTurnover," +
                "organizationType, addressZipCode, locationX, locationY, locationName from WORKERS");
        FieldsWrapper wrapper = new FieldsWrapper();
        collection.clear();

        while (rs.next()) {
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


            collection.add(new Worker(wrapper.getId(), wrapper.getCreationDate(), wrapper.getWorkerName(),
                    coordinates, wrapper.getSalary(), wrapper.getStartDate(), wrapper.getEndDate(),
                    wrapper.getPosition(), organization));
        }
        lock.unlock();
    }

    public String getCreator(int id) {
        lock.lock();
        String creator = "";
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT creator from WORKERS WHERE id = " + id);
            if (rs.next())
                creator = rs.getString("creator");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        lock.unlock();
        return creator;
    }

}