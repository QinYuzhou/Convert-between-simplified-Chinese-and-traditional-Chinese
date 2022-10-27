import java.io.*;
import java.util.*;

public class Translate_Trie {
    static class Trie_node{
        HashMap<Character,Trie_node> son;
        String trans;
        Trie_node(){
            son=new HashMap<>();
            trans="";
        }
    }
    static Trie_node root_S_T=new Trie_node();
    static Trie_node root_T_S=new Trie_node();
    static HashSet<String> SWords=new HashSet<>();
    static HashSet<String> TWords=new HashSet<>();
    public static void init(){
        try {
            BufferedReader bufferedReader_simp= new BufferedReader(new FileReader("simp.txt"));
            BufferedReader bufferedReader_trad = new BufferedReader(new FileReader("trad.txt"));
            String Simp,Trad;
            while((Simp=bufferedReader_simp.readLine())!=null&&(Trad=bufferedReader_trad.readLine())!=null) {
                SWords.add(Simp);
                TWords.add(Trad);
                Trie_node node=root_S_T;
                for(int i=0;i<Simp.length();i++) {
                    if (!node.son.containsKey(Simp.charAt(i)))
                        node.son.put(Simp.charAt(i), new Trie_node());
                    node = node.son.get(Simp.charAt(i));
                }
                node.trans=Trad;
                node=root_T_S;
                for(int i=0;i<Trad.length();i++) {
                    if (!node.son.containsKey(Trad.charAt(i)))
                        node.son.put(Trad.charAt(i), new Trie_node());
                    node = node.son.get(Trad.charAt(i));
                }
                node.trans=Simp;
            }
            BufferedReader bufferedReader=new BufferedReader(new FileReader("simplified.txt"));
            String read;
            while((read=bufferedReader.readLine())!=null){
                if(!read.contains("="))
                    continue;
                String[] split=read.split("=");
                TWords.add(split[0]);
                SWords.add(split[1]);
                Trie_node node=root_T_S;
                for(int i=0;i<split[0].length();i++) {
                    if (!node.son.containsKey(split[0].charAt(i)))
                        node.son.put(split[0].charAt(i), new Trie_node());
                    node = node.son.get(split[0].charAt(i));
                }
                node.trans=split[1];
                node=root_S_T;
                for(int i=0;i<split[1].length();i++) {
                    if (!node.son.containsKey(split[1].charAt(i)))
                        node.son.put(split[1].charAt(i), new Trie_node());
                    node = node.son.get(split[1].charAt(i));
                }
                node.trans=split[0];
            }
            bufferedReader=new BufferedReader(new FileReader("traditional.txt"));
            while((read=bufferedReader.readLine())!=null){
                if(!read.contains("="))
                    continue;
                String[] split=read.split("=");
                SWords.add(split[0]);
                TWords.add(split[1]);
                Trie_node node=root_S_T;
                for(int i=0;i<split[0].length();i++) {
                    if (!node.son.containsKey(split[0].charAt(i)))
                        node.son.put(split[0].charAt(i), new Trie_node());
                    node = node.son.get(split[0].charAt(i));
                }
                node.trans=split[1];
                node=root_T_S;
                for(int i=0;i<split[1].length();i++) {
                    if (!node.son.containsKey(split[1].charAt(i)))
                        node.son.put(split[1].charAt(i), new Trie_node());
                    node = node.son.get(split[1].charAt(i));
                }
                node.trans=split[0];
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
        Trie_node node;
        boolean flag;
        for (String word : words) {
            node=root_S_T;
            flag=true;
            for(int i=0;i<word.length();i++) {
                if (!node.son.containsKey(word.charAt(i))) {
                    flag = false;
                    break;
                }
                node=node.son.get(word.charAt(i));
            }
            if(flag&& !Objects.equals(node.trans, ""))
                stringBuilder.append(node.trans);
            else
                stringBuilder.append(word);
        }
        return stringBuilder.toString();
    }

    public static String translate_from_Trad_to_Simp(String sentence){
        String[] words=divide_sentence(sentence);
        StringBuilder stringBuilder=new StringBuilder();
        Trie_node node;
        boolean flag;
        for (String word : words) {
            node=root_T_S;
            flag=true;
            for(int i=0;i<word.length();i++) {
                if (!node.son.containsKey(word.charAt(i))) {
                    flag = false;
                    break;
                }
                node=node.son.get(word.charAt(i));
            }
            if(flag&& !Objects.equals(node.trans, ""))
                stringBuilder.append(node.trans);
            else
                stringBuilder.append(word);
        }
        return stringBuilder.toString();
    }
}

