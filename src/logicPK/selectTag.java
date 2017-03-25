package logicPK;


import java.util.ArrayList;
import java.util.List;

import entity.Word;
import entity.postContent;

/**
 * Created by Administrator on 2017/2/24 0024.
 */
public class selectTag {
    public selectTag(){

    }


    public  postContent select(String str){
        System.out.println(str);
        postContent poc=new postContent();
        List<Word> words=new ArrayList<Word>();
        String strSpl[]=str.split("#");
        int count=0;
        for(int i=0;i<strSpl.length;i++){
            //瑙ｅ喅鍑虹幇#blabla/nb/2##blabla/n/3#鐨勬儏鍐�
            if(strSpl[i].equals("")){
                strSpl[i+1]="#"+strSpl[i+1];
                break;
            }


           // String wordInfo[]=strSpl[i].split("/+");
            int lastIndex=strSpl[i].lastIndexOf("/");
            int lastSecondIndex=strSpl[i].lastIndexOf("/",lastIndex-1);
            //閬垮厤鍑虹幇鈥� /4鈥�
            if(lastSecondIndex>0){
                String name=strSpl[i].substring(0,lastSecondIndex);
                String pos=strSpl[i].substring(lastSecondIndex+1,lastIndex);
                //鍙繚鐣欏悕璇嶃�佸姩璇嶅拰褰㈠璇嶏紝鑻ュ叏閮ㄤ繚鐣欙紝鍒欏彧闇�灏唅f璇彞鍒犻櫎鍗冲彲
              //  if(pos.matches("n.*|v.*|a.*"))
               // {
                    int wordCount=Integer.parseInt(strSpl[i].substring(lastIndex+1));
                    Word w=new Word();
                    w.setName(name);
                    w.setPos(pos);
                    w.setCount(wordCount);
                    words.add(w);
                    count=count+wordCount;
           //     }



                System.out.println("words:"+strSpl[i].substring(0,lastSecondIndex)+" cixing:"+
                        strSpl[i].substring(lastSecondIndex+1,lastIndex)+" shuliang:"+strSpl[i].substring(lastIndex+1));



            }


        }
        poc.setWords(words);
        poc.setWordsCount(count);
        return poc;
    }



}
