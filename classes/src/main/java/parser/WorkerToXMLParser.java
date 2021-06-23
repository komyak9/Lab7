package parser;

import content.Worker;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public class WorkerToXMLParser {
    public String parse(Worker worker) throws IOException {
        String result = tabCount(1) + "<worker>\n";
        result += writeSingleElement("name", worker.getName(), 2);
        result += writeSingleElement("id", String.valueOf(worker.getId()), 2);
        result += writeSingleElement("creationDate", worker.getCreationDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a z")), 2);
        result += tabCount(2) + "<coordinates>\n";
        result += writeSingleElement("x", String.valueOf(worker.getCoordinates().getX()), 3);
        result += writeSingleElement("y", String.valueOf(worker.getCoordinates().getY()), 3);
        result += tabCount(2) + "</coordinates>\n";
        result += writeSingleElement("salary", String.valueOf(worker.getSalary()), 2);
        result += writeSingleElement("startDate", worker.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), 2);

        if (!(worker.getEndDate() == null))
            result += writeSingleElement("endDate", new SimpleDateFormat("dd-MM-yyyy").format(worker.getEndDate()), 2);

        result += writeSingleElement("position", worker.getPosition().toString(), 2);
        result += tabCount(2) + "<organization>\n";
        result += writeSingleElement("annualTurnover", String.valueOf(worker.getOrganization().getAnnualTurnover()), 3);
        result += writeSingleElement("type", worker.getOrganization().getType().toString(), 3);
        result += tabCount(3) + "<address>\n";
        result += writeSingleElement("zipCode", worker.getOrganization().getOfficialAddress().getZipCode(), 4);
        result += tabCount(4) + "<location>\n";
        result += writeSingleElement("x", String.valueOf(worker.getOrganization().getOfficialAddress().getTown().getX()), 5);
        result += writeSingleElement("y", String.valueOf(worker.getOrganization().getOfficialAddress().getTown().getY()), 5);

        if (!(worker.getOrganization().getOfficialAddress().getTown().getName() == null))
            result += writeSingleElement("name", worker.getOrganization().getOfficialAddress().getTown().getName(), 5);

        result += tabCount(4) + "</location>\n";
        result += tabCount(3) + "</address>\n";
        result += tabCount(2) + "</organization>\n";
        result += tabCount(1) + "</worker>\n";
        return result;
    }

    private String writeSingleElement(String tag, String value, int tabCount) throws IOException {
        return tabCount(tabCount) + '<' + tag + '>' + value + "</" + tag + ">\n";
    }

    private String tabCount(int count) {
        String line = "";
        for (int i = 0; i < count; i++) {
            line += '\t';
        }
        return line;
    }
}