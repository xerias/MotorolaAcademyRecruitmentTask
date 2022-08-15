import java.io.FileInputStream;
import java.io.IOException;
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
    //String absolutePathtoFile = "C:\\Users\\Dell\\Documents\\Motorola - recruitment task Java\\words.txt";
    String relativePathToFile = "words.txt";

    public main() {
        Scanner sc=new Scanner(System.in);
        getShuffledWords();
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

    void playGame(Game g){
        LocalDateTime timePt1 = LocalDateTime.now();
        while(g.getAttempts()<g.getAttemptsLimit()) {
            //g.printAnswers();
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
            g.increaseAttempts();
            if(g.check(card1, card2)){
                g.increasePoints();
                if(g.getScore()==g.getMaxScore()){
                    System.out.println("Congratulations! You won");
                    break;
                }
            }
            g.printGameTable();
        }
        if(g.getScore()<g.getMaxScore()) System.out.println("Unfortunately you have lost. Try again!");
        //Print Summary
        g.printGameTable();
        LocalDateTime timePt2 = LocalDateTime.now();
        System.out.println("Total attempts: "+g.getAttempts());
        System.out.println("Points scored: "+g.getScore());
        System.out.println("Time played: "+ Duration.between(timePt1,timePt2).getSeconds()+" [s]");
        System.out.println("Day: "+timePt1.getDayOfMonth()+" Month: "+timePt1.getMonthValue()+ " Year: "+timePt1.getYear());

    }


}

