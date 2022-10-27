import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Translate_Normal {
    static ArrayList<String> Simps=new ArrayList<>();
    static ArrayList<String> Trads=new ArrayList<>();

    public static void init(){
        try {
            BufferedReader bufferedReader_simp= new BufferedReader(new FileReader("simp.txt"));
            BufferedReader bufferedReader_trad = new BufferedReader(new FileReader("trad.txt"));
            String Simp,Trad;
            while((Simp=bufferedReader_simp.readLine())!=null&&(Trad=bufferedReader_trad.readLine())!=null) {
                Simps.add(Simp);
                Trads.add(Trad);
            }
            BufferedReader bufferedReader=new BufferedReader(new FileReader("simplified.txt"));
            String read;
            while((read=bufferedReader.readLine())!=null){
                if(!read.contains("="))
                    continue;
                String[] split=read.split("=");
                Simps.add(split[1]);
                Trads.add(split[0]);
            }
            bufferedReader=new BufferedReader(new FileReader("traditional.txt"));
            while((read=bufferedReader.readLine())!=null){
                if(!read.contains("="))
                    continue;
                String[] split=read.split("=");
                Simps.add(split[0]);
                Trads.add(split[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String[] MM(String sentence){
        ArrayList<String> words=new ArrayList<>();
        for(int i=0;i<sentence.length();i++){
            boolean flag=false;
            for(int j=Math.min(4,sentence.length()-i);j>=1;j--){
                if(Trads.contains(sentence.substring(i,i+j))||Simps.contains(sentence.substring(i,i+j))) {
                    words.add(sentence.substring(i, i + j));
                    i+=j-1;
                    flag=true;
                    break;
                }
            }
            if(!flag){
                words.add(sentence.substring(i,i+1));
            }
        }
        String[] ret=new String[words.size()];
        words.toArray(ret);
        return ret;
    }

    public static String[] RMM(String sentence){
        ArrayList<String> words=new ArrayList<>();
        for(int i=sentence.length()-1;i>=0;i--){
            boolean flag=false;
            for(int j=Math.min(3,i);j>=0;j--){
                if(Trads.contains(sentence.substring(i-j,i+1))||Simps.contains(sentence.substring(i-j,i+1))) {
                    words.add(sentence.substring(i-j,i+1));
                    i-=j;
                    flag=true;
                    break;
                }
            }
            if(!flag){
                words.add(sentence.substring(i,i+1));
            }
        }
        Collections.reverse(words);
        String[] ret=new String[words.size()];
        words.toArray(ret);
        return ret;
    }

    public static String[] divide_sentence(String sentence){
        String[] MM_result=MM(sentence);
        String[] RMM_result=RMM(sentence);
        if(MM_result.length<=RMM_result.length)
            return MM_result;
        return RMM_result;
    }

    public static String translate_from_Simp_to_Trad(String sentence){
        String[] words=divide_sentence(sentence);
        StringBuilder stringBuilder=new StringBuilder();
        for (String word : words) {
            if(Simps.contains(word))
                stringBuilder.append(Trads.get(Simps.indexOf(word)));
            else
                stringBuilder.append(word);
        }
        return stringBuilder.toString();
    }

    public static String translate_from_Trad_to_Simp(String sentence){
        String[] words=divide_sentence(sentence);
        StringBuilder stringBuilder=new StringBuilder();
        for (String word : words) {
            if(Trads.contains(word))
                stringBuilder.append(Simps.get(Trads.indexOf(word)));
            else
                stringBuilder.append(word);
        }
        return stringBuilder.toString();
    }
}
