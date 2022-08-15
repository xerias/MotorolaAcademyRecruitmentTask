import java.util.Comparator;

public class RecordsAttemptsComparator implements Comparator<Record> {
    @Override
    public int compare(Record r1, Record r2) {
        return Integer.compare(r1.gTries,r2.gTries);
    }


}
