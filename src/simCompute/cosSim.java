package simCompute;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.Word;
import entity.postContent;


/**
 * Created by Administrator on 2017/2/25 0025.
 */
public class cosSim {
    Map<String,float[]> mix=new HashMap<>();

    public void weightMix(postContent postA,postContent postB){

        Map<String,float[]> weightMix=new HashMap<>();//绗竴鍒桝锛岀浜屽垪B
        List<Word> wordsA=postA.getWords();
        List<Word> wordsB=postB.getWords();

        for(int i=0;i<wordsA.size()&&i<30;i++){
            float d[]=new float[2];
           // d[0]=wordsA.get(i).getWeight();//浠ユ潈閲嶄綔涓鸿瘝棰戝悜閲�
            d[0]=(float)(wordsA.get(i).getCount())/(float)postA.getWordsCount();//浠ョ浉瀵硅瘝棰戜綔涓鸿瘝棰戝悜閲�
            weightMix.put(wordsA.get(i).getName(),d);

        }
        for(int j=0;j<wordsB.size()&&j<15;j++){
          //  System.out.println("kop锛�"+j+" word:"+wordsB.get(j).getWeight());
            float f[]=new float[2];
            if(weightMix.containsKey(wordsB.get(j).getName())){
                f=weightMix.get(wordsB.get(j).getName());
               // f[1]=wordsB.get(j).getWeight();//浠ユ潈閲嶄綔涓鸿瘝棰戝悜閲�
                f[1]=(float)wordsB.get(j).getCount()/(float)postB.getWordsCount();//浠ョ浉瀵硅瘝棰戜綔涓鸿瘝棰戝悜閲�
            }else{
              //  f[1]=wordsB.get(j).getWeight();//浠ユ潈閲嶄綔涓鸿瘝棰戝悜閲�
                f[1]=(float)wordsB.get(j).getCount()/(float)postB.getWordsCount();//浠ョ浉瀵硅瘝棰戜綔涓鸿瘝棰戝悜閲�
            }

            weightMix.put(wordsB.get(j).getName(),f);

        }
       mix=weightMix;
    }

    public double computeCosSim(){
        float fenzi=0;
        float fenmuAPart=0;
        float fenmuBPart=0;
        for(float[] f:mix.values()){
            fenzi=fenzi+f[0]*f[1];
            fenmuAPart+=f[0]*f[0];
            fenmuBPart+=f[1]*f[1];
        }
        double cosSim=fenzi/(Math.sqrt(fenmuAPart)+Math.sqrt(fenmuBPart));
        return  cosSim;

    }
}
