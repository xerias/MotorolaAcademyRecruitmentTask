import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;


public class main {
    public static void main(String[] args) {
        new main();
    }

    enum Difficulty {
        EASY,
        HARD
    }
//==================================================================================================================
    public main() {
        Difficulty difficulty = null;
        ArrayList<String> words=null   ;
        ArrayList<String> aWords=new ArrayList<String>(8); //random words
        ArrayList<String> bWords=new ArrayList<String>(8); //random words
        //String absolutePathtoFile = "C:\\Users\\Dell\\Documents\\Motorola - recruitment task Java\\words.txt";
        String relativePathToFile = "words.txt";

        try {
            words = getWordsArray(relativePathToFile);
            System.out.println(words);
        } catch (IOException e) {
            e.printStackTrace();
        }

        difficulty=setDiffculty(); // with user response
        switch (difficulty) {
            case EASY:{
                Collections.shuffle(words);
                for (int i=0;i<4;i++){
                    aWords.add(words.get(i));
                }
            }break;
            case HARD:{
                Collections.shuffle(words);
                for (int i=0;i<8;i++){
                    aWords.add(words.get(i));
                }
            }
        }
        bWords.addAll(aWords);
        Collections.shuffle(bWords);
        System.out.println(aWords);
        int cnt; //counts user guesses [0-10] in EASY MODE
        String[][] result = new String[3][9];
        result [0][0]=" ";
        for(int i=1;i<=4;i++) result[0][i]=Integer.toString(i);
        result[1][0]="A";
        result[2][0]="B";
        for(int i=1;i<=4;i++){
            int length = Math.max(aWords.get(i-1).length(),bWords.get(i-1).length());
            StringBuilder sb= new StringBuilder(length);
            sb.append(i);
            sb.append(new String (new char[length-1]).replace("\0"," "));
            result[0][i]=sb.toString();
            sb.replace(0,1,"X");
            result[1][i]=sb.toString();
            result[2][i]=sb.toString();
        }
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
    //==================================================================================================================
    private Difficulty setDiffculty() {
        boolean loop=true;
        Scanner input=new Scanner(System.in);
        int guess_cnt=0;
        System.out.println("Choose difficulty. Type easy (4 pairs) or hard (8 pairs)");
        while(loop){
            String response=input.nextLine();
            if(response.toLowerCase(Locale.ROOT).equals("easy")) {
                //----------------------------------------------------------------------------------------------;
                return Difficulty.EASY;
            }else{
                if(response.toLowerCase(Locale.ROOT).equals("hard")){
                    //------------------------------------------------------------------------------------------;
                    return Difficulty.HARD;
                }else{
                    guess_cnt++;
                    if (guess_cnt<2) System.out.println("Not recognized.Try again");
                    else{
                        System.out.println("Sorry, can't recognize Your reply. Game level set to EASY");
                        loop=false;
                    }
                }
            }
        }
        return Difficulty.EASY;
    }

    ArrayList <String> getWordsArray(String path) throws  IOException {
        FileInputStream fis= new FileInputStream(path);
        ArrayList <String> words=new ArrayList<String>();
        int c;
        c= fis.read();
        if(c==-1) return null;
        //-------------------------------------------------------
        String w ="";
        do{
            if(c=='\r'){
                words.add(w);
                fis.skip(1); // ommiting "\n" character
                w="";
            }else{
                w+=(char)c;

            }
            c= fis.read();
        } while (c!=-1);
        return words;
    }
}


