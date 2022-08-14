import java.util.ArrayList;

public class Game {



    private
    String[][] result;
    String[] A;
    String[] B;
    private int level; // 4 or 8, easy or hard
    private int score;
    private int attempts;
    private int time;

    public Game(ArrayList<String> aWords,ArrayList<String> bWords,int level){
            A = aWords.toArray(new String[0]);
            B = bWords.toArray(new String[0]);
            this.level=level;
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
            for(int k=0;k<5;k++){
                s+=result[i][k];
                s+=" ";
            }
            System.out.println(s);
            s="";
        }
    }



}
