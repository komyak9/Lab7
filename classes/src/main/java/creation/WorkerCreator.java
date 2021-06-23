package creation;

import content.*;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class WorkerCreator implements Serializable {
    public Worker createWorker(Scanner scanner) throws Exception {
        FieldsWrapper wrapper = collectData(scanner);

        Location location = new Location(wrapper.getLocationX(), wrapper.getLocationY(), wrapper.getLocationName());
        Address address = new Address(wrapper.getZipCode(), location);
        Organization organization = new Organization(wrapper.getAnnualTurnover(), wrapper.getOrganizationType(), address);
        Coordinates coordinates = new Coordinates(wrapper.getCoordinatesX(), wrapper.getCoordinatesY());
        return new Worker(0, ZonedDateTime.now(), wrapper.getWorkerName(), coordinates, wrapper.getSalary(),
                wrapper.getStartDate(), wrapper.getEndDate(), wrapper.getPosition(), organization);
    }

    private FieldsWrapper collectData(Scanner scanner) {
        ScannerWrapper scannerWrapper = new ScannerWrapper(scanner);
        FieldsWrapper wrapper = new FieldsWrapper();
        boolean isFilled = false;

        while (!isFilled) {
            try {
                System.out.println("Enter a name: ");
                wrapper.setWorkerName(scannerWrapper.readData());
                isFilled = true;
            } catch (Exception ex) {
                System.out.println(ex.getMessage() + "\nPlease, try again: ");
            }
        }
        isFilled = false;

        System.out.println("Enter coordinates: ");
        while (!isFilled) {
            try {
                System.out.println("Enter \"x\" value: ");
                wrapper.setCoordinatesX(Long.parseLong(scannerWrapper.readData()));
                isFilled = true;
            } catch (Exception ex) {
                System.out.println(ex.getMessage() + "\nPlease, try again: ");
            }
        }
        isFilled = false;
        while (!isFilled) {
            try {
                System.out.println("Enter \"y\" value: ");
                wrapper.setCoordinatesY(Integer.parseInt(scannerWrapper.readData()));
                isFilled = true;
            } catch (Exception ex) {
                System.out.println(ex.getMessage() + "\nPlease, try again: ");
            }
        }
        isFilled = false;

        while (!isFilled) {
            try {
                System.out.println("Enter salary: ");
                wrapper.setSalary(Float.parseFloat(scannerWrapper.readData()));
                isFilled = true;
            } catch (Exception ex) {
                System.out.println(ex.getMessage() + " Please, try again: ");
            }
        }
        isFilled = false;

        while (!isFilled) {
            try {
                System.out.println("Enter start date (example: 1986-04-08 12:30):");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                wrapper.setStartDate(LocalDateTime.parse(scannerWrapper.readData(), formatter));
                isFilled = true;
            } catch (Exception ex) {
                System.out.println(ex.getMessage() + " Please, try again: ");
            }
        }
        isFilled = false;

        while (!isFilled) {
            try {
                System.out.println("Enter end date (example: 04/08/1999): ");
                String line = scannerWrapper.readData();
                if (line == null)
                    wrapper.setEndDate(null);
                else
                    wrapper.setEndDate(new SimpleDateFormat("dd/MM/yyyy").parse(line));
                isFilled = true;
            } catch (Exception ex) {
                System.out.println(ex.getMessage() + "\nPlease, try again: ");
            }
        }
        isFilled = false;

        while (!isFilled) {
            try {
                System.out.println("Enter position, choose from a list: ");
                for (Position positions : Position.values())
                    System.out.print(positions + " ");

                System.out.println();
                wrapper.setPosition(Position.valueOf(scannerWrapper.readData()));
                isFilled = true;
            } catch (Exception ex) {
                System.out.println(ex.getMessage() + "\nPlease, try again: ");
            }
        }
        isFilled = false;

        System.out.println("Enter info about organization: ");
        while (!isFilled) {
            try {
                System.out.println("Enter annual turnover: ");
                wrapper.setAnnualTurnover(Integer.parseInt(scannerWrapper.readData()));
                isFilled = true;
            } catch (Exception ex) {
                System.out.println(ex.getMessage() + "\nPlease, try again: ");
            }
        }
        isFilled = false;
        while (!isFilled) {
            try {
                System.out.println("Enter organization type, choose from a list: ");
                for (OrganizationType organizationType : OrganizationType.values()) {
                    System.out.print(organizationType + " ");
                }
                System.out.println();
                wrapper.setOrganizationType(OrganizationType.valueOf(scannerWrapper.readData()));
                isFilled = true;
            } catch (Exception ex) {
                System.out.println(ex.getMessage() + "\nPlease, try again: ");
            }
        }
        isFilled = false;

        System.out.println("Enter official address of the organization: ");
        while (!isFilled) {
            try {
                System.out.println("Enter zip code: ");
                wrapper.setZipCode(scannerWrapper.readData());
                isFilled = true;
            } catch (Exception ex) {
                System.out.println(ex.getMessage() + "\nPlease, try again: ");
            }
        }
        isFilled = false;

        System.out.println("Enter location of the organization: ");
        while (!isFilled) {
            try {
                System.out.println("Enter \"x\" value: ");
                wrapper.setLocationX(Integer.parseInt(scannerWrapper.readData()));
                isFilled = true;
            } catch (Exception ex) {
                System.out.println(ex.getMessage() + "\nPlease, try again: ");
            }
        }
        isFilled = false;
        while (!isFilled) {
            try {
                System.out.println("Enter \"y\" value: ");
                wrapper.setLocationY(Integer.parseInt(scannerWrapper.readData()));
                isFilled = true;
            } catch (Exception ex) {
                System.out.println(ex.getMessage() + "\nPlease, try again: ");
            }
        }
        isFilled = false;

        while (!isFilled) {
            try {
                System.out.println("Enter name of the town: ");
                wrapper.setLocationName(scannerWrapper.readData());
                isFilled = true;
            } catch (Exception ex) {
                System.out.println(ex.getMessage() + "\nPlease, try again: ");
            }
        }

        return wrapper;
    }
}
