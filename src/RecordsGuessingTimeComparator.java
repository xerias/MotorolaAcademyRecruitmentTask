import java.util.Comparator;

public class RecordsGuessingTimeComparator implements Comparator<Record> {
    @Override
    public int compare(Record r1, Record r2) {
        return Integer.compare(r1.gTime,r2.gTime);
    }

}
