import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class Translate_Hash {
    static HashMap<String,String> Simp_Trad=new HashMap<>();
    static HashMap<String,String> Trad_Simp=new HashMap<>();
    static HashSet<String> TWords=new HashSet<>();
    static HashSet<String> SWords=new HashSet<>();
    public static void init(){
        try {
            BufferedReader bufferedReader_simp= new BufferedReader(new FileReader("simp.txt"));
            BufferedReader bufferedReader_trad = new BufferedReader(new FileReader("trad.txt"));
            String Simp,Trad;
            while((Simp=bufferedReader_simp.readLine())!=null&&(Trad=bufferedReader_trad.readLine())!=null) {
                Simp_Trad.put(Simp,Trad);
                Trad_Simp.put(Trad,Simp);
                SWords.add(Simp);
                TWords.add(Trad);
            }
            BufferedReader bufferedReader=new BufferedReader(new FileReader("simplified.txt"));
            String read;
            while((read=bufferedReader.readLine())!=null){
                if(!read.contains("="))
                    continue;
                String[] split=read.split("=");
                Simp_Trad.put(split[1],split[0]);
                Trad_Simp.put(split[0],split[1]);
                TWords.add(split[0]);
                SWords.add(split[1]);
            }
            bufferedReader=new BufferedReader(new FileReader("traditional.txt"));
            while((read=bufferedReader.readLine())!=null){
                if(!read.contains("="))
                    continue;
                String[] split=read.split("=");
                Simp_Trad.put(split[0],split[1]);
                Trad_Simp.put(split[1],split[0]);
                SWords.add(split[0]);
                TWords.add(split[1]);
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
                if(SWords.contains(sentence.substring(i,i+j))||TWords.contains(sentence.substring(i,i+j))) {
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
                if(SWords.contains(sentence.substring(i-j,i+1))||TWords.contains(sentence.substring(i-j,i+1))) {
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
            stringBuilder.append(Simp_Trad.getOrDefault(word, word));
        }
        return stringBuilder.toString();
    }

    public static String translate_from_Trad_to_Simp(String sentence){
        String[] words=divide_sentence(sentence);
        StringBuilder stringBuilder=new StringBuilder();
        for (String word : words) {
            stringBuilder.append(Trad_Simp.getOrDefault(word, word));
        }
        return stringBuilder.toString();
    }
}
