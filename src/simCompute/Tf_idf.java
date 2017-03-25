package simCompute;

import java.io.*;
import java.util.ArrayList;

import java.util.List;

import entity.Word;
import entity.postContent;
import logicPK.FileOP;
import logicPK.selectTag;

public class Tf_idf 
{
	private String path;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public File[] findFile(String path){
		File f=new File(path);
		File[] files=null;
		if(!f.exists()){
			System.out.println("路径不存在");
		}else{
			files=f.listFiles();

		}
		return files;

	}


	public  List<postContent> ini_postContent() throws Exception
	{
		List<postContent> postC=new ArrayList<postContent>();
		File files[]=findFile(path);
		for(int i=0;i<files.length;i++)

		{
			System.out.println(files.length+"这是第"+i+"地址为："+path+"_"+i+".txt");
			FileOP fop=new FileOP();
			//String str=fop.readFile(files[i].toString()+"\\"+"_"+i+".txt");
			String str=fop.readFile(path+"_"+i+".txt");

			selectTag slt=new selectTag();
			postContent poc=slt.select(str);
			postC.add(poc);
			////要先读出词频文件List<Word> wL=readFile(files[i].getPath());

		}
		return postC;

	}

	/**
	 * 判断一个文档中是否包含指定单词，
	 * 包括返回1，不包括返回0
	 * @param postC
	 * @param word
	 * @return
	 */
	public  int count_containword(List<postContent> postC ,String word)
	{
		int n=0;
		for(int i=0;i<postC.size();i++){
			List<Word> wL=postC.get(i).getWords();
			for(int j=0;j<wL.size();j++){
				if(wL.get(j).getName().equalsIgnoreCase(word)){
					n++;
					break;
				}
			}
		}
		return n;

	}



	/**
	 * 计算每个单词的权重，并输出结果
	 * @param
	 * @throws IOException
	 */
	
	public  List<postContent> tf_idf(String weightPath) throws Exception
	{
		List<postContent> postC=ini_postContent();

		for(int i=0;i<postC.size();i++){
			List<Word> words=postC.get(i).getWords();

			for(int j=0;j<words.size();j++){
				Word wordTemp=words.get(j);
				float tf=(float)wordTemp.getCount()/(float)postC.get(i).getWordsCount();
				float idf=(float)Math.log((float)postC.size()/(float)count_containword(postC,wordTemp.getName()));
				float weight=tf*idf;
				postC.get(i).getWords().get(j).setWeight(weight);
				postC.get(i).sortWordList();
			}


	//		FileOP fop=new FileOP();
	//		fop.writeFile(postC.get(i).toString(),i,weightPath);
		}
		return postC;

		
	}
	
	public static void main (String args[])throws Exception {
		Tf_idf td=new Tf_idf();
		td.setPath("F:\\OnlineStudy\\frequencyKPoint\\");
		td.tf_idf("F:\\OnlineStudy\\tfidf_KPOINT_weight\\");
	}
	

}
