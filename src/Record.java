import java.util.Comparator;

public class Record  {
    String pName;
    String date; //yyyy-mm-dd
    int gTime; // seconds
    int gTries;

    public Record(String pName, String date, int gTime, int gTries) {
        this.pName = pName;
        this.date = date;
        this.gTime = gTime;
        this.gTries = gTries;
    }

    public Record(String date, int gTime, int gTries) {
        this.date = date;
        this.gTime = gTime;
        this.gTries = gTries;
    }

    @Override
    public String toString() {
        StringBuilder sb= new StringBuilder();
        sb.append(pName+new String(new char[10-pName.length()]).replace("\0"," ")+"|");
        sb.append(date+"    "+"|");
        sb.append(gTime+new String(new char[16-String.valueOf(gTime).length()]).replace("\0"," ")+"|");
        sb.append(gTries+new String(new char[14-String.valueOf(gTries).length()]).replace("\0"," "));
        return sb.toString();
    }



}
