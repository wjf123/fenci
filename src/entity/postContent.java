package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/24 0024.
 */
public class postContent {
    List<Word> words=new ArrayList<Word>();
    int wordsCount;
    public postContent(){
    	
    }

    public int getWordsCount() {
        return wordsCount;
    }

    public void setWordsCount(int wordsCount) {
        this.wordsCount = wordsCount;
    }

    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }

    public void sortWordList(){
       // List<Word> wTemp=new ArrayList<Word>();
        for(int i=0;i< words.size()-1;i++){
            for(int j=0;j<words.size()-i-1;j++){
                if(words.get(j).getWeight()<words.get(j+1).getWeight()){
                    Word w=words.get(j);
                    words.set(j,words.get(j+1));
                    words.set(j+1,w);
                }
            }
        }


    }
    public String toString(){
        String str="";
        for(int i=0;i<words.size();i++){
            Word w=words.get(i);
            str=str+w.getName()+" "+w.getPos()+" "+w.getCount()+" "+w.getWeight()+"\n";
        }
        str=str+"toall:"+wordsCount;
        return str;
    }
}
