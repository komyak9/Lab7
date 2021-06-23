package content;

import java.io.Serializable;

/**
 * Класс для хранения информации об организации.
 *
 * @autor komyak9
 */
public class Organization implements Serializable {
    private int annualTurnover; //Значение поля должно быть больше 0
    private OrganizationType type; //Поле не может быть null
    private Address officialAddress; //Поле не может быть null

    public Organization(int annualTurnover, OrganizationType type, Address address) throws Exception {
        if (type == null)
            throw new Exception("Error. Type of the organization can't be null.");
        else if (address == null)
            throw new Exception("Error. content.Address of the organization can't be null.");

        setAnnualTurnover(annualTurnover);
        setType(type);
        setOfficialAddress(address);
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

    public OrganizationType getType() {
        return type;
    }

    public void setType(OrganizationType type) {
        this.type = type;
    }

    public Address getOfficialAddress() {
        return officialAddress;
    }

    public void setOfficialAddress(Address officialAddress) {
        this.officialAddress = officialAddress;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "annualTurnover=" + annualTurnover +
                ", type=" + type +
                ", officialAddress=" + officialAddress +
                '}';
    }
}
