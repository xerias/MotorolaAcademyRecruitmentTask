import java.io.*;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class main {
    public static void main(String[] args) {
        new main();
    }

    //==================================================================================================================
    ArrayList<String> words = null;
    ArrayList<Record> records;
    //String absolutePathtoFile = "C:\\Users\\Dell\\Documents\\Motorola - recruitment task Java\\words.txt";
    String relativePathToFile = "words.txt";
    String recordsHeader;

    public main() {
        Scanner sc=new Scanner(System.in);
        getShuffledWords();
        records=new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.append("NAME      |");
        sb.append("DATE          |");
        sb.append("GUESSING TIME   |");
        sb.append("GUESSING TRIES");
        sb.append("\r\n");
        recordsHeader=sb.toString();
        getRecords(records);
        Game game = new Game(words, setDiffculty());

        while(true){
        game.printGameTable();
        playGame(game);
        System.out.println("Do You want to play again?");

        if(getUserInput(new String[]{"^yes|YES$|no|NO"},new String[]{"Type yes or no"}).equals("YES")){
            getShuffledWords();
            game=new Game(words,setDiffculty());
        }else{
            break;
        }
        }






    }

    //==================================================================================================================
    private int setDiffculty() {
        boolean loop = true;
        Scanner input = new Scanner(System.in);
        int guess_cnt = 0;
        System.out.println("Choose difficulty. Type easy (4 pairs) or hard (8 pairs)");
        while (loop) {
            String response = input.nextLine();
            if (response.toLowerCase(Locale.ROOT).equals("easy")) {
                //----------------------------------------------------------------------------------------------;
                return DIFFICULTY.EASY;
            } else {
                if (response.toLowerCase(Locale.ROOT).equals("hard")) {
                    //------------------------------------------------------------------------------------------;
                    return DIFFICULTY.HARD;
                } else {
                    guess_cnt++;
                    if (guess_cnt < 2) System.out.println("Not recognized.Try again");
                    else {
                        System.out.println("Sorry, can't recognize Your reply. Game level set to EASY");
                        loop = false;
                    }
                }
            }
        }
        return DIFFICULTY.EASY;
    }

    ArrayList<String> getWordsArray(String path) throws IOException {
        FileInputStream fis = new FileInputStream(path);
        ArrayList<String> words = new ArrayList<String>();
        int c;
        c = fis.read();
        if (c == -1) return null;
        //-------------------------------------------------------
        String w = "";
        do {
            if (c == '\r') {
                words.add(w);
                fis.skip(1); // ommiting "\n" character
                w = "";
            } else {
                w += (char) c;

            }
            c = fis.read();
        } while (c != -1);
        return words;
    }

    String getCoordinate() {
        boolean correct = false;
        String input = "";
        while (!correct) {
            Scanner sc = new Scanner(System.in);
            input = sc.nextLine();
            String cor_pt = "^\\w\\d$";
            Pattern pt = Pattern.compile(cor_pt);
            Matcher mt = pt.matcher(input);
            if (mt.matches()) correct=true;
            else {
                System.out.println("Coordinates not recognized. Use Only A or B and digits 1-8");
            }
        }
        return input.toUpperCase(Locale.ROOT);
    }
    String getUserInput(String[] patterns, String[] hints){
        boolean correct = false;
        String input = "";
        while (!correct) {
            Scanner sc = new Scanner(System.in);
            input = sc.nextLine();
            //String cor_pt = "^\\w\\d$";
            Pattern pt;
            Matcher mt;
            for (int i=0;i<patterns.length;i++) {
                pt=Pattern.compile(patterns[i]);
                mt=pt.matcher(input);
                if (mt.matches()) correct=true;
                else System.out.println("Input doesn't match expected pattern. "+ hints[i]);
            }
        }
        return input.toUpperCase(Locale.ROOT);

    }

    void getShuffledWords(){
        try {
            words = getWordsArray(relativePathToFile);
            Collections.shuffle(words);
            //System.out.println(words);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void getRecords(ArrayList<Record> r){
        FileWriter fw = null;
        BufferedWriter bw=null;
        FileReader fr = null;
        BufferedReader br = null;
        File records = new File("Records.txt");
        if(!records.exists()) {
            try {
                records.createNewFile();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            try {
                fr = new FileReader(records);
                br = new BufferedReader(fr);
                int lcnt=0;
                String s="";
                String[] data;
                while((s=br.readLine())!= null){
                    lcnt++;
                    if(lcnt>1){ //got some records
                        data=s.split("\\|");
                        //System.out.println(data);
                        Record record= new Record(data[0].trim(),data[1].trim(),Integer.parseInt(data[2].trim()),Integer.parseInt(data[3].trim()));
                        r.add(record);
                    }
                }
                br.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    //sort by guessing tries
       Collections.sort(r,new RecordsAttemptsComparator().
               thenComparing(new RecordsGuessingTimeComparator()));

    }
    void playGame(Game g){
        LocalDateTime timePt1 = LocalDateTime.now();
        while(g.getAttempts()<g.getAttemptsLimit()) {
            g.printAnswers();
            String card1, card2;
            String[] pt= new String [1];
            String[] h=new String [1];
            System.out.println("Give first coordinate");
            pt[0]=g.getLevel()==DIFFICULTY.EASY ? "^\\w[1-4]$" : "^\\w[1-8]$";
            h[0]=g.getLevel()==DIFFICULTY.EASY ? "Use A or B with 1-4. Example: B3" : "Use A or B with 1-8. Example: B6";
            card1 = getUserInput(pt, h);
            g.uncover(card1);
            g.printGameTable();
            System.out.println("Give second coordinate");
            card2 = getUserInput(pt, h);
            g.uncover(card2);
            g.printGameTable();
            g.increaseAttempts();
            if(g.check(card1, card2)){
                g.increasePoints();
                if(g.getScore()==g.getMaxScore()){
                    break;
                }
            }
            g.printGameTable();
        }
        LocalDateTime timePt2 = LocalDateTime.now();
        g.setTime((int)Duration.between(timePt1,timePt2).getSeconds());
        if(g.getScore()<g.getMaxScore()) System.out.println("Unfortunately you have lost. Try again!");
        else System.out.println("Congratulations! You won");
        g.printGameTable();

        System.out.println("Total attempts: "+g.getAttempts());
        System.out.println("Points scored: "+g.getScore());
        System.out.println("Time played: "+ g.getTime()+" [s]");
        StringBuilder sb = new StringBuilder();
        DecimalFormat mFormat = new DecimalFormat("00");
        sb.append(timePt1.getYear()+"-"+mFormat.format(timePt1.getMonthValue())+"-"+timePt1.getDayOfMonth());
        System.out.println("Date: "+sb.toString());
        Record r= new Record(sb.toString(),g.getTime(),g.getAttempts());
        Comparator comp = new RecordsAttemptsComparator().thenComparing(new RecordsGuessingTimeComparator());

        if( comp.compare(r,records.get(records.size()-1))<0){ // better than last record

                System.out.println("You are in the best 10! Please enter Your name");
                String name = getUserInput(new String[]{"\\w.{1,10}"},new String[]{"Enter Your name"});
                if(records.size()>9) records.remove(records.size()-1);
                r.pName=name;
                records.add(r);
                Collections.sort(records,comp);
        }
        System.out.println("Top scores table:");
        System.out.println(recordsHeader);
        for (Record rr: records){
            System.out.println(rr.toString());
        }

        //writing data to file
        FileWriter fw = null;
        BufferedWriter bw=null;

        File file = new File("Records.txt");
        try {
            fw = new FileWriter(file);
            bw= new BufferedWriter(fw);
            bw.write(recordsHeader);
            for (Record rr: records){
                bw.write(rr.toString());
                bw.write("\r\n");
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }








    }


}

