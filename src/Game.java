import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import static java.lang.Character.getNumericValue;

public class Game {
    String[][] x_table;
    String[][] answers;
    String[][] gameTable;
    String[] A;
    String[] B;
    ArrayList<String> dWords;
    private int level; // 4 or 8, easy or hard
    private int score;
    private int maxScore;
    private int attempts;
    private final int attemptsLimit;
    private int time;
    LocalDateTime date;
    private String playerName;

    Date dateStart, dateFinish;



    public Game(ArrayList<String> words, int level){
            dateStart= new java.util.Date();
            this.level=level;
            maxScore=level;
            attemptsLimit = level==DIFFICULTY.EASY ? DIFFICULTY.EASY_ATTEMPTS : DIFFICULTY.HARD_ATTEMPTS;
            attempts=0;
            time=0;
            A= new String[level];
            B= new String[level];
            dWords= new ArrayList<>(2*level);
            for (int i=0;i<level;i++){
                this.dWords.add(words.get(i));
                this.dWords.add(words.get(i));
            }
            Collections.shuffle(dWords);
            for (int i=0;i<level;i++){
                A[i]=dWords.get(i);
                B[i]=dWords.get(i+level);
            }


            this.x_table = new String[3][level+1];
            this.answers= new String[3][level+1];
            this.gameTable= new String[3][level+1];
            gameTable[0][0]=answers[0][0]= x_table[0][0]=" ";

            for(int i=1;i<=level;i++) answers[0][i]= x_table[0][i]=Integer.toString(i);
            gameTable[1][0]=answers[1][0]= x_table[1][0]="A";
            gameTable[2][0]=answers[2][0]= x_table[2][0]="B";

            for(int i=1;i<=level;i++){
                int length = Math.max(A[i-1].length(),B[i-1].length());
                //coordinates builder
                StringBuilder sb= new StringBuilder(length);
                sb.append(i);
                sb.append(new String (new char[length-1]).replace("\0"," "));
                gameTable[0][i]=x_table[0][i]=sb.toString();
                gameTable[0][i]=answers[0][i]=sb.toString();

                //X...builder
                sb.replace(0,1,"X");
                gameTable[1][i]=answers[1][i]= x_table[1][i]=sb.toString();
                gameTable[2][i]=answers[2][i]= x_table[2][i]=sb.toString();

                //words from A and B, added whitespaces. Length adjusted to the highest in column
                answers[1][i]=String.join("",A[i-1],new String(new char[length-A[i-1].length()]).replace("\0"," "));
                answers[2][i]=String.join("",B[i-1],new String(new char[length-B[i-1].length()]).replace("\0"," "));

            }

    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = this.score == 0 ? score : throw_();

    }

    private int throw_() {
        throw new RuntimeException("variable is already set");
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public  void increaseAttempts() {
        this.attempts +=1;
    }
    public  void increasePoints() {
        this.score +=1;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getLevel() {
        return level;
    }
    public void printAnswers(){
        System.out.println("-------------------------------------");
        String s="";
        for(int i=0;i<3;i++){
            for(int k=0;k<level+1;k++){
                s+=answers[i][k];
                s+=" ";
            }
            System.out.println(s);
            s="";
        }
        System.out.println("-------------------------------------");
    }

    public void printGameTable(){
        System.out.println("-------------------------------------");
        String mode = level==DIFFICULTY.EASY ? "easy" : "hard";
        System.out.println("Level: "+mode);
        System.out.println("Guess chances: "+ (attemptsLimit-attempts));
        System.out.println("Points: "+getScore());
        String s="";
        for(int i=0;i<3;i++){
            for(int k=0;k<level+1;k++){
                s+=gameTable[i][k];
                s+=" ";
            }
            System.out.println(s);
            s="";
        }
        System.out.println("-------------------------------------");
    }


    public void cover(String coordinates){
        int i = coordinates.charAt(0) == 'A' ? 1 : 2;
        int pos = getNumericValue(coordinates.charAt(1));
        System.out.println(dontHavePair(coordinates));
        if(dontHavePair(coordinates)) gameTable[i][pos]=x_table[i][pos];

    }

    public String uncover (String coordinates){
        //System.out.println(coordinates);
        int i = coordinates.charAt(0) == 'A' ? 1 : 2;
        int pos = getNumericValue(coordinates.charAt(1));
        gameTable[i][pos]=answers[i][pos];
        return gameTable[i][pos];
    }

    boolean check (String cor1, String cor2){
        boolean result=false;
        int i1 = cor1.charAt(0) == 'A' ? 1 : 2;
        int pos1 = getNumericValue(cor1.charAt(1));
        int i2 = cor2.charAt(0) == 'A' ? 1 : 2;
        int pos2 = getNumericValue(cor2.charAt(1));
        //System.out.println(answers[i1][pos1]+"/");
        //System.out.println(answers[i2][pos2]+"/");

        if(cor1.equals(cor2)){
            cover(cor1);
            return false;
        }else{
            if(gameTable[i1][pos1].trim().equals(answers[i2][pos2].trim())){
                System.out.println("Correct GUESS!");
                //------------------------------------------------------------------------------
                uncover(cor2); return result=true;
            }else{
                cover(cor1);
            }
        }
        return result;
    }

    public int getAttemptsLimit() {
        return attemptsLimit;
    }

    public int getMaxScore() {
        return maxScore;
    }

    
    boolean dontHavePair(String cor){
        int r = cor.charAt(0) == 'A' ? 1 : 2;
        int c = getNumericValue(cor.charAt(1));
        for(int i=1;i<3;i++)
        for(int k=1;k<level+1;k++)
            if(r!=i && c!=k)
            if(gameTable[r][c].trim().equals(gameTable[i][k].trim())  ) return false;
        return true;
    }
}
