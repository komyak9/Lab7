package db;

import content.OrganizationType;
import content.Position;

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

public class DBInteractionCommands implements Serializable {
    Connection connection = null;
    private String creatorName = null;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public void addElement(String creator, int id, String name, int coordinatesX, int coordinatesY,
                           ZonedDateTime creationDate, float salary, LocalDateTime startDate, Date endDate,
                           Position position, int organizationAnnualTurnover, OrganizationType organizationType,
                           String addressZipCode, int locationX, int locationY, String locationName) throws SQLException {
        String endDateString = "";
        if (endDate != null)
            endDateString = new SimpleDateFormat("dd/MM/yyyy").format(endDate);
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO WORKERS VALUES('" + creator + "', " + id + ", '" + name + "', " +
                coordinatesX + ", " + coordinatesY + ", '" + creationDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a z")) +
                "', " + salary + ", '" + startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + "', '" +
                endDateString + "', '" + position + "', " +
                organizationAnnualTurnover + ", '" + organizationType + "', '" + addressZipCode + "', " + locationX +
                ", " + locationY + ", '" + locationName + "')");

    }

    public void addMaxElement(String creator, int id, String name, int coordinatesX, int coordinatesY,
                              ZonedDateTime creationDate, float salary, LocalDateTime startDate, Date endDate,
                              Position position, int organizationAnnualTurnover, OrganizationType organizationType,
                              String addressZipCode, int locationX, int locationY, String locationName) throws SQLException {

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT creator, id, name, coordinatesX, coordinatesY, creationDate, " +
                "salary, startDate, endDate, position, organizationAnnualTurnover, organizationType, addressZipCode, " +
                "locationX, locationY, locationName from WORKERS ORDER BY name DESC");

        if (rs.next()) {
            if (name.compareTo(rs.getString("name")) > 0) {
                addElement(creator, id, name, coordinatesX, coordinatesY, creationDate, salary, startDate, endDate,
                        position, organizationAnnualTurnover, organizationType, addressZipCode, locationX,
                        locationY, locationName);
            }
        }
    }

    public void addMinElement(String creator, int id, String name, int coordinatesX, int coordinatesY,
                              ZonedDateTime creationDate, float salary, LocalDateTime startDate, Date endDate,
                              Position position, int organizationAnnualTurnover, OrganizationType organizationType,
                              String addressZipCode, int locationX, int locationY, String locationName) throws SQLException {

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT creator, id, name, coordinatesX, coordinatesY, creationDate, " +
                "salary, startDate, endDate, position, organizationAnnualTurnover, organizationType, addressZipCode, " +
                "locationX, locationY, locationName from WORKERS ORDER BY name ASC");

        if (rs.next()) {
            if (name.compareTo(rs.getString("name")) < 0) {
                addElement(creator, id, name, coordinatesX, coordinatesY, creationDate, salary, startDate, endDate,
                        position, organizationAnnualTurnover, organizationType, addressZipCode, locationX,
                        locationY, locationName);
            }
        }
    }

    public boolean isEmpty() throws SQLException {
        boolean isEmpty = true;
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT creator, id, name, coordinatesX, coordinatesY," +
                "creationDate, salary, startDate, endDate, position, organizationAnnualTurnover," +
                "organizationType, addressZipCode, locationX, locationY, locationName from WORKERS");
        if (rs.next())
            isEmpty = false;

        return isEmpty;
    }

    public void clear() throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("DELETE FROM workers WHERE id >= 0");
    }

    public String readCollectionDescending() throws SQLException {
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

        return result;
    }

    public void removePosition(Position position) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("DELETE FROM workers WHERE position = '" + position.toString() + "'");
    }

    public void removeSalary(float salary) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT creator, id, name, coordinatesX, coordinatesY, creationDate, " +
                "salary, startDate, endDate, position, organizationAnnualTurnover, organizationType, addressZipCode, " +
                "locationX, locationY, locationName from WORKERS WHERE salary = " + salary);

        if (rs.next())
            statement.executeUpdate("DELETE FROM workers WHERE id = " + rs.getInt("id"));
    }

    public void removeId(int id) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("DELETE FROM workers WHERE id = " + id);
    }

    public void removeGreater(String name) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("DELETE FROM workers WHERE name > '" + name + "'");
    }

    public String readCollection() throws SQLException {
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
        return result;
    }

    public void updateElement(int id, String name, int coordinatesX, int coordinatesY,
                              ZonedDateTime creationDate, float salary, LocalDateTime startDate, Date endDate,
                              Position position, int organizationAnnualTurnover, OrganizationType organizationType,
                              String addressZipCode, int locationX, int locationY, String locationName) throws SQLException {
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
                locationName + "' WHERE id = " + id);

    }

}