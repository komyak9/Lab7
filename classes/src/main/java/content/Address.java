package content;

import java.io.Serializable;

/**
 * Класс для хранения адреса места работы работника.
 *
 * @autor komyak9
 */
public class Address implements Serializable {
    private String zipCode; //Длина строки должна быть не меньше 6, Поле не может быть null
    private Location town; //Поле не может быть null

    public Address(String zipCode, Location town) throws Exception {
        if (zipCode == null)
            throw new Exception("Error. Zip code can't be null.");
        else if (town == null)
            throw new Exception("Error. content.Location can't be null.");

        setZipCode(zipCode);
        setTown(town);
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) throws Exception {
        if (zipCode.length() >= 6)
            this.zipCode = zipCode;
        else
            throw new Exception("Length is not suitable. Zip code must has >= 6 symbols.");
    }

    public Location getTown() {
        return town;
    }

    public void setTown(Location town) {
        this.town = town;
    }

    @Override
    public String toString() {
        return "Address{" +
                "zipCode=" + zipCode +
                ", town=" + town +
                '}';
    }
}
