package parser;

import content.*;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Класс для преобразования объектов Worker в xml-файл и наоборот.
 *
 * @autor komyak9
 */
public class WorkerParser implements Serializable {
    public LinkedList<Worker> getWorkersList(File file) throws Exception {
        Scanner scanner = new Scanner(file);
        XMLToObjectParser parser = new XMLToObjectParser();
        LinkedList<Worker> result = new LinkedList<>();
        ArrayList<String> fileData = new ArrayList<>();
        Date date = null;

        Field[] locationFields = Location.class.getDeclaredFields();
        HashMap<String, String> locationMap = new HashMap<>();
        Location location;

        Field[] addressFields = Address.class.getDeclaredFields();
        HashMap<String, String> addressMap = new HashMap<>();
        Address address;

        Field[] organizationFields = Organization.class.getDeclaredFields();
        HashMap<String, String> organizationMap = new HashMap<>();
        Organization organization;

        Field[] coordinatesFields = Coordinates.class.getDeclaredFields();
        HashMap<String, String> coordinatesMap = new HashMap<>();
        Coordinates coordinates;

        Field[] workerFields = Worker.class.getDeclaredFields();
        HashMap<String, String> workerMap = new HashMap<>();
        Worker worker;

        try {
            while (scanner.hasNextLine())
                fileData.add(scanner.nextLine());
        } catch (Error error) {
            System.out.println(error.getMessage());
        }

        try {
            for (int w = 2; !fileData.get(w).contains("</workers>"); ) {
                if (fileData.get(w).contains("worker")) {
                    fileData.remove(w);
                    for (int i = w; !fileData.get(w).contains("</worker>"); i++) {
                        if (fileData.get(i).contains("<location>")) {
                            for (int j = i + 1; !fileData.get(j).contains("</location>"); ) {
                                for (int k = 0; k < locationFields.length; k++) {
                                    if (fileData.get(j).contains('<' + locationFields[k].getName() + '>'))
                                        locationMap.put(locationFields[k].getName(),
                                                parser.getSingleValue(locationFields[k].getName(), fileData.get(j)));
                                }
                                fileData.remove(j);
                            }
                            fileData.remove(i);
                            fileData.remove(i);
                            break;
                        }
                    }

                    for (int i = w; !fileData.get(w).contains("</worker>"); i++) {
                        if (fileData.get(i).contains("<address>")) {
                            for (int j = i + 1; !fileData.get(j).contains("</address>"); ) {
                                for (int k = 0; k < addressFields.length; k++) {
                                    if (fileData.get(j).contains('<' + addressFields[k].getName() + '>'))
                                        addressMap.put(addressFields[k].getName(),
                                                parser.getSingleValue(addressFields[k].getName(), fileData.get(j)));
                                }
                                fileData.remove(j);
                            }
                            fileData.remove(i);
                            fileData.remove(i);
                            break;
                        }
                    }

                    for (int i = w; !fileData.get(w).contains("</worker>"); i++) {
                        if (fileData.get(i).contains("<organization>")) {
                            for (int j = i + 1; !fileData.get(j).contains("</organization>"); ) {
                                for (int k = 0; k < organizationFields.length; k++) {
                                    if (fileData.get(j).contains('<' + organizationFields[k].getName() + '>'))
                                        organizationMap.put(organizationFields[k].getName(),
                                                parser.getSingleValue(organizationFields[k].getName(), fileData.get(j)));
                                }
                                fileData.remove(j);
                            }
                            fileData.remove(i);
                            fileData.remove(i);
                            break;
                        }
                    }

                    for (int i = w; !fileData.get(w).contains("</worker>"); i++) {
                        if (fileData.get(i).contains("<coordinates>")) {
                            for (int j = i + 1; !fileData.get(j).contains("</coordinates>"); ) {
                                for (int k = 0; k < coordinatesFields.length; k++) {
                                    if (fileData.get(j).contains('<' + coordinatesFields[k].getName() + '>'))
                                        coordinatesMap.put(coordinatesFields[k].getName(),
                                                parser.getSingleValue(coordinatesFields[k].getName(), fileData.get(j)));
                                }
                                fileData.remove(j);
                            }
                            fileData.remove(i);
                            fileData.remove(i);
                            break;
                        }
                    }

                    for (int i = w; !fileData.get(w).contains("</worker>"); ) {
                        for (int k = 0; k < workerFields.length; k++) {
                            if (fileData.get(i).contains('<' + workerFields[k].getName() + '>')) {
                                workerMap.put(workerFields[k].getName(),
                                        parser.getSingleValue(workerFields[k].getName(), fileData.get(i)));
                            }
                        }
                        fileData.remove(i);
                    }

                    fileData.remove(w);


                    if (workerMap.get("endDate") != null)
                        date = new SimpleDateFormat("dd-MM-yyyy").parse(workerMap.get("endDate"));
                    else
                        date = null;

                    location = new Location(Integer.parseInt(locationMap.get("x")), Integer.parseInt(locationMap.get("y")),
                            locationMap.get("name"));
                    address = new Address(addressMap.get("zipCode"), location);
                    organization = new Organization(Integer.parseInt(organizationMap.get("annualTurnover")),
                            OrganizationType.valueOf(organizationMap.get("type")), address);
                    coordinates = new Coordinates(Long.parseLong(coordinatesMap.get("x")),
                            Integer.parseInt(coordinatesMap.get("y")));
                    worker = new Worker(Integer.parseInt(workerMap.get("id")),
                            ZonedDateTime.parse(workerMap.get("creationDate"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a z")), workerMap.get("name"),
                            coordinates, Float.parseFloat(workerMap.get("salary")),
                            LocalDateTime.parse(workerMap.get("startDate"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                            date, Position.valueOf(workerMap.get("position")), organization);
                    result.add(worker);
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw ex;
        }
        return result;
    }
}
