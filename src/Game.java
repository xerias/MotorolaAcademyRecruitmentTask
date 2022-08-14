import java.util.ArrayList;
import java.util.Collections;

public class Game {
    private
    String[][] result;
    String[] A;
    String[] B;
    ArrayList<String> words;
    private int level; // 4 or 8, easy or hard
    private int score;
    private int attempts;
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

            attempts=DIFFICULTY.EASY_ATTEMPTS;
            this.level=level;
            attempts= level==DIFFICULTY.EASY ? DIFFICULTY.EASY_ATTEMPTS : DIFFICULTY.HARD_ATTEMPTS;
            this.result = new String[3][level+1];
            result [0][0]=" ";
            for(int i=1;i<=level;i++) result[0][i]=Integer.toString(i);
            result[1][0]="A";
            result[2][0]="B";
            for(int i=1;i<=level;i++){
            int length = Math.max(A[i-1].length(),B[i-1].length());
            StringBuilder sb= new StringBuilder(length);
            sb.append(i);
            sb.append(new String (new char[length-1]).replace("\0"," "));
            result[0][i]=sb.toString();
            sb.replace(0,1,"X");
            result[1][i]=sb.toString();
            result[2][i]=sb.toString();
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

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void printResult(){
        String s="";
        for(int i=0;i<3;i++){
            for(int k=0;k<level+1;k++){
                s+=result[i][k];
                s+=" ";
            }
            System.out.println(s);
            s="";
        }
    }
}
