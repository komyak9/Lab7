package creation;

import java.io.Serializable;
import java.util.HashSet;

/**
 * Класс для хранения и генерирования id работников.
 *
 * @autor komyak9
 */
public class IdGenerator implements Serializable {
    private final HashSet<Integer> idSet = new HashSet<>();

    public int generateId() throws Exception {
        int id;

        if (idSet.size() == Integer.MAX_VALUE)
            throw new Exception("The number of available id is empty.");

        do {
            id = (int) (Math.random() * Integer.MAX_VALUE + 1);
        } while (idSet.contains(id));

        this.addId(id);
        return id;
    }

    public HashSet<Integer> getIdSet() {
        return idSet;
    }

    public void addId(int id) {
        idSet.add(id);
    }

    public void remove(int id) {
        idSet.remove(id);
    }

    public void clear() {
        idSet.clear();
    }
}
