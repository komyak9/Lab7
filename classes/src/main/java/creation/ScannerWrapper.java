package creation;

import java.io.Serializable;
import java.util.Scanner;

/**
 * Класс-обёртка Scanner для реализации дополнительного условия ввода данных.
 *
 * @autor komyak9
 */
public class ScannerWrapper implements Serializable {
    private final Scanner scanner;

    public ScannerWrapper(Scanner scanner) {
        this.scanner = scanner;
    }

    public String readData() {
        String line = null;
        try {
            if (!scanner.hasNextLine())
                throw new Exception("There is no line. Program's going to be finished.");

            line = scanner.nextLine();
            if (line.equals(""))
                line = null;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.exit(0);
        }
        return line;
    }

    public void close() {
        scanner.close();
    }
}
