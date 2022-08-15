import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import static java.lang.Character.getNumericValue;

public class Game {
    private
    String[][] x_table;
    String[][] answers;
    String[][] gameTable;
    String[] A;
    String[] B;
    ArrayList<String> words;
    private int level; // 4 or 8, easy or hard
    private int score;
    private int attemptsLeft;
    private int time;

    public Game(ArrayList<String> words,int level){

        switch (level) {
            case DIFFICULTY.EASY:{
                this.words=new ArrayList<String>(4);
                Collections.shuffle(words);
                for (int i=0;i<4;i++){
                    this.words.add(words.get(i));
                }
            }break;
            case DIFFICULTY.HARD:{
                Collections.shuffle(words);
                this.words=new ArrayList<String>(8);
                for (int i=0;i<8;i++){
                    this.words.add(words.get(i));
                }
            }
        }
            A = this.words.toArray(new String[0]);
            Collections.shuffle(this.words);
            B = this.words.toArray(new String[0]);
            attemptsLeft =DIFFICULTY.EASY_ATTEMPTS;

            this.level=level;
            attemptsLeft = level==DIFFICULTY.EASY ? DIFFICULTY.EASY_ATTEMPTS : DIFFICULTY.HARD_ATTEMPTS;
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

    public int getAttemptsLeft() {
        return attemptsLeft;
    }

    public void setAttemptsLeft(int attemptsLeft) {
        this.attemptsLeft = attemptsLeft;
    }

    public  void decreaseAttemptsLeft () {
        this.attemptsLeft-=1;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void printResult(){
        System.out.println("-------------------------------------");
        String mode = level==DIFFICULTY.EASY ? "easy" : "hard";
        System.out.println("Level: "+mode);
        System.out.println("Guess chances: "+attemptsLeft+"\n");
        String s="";
        for(int i=0;i<3;i++){
            for(int k=0;k<level+1;k++){
                s+= x_table[i][k];
                s+=" ";
            }
            System.out.println(s);
            s="";
        }
        System.out.println("-------------------------------------");
    }
    public void printAnswers(){
        System.out.println("-------------------------------------");
        String mode = level==DIFFICULTY.EASY ? "easy" : "hard";
        System.out.println("Level: "+mode);
        System.out.println("Guess chances: "+attemptsLeft+"\n");
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
        System.out.println("Guess chances: "+attemptsLeft+"\n");
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
        gameTable[i][pos]=x_table[i][pos];

    }

    public String uncover (String coordinates){
        System.out.println(coordinates);
        int i = coordinates.charAt(0) == 'A' ? 1 : 2;
        int pos = getNumericValue(coordinates.charAt(1));
        gameTable[i][pos]=answers[i][pos];
        return gameTable[i][pos];
    }

    boolean check (String cor1, String cor2){
        boolean result=false;
        int i1 = cor1.charAt(0) == 'A' ? 1 : 2;
        int pos1 = getNumericValue(cor2.charAt(1));
        int i2 = cor1.charAt(0) == 'A' ? 1 : 2;
        int pos2 = getNumericValue(cor2.charAt(1));

        if(answers[i1][pos1].equals(answers[i2][pos2])){
            uncover(cor2); return result=true;
        }
        cover(cor1);
        return result;
    }

    static class pair{
        static String guess1;
        static String guess2;
    }
}
