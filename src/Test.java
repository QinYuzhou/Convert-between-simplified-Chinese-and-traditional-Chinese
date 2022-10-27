import java.io.*;

import static java.lang.Thread.sleep;

public class Test {
    public static void main(String[] args) throws IOException, InterruptedException {
        String s;
        long time;
        Translate_Normal.init();
        Translate_Hash.init();
        Translate_Trie.init();
        BufferedReader in=new BufferedReader(new FileReader("诡秘之主.txt"));
        BufferedWriter out_Trie=new BufferedWriter(new FileWriter("诡秘之主byTrie.txt"));
        time=System.currentTimeMillis();
        while((s=in.readLine())!=null){
            out_Trie.write(Translate_Trie.translate_from_Simp_to_Trad(s));
            out_Trie.newLine();
        }
        System.out.println("Translate by Trie using "+(System.currentTimeMillis()-time)+"ms");
        in.close();
        out_Trie.close();
        in=new BufferedReader(new FileReader("诡秘之主.txt"));
        BufferedWriter out_Hsah=new BufferedWriter(new FileWriter("诡秘之主byHash.txt"));
        time=System.currentTimeMillis();
        while((s=in.readLine())!=null){
            out_Hsah.write(Translate_Hash.translate_from_Simp_to_Trad(s));
            out_Hsah.newLine();
        }
        System.out.println("Translate by Hash using "+(System.currentTimeMillis()-time)+"ms");
        in.close();
        out_Hsah.close();
        in=new BufferedReader(new FileReader("诡秘之主.txt"));
        BufferedWriter out_Normal=new BufferedWriter(new FileWriter("诡秘之主byNormal.txt"));
        time=System.currentTimeMillis();
        while((s=in.readLine())!=null){
            out_Normal.write(Translate_Normal.translate_from_Simp_to_Trad(s));
            out_Normal.newLine();
        }
        System.out.println("Translate by Normal using "+(System.currentTimeMillis()-time)+"ms");
        in.close();
        out_Normal.close();
    }
}
