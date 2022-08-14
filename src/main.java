import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;


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


