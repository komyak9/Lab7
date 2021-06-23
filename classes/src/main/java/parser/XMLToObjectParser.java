package parser;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс для преобразования xml-строк в данные объекта.
 *
 * @autor komyak9
 */
public class XMLToObjectParser implements Serializable {
    public String getSingleValue(String keyWord, String string) {
        Pattern pattern = Pattern.compile(">.*?<");
        Matcher matcher = pattern.matcher(string);
        String result = "";

        if (string.contains('<' + keyWord + '>') && string.contains("</" + keyWord + '>')) {
            while (matcher.find())
                result = string.substring(matcher.start(), matcher.end());
        }
        return result.substring(1, result.length() - 1);
    }

    public HashMap<String, String> getAttributes(String string) {
        HashMap<String, String> map = new HashMap<>();
        String key = "";
        String value = "";

        Pattern pattern = Pattern.compile(" .*?\">");
        Matcher matcher = pattern.matcher(string);
        String attributeLine = "";
        while (matcher.find()) {
            attributeLine = string.substring(matcher.start(), matcher.end());
            attributeLine = attributeLine.substring(1, attributeLine.length() - 1);
        }

        String[] attributes = attributeLine.trim().split("\" ");
        for (String attribute : attributes) {
            if (attribute.charAt(attribute.length() - 1) == '\"')
                attribute = attribute.substring(0, attribute.length() - 1);

            pattern = Pattern.compile(".*?=");
            matcher = pattern.matcher(attribute);
            while (matcher.find()) {
                key = attribute.substring(matcher.start(), matcher.end());
                key = key.substring(0, key.length() - 1);
            }
            pattern = Pattern.compile("=\".+");
            matcher = pattern.matcher(attribute);
            while (matcher.find()) {
                value = attribute.substring(matcher.start(), matcher.end());
                value = value.substring(2);
            }
            map.put(key, value);
        }
        return map;
    }

    public Map<String, String> getMultiValues(String keyWord, LinkedList<String> list, Integer startLine) {
        Map<String, String> result = new HashMap<>();
        String key = "";
        String value = "";
        Pattern pattern;
        Matcher matcher;

        for (int i = startLine + 1; !list.get(i).contains("</" + keyWord + '>'); i++) {
            pattern = Pattern.compile("<[^/].*?>");
            matcher = pattern.matcher(list.get(i));
            while (matcher.find()) {
                key = list.get(i).substring(matcher.start(), matcher.end());
                key = key.substring(1, key.length() - 1);
            }
            value = getSingleValue(key, list.get(i));
            result.put(key, value);
        }
        return result;
    }
}
