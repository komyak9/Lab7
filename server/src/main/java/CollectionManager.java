import commands.Command;
import content.*;
import creation.FieldsWrapper;
import creation.IdGenerator;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

public class CollectionManager {
    private final IdGenerator idGenerator = new IdGenerator();  /////////////////////////////////////
    private final DBInteraction dbInteraction;
    private final LinkedList<Worker> workersList;
    private String creatorName = null;

    public CollectionManager(DBInteraction dbInteraction) {
        this.dbInteraction = dbInteraction;
        workersList = new LinkedList<>();
        fillCollection();
    }

    public String execute(Command<?> command) {
        command.setIdGenerator(idGenerator);  ////////////////////////////////////////////////////////
        command.setDbInteractionCommands(dbInteraction.getConnection(), creatorName);
        System.out.println(creatorName);
        String authorizationMessage = dbInteraction.validateUser(command.getUser());
        command.execute(workersList);
        command.setMessage(authorizationMessage + "\n" + command.getMessage());
        return command.getMessage();
    }

    private void fillCollection() {
        try (Statement statement = dbInteraction.getConnection().createStatement();
             ResultSet rs = statement.executeQuery("SELECT creator, id, name, coordinatesX, coordinatesY," +
                     "creationDate, salary, startDate, endDate, position, organizationAnnualTurnover," +
                     "organizationType, addressZipCode, locationX, locationY, locationName from WORKERS")) {

            System.out.println("Filling up the collection...");
            FieldsWrapper wrapper = new FieldsWrapper();
            while (rs.next()) {
                creatorName = rs.getString("creator");
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


                workersList.add(new Worker(wrapper.getId(), wrapper.getCreationDate(), wrapper.getWorkerName(),
                        coordinates, wrapper.getSalary(), wrapper.getStartDate(), wrapper.getEndDate(),
                        wrapper.getPosition(), organization));
            }
            System.out.println("Data from the database was successfully downloaded.");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public LinkedList<Worker> getWorkersList() {
        return workersList;
    }
}