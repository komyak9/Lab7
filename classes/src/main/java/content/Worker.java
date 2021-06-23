package content;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Formatter;

/**
 * Класс для хранения информации о работнике.
 *
 * @autor komyak9
 */
public class Worker implements Comparable<Worker>, Serializable {
    private final Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private final ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private float salary; //Значение поля должно быть больше 0
    private LocalDateTime startDate; //Поле не может быть null
    private Date endDate; //Поле может быть null
    private Position position; //Поле не может быть null
    private Organization organization; //Поле не может быть null

    public Worker(Integer id, ZonedDateTime creationDate, String name, Coordinates coordinates, float salary, LocalDateTime startDate, Date endDate, Position position, Organization organization) throws Exception {
        this.creationDate = creationDate;

        if (name == null)
            throw new Exception("Error. Name of the worker can't be null.");
        else if (coordinates == null)
            throw new Exception("Error. Coordinates of the worker can't be null.");
        else if (startDate == null)
            throw new Exception("Error. Start date of the worker can't be null.");
        else if (position == null)
            throw new Exception("Error. Position of the worker can't be null.");
        else if (organization == null)
            throw new Exception("Error. Organization of the worker can't be null.");

        setName(name);
        setCoordinates(coordinates);
        setSalary(salary);
        setStartDate(startDate);
        setEndDate(endDate);
        setPosition(position);
        setOrganization(organization);
        this.id = id;
    }

    public int compareTo(Worker worker) {
        return name.compareTo(worker.getName());
    }

    public int getId() {
        return id;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) throws Exception {
        if (salary <= 0)
            throw new Exception("Salary can't be 0 or lower.");
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws Exception {
        if (name.equals(""))
            throw new Exception("Error. Worker's name can't be empty.");
        else
            this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @Override
    public String toString() {
        return "\nid: " + id + "\t\tName: " + name + "\t\tSalary: " + salary + "\t\tStart date: " +
                startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                "\t\tEnd date: " + new Formatter().format("%tD", endDate) +
                "\t\tPosition: " + position + "\t\tOrganization: " + organization.getType() +
                "\t\tCity name: " + organization.getOfficialAddress().getTown().getName();
    }
}
