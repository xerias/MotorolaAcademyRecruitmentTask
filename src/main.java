import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;


public class main {
    public static void main(String[] args) {
        new main();
    }

    enum Difficulty {
        EASY,
        HARD
    }

    public main() {
        Difficulty difficulty = null;
        ArrayList<String> words;
        //String absolutePathtoFile = "C:\\Users\\Dell\\Documents\\Motorola - recruitment task Java\\words.txt";
        String relativePathToFile = "words.txt";

        try {
            words = getWordsArray(relativePathToFile);
            System.out.println(words);
        } catch (IOException e) {
            e.printStackTrace();
        }

        difficulty=setDiffculty(); // with user response

    }

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


