package creation;

import content.OrganizationType;
import content.Position;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Класс для обработки полей создаваемых объектов.
 *
 * @autor komyak9
 */
public class FieldsWrapper implements Serializable {
    private int id;
    private String workerName; //Поле не может быть null, Строка не может быть пустой
    private float salary; //Значение поля должно быть больше 0
    private ZonedDateTime creationDate;
    private LocalDateTime startDate; //Поле не может быть null
    private Date endDate = null; //Поле может быть null
    private Position position; //Поле не может быть null

    private long coordinatesX; //Значение поля должно быть больше -690
    private int coordinatesY; //Значение поля должно быть больше -247

    private Integer locationX; //Поле не может быть null
    private int locationY;
    private String locationName = null; //Длина строки не должна быть больше 429, Поле может быть null

    private int annualTurnover; //Значение поля должно быть больше 0
    private OrganizationType organizationType; //Поле не может быть null

    private String zipCode; //Длина строки должна быть не меньше 6, Поле не может быть null

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getLocationX() {
        return locationX;
    }

    public void setLocationX(Integer x) throws Exception {
        if (x == null)
            throw new Exception("Error. \"x\" can't be null.");
        locationX = x;
    }

    public int getLocationY() {
        return locationY;
    }

    public void setLocationY(int y) {
        locationY = y;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String name) throws Exception {
        if (name == null || name.length() <= 429) {
            locationName = name;
        } else {
            throw new Exception("Wrong length. Name length must me <= 429.");
        }
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) throws Exception {
        if (salary <= 0)
            throw new Exception("Salary can't be 0 or lower.");
        this.salary = salary;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String name) throws Exception {
        if (name == null)
            throw new Exception("Error. Name of the worker can't be null.");
        else if (name.equals(""))
            throw new Exception("Error. Worker's name can't be empty.");
        else
            this.workerName = name;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) throws Exception {
        if (startDate == null)
            throw new Exception("Error. Start date of the worker can't be null.");
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

    public void setPosition(Position position) throws Exception {
        if (position == null)
            throw new Exception("Error. Position of the worker can't be null.");
        this.position = position;
    }

    public long getCoordinatesX() {
        return coordinatesX;
    }

    public void setCoordinatesX(long x) throws Exception {
        if (x > -690)
            coordinatesX = x;
        else
            throw new Exception("Wrong value. Coordinate \"x\" must be > -690.");
    }

    public int getCoordinatesY() {
        return coordinatesY;
    }

    public void setCoordinatesY(int y) throws Exception {
        if (y > -247)
            coordinatesY = y;
        else
            throw new Exception("Wrong value. Coordinate \"y\" must be > -247.");
    }

    public int getAnnualTurnover() {
        return annualTurnover;
    }

    public void setAnnualTurnover(int annualTurnover) throws Exception {
        if (annualTurnover > 0)
            this.annualTurnover = annualTurnover;
        else
            throw new Exception("Value is not suitable. Annual turnover must be > 0.");
    }

    public OrganizationType getOrganizationType() {
        return organizationType;
    }

    public void setOrganizationType(OrganizationType type) throws Exception {
        if (type == null)
            throw new Exception("Error. Type of the organization can't be null.");
        organizationType = type;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) throws Exception {
        if (zipCode == null)
            throw new Exception("Error. Zip code can't be null.");
        else if (zipCode.length() >= 6)
            this.zipCode = zipCode;
        else
            throw new Exception("Length is not suitable. Zip code must has >= 6 symbols.");
    }
}
