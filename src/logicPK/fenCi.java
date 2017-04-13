package logicPK;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.sun.jna.Library;
import com.sun.jna.Native;

public class fenCi {
	

	// 定义接口CLibrary，继承自com.sun.jna.Library
	public interface CLibrary extends Library {

		// 定义并初始化接口的静态变量
		CLibrary Instance = (CLibrary) Native.loadLibrary(
				"NLPIR", CLibrary.class);
		

		// printf函数声明
		public boolean NLPIR_Init(byte[] sDataPath, int encoding,
				byte[] sLicenceCode);
		public Object NLPIR_ParagraphProcessA();

		public String NLPIR_ParagraphProcess(String sSrc, int bPOSTagged);

		public String NLPIR_WordFreqStat(String sText);

		public String NLPIR_GetKeyWords(String sLine,int nMaxKeyLimit,boolean bWeightOut);
		public int NLPIR_ImportUserDict(byte[] sPath,boolean bOverwrite);
		
		public int NLPIR_AddUserWord(String sWord);  
		
		
		public void NLPIR_Exit();
	}

	public static String transString(String aidString, String ori_encoding,
			String new_encoding) {
		try {
			return new String(aidString.getBytes(ori_encoding), new_encoding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void innit() throws Exception
	{
		String dic="";
		
		String system_charset = "utf-8";
		int charset_type = 1;
		if (!CLibrary.Instance.NLPIR_Init(dic.getBytes(system_charset),
				charset_type, "0".getBytes(system_charset))) {
			System.err.println("初始化失败！");
		}
	}
	public String fenci(String str,int stage) throws Exception
	{
	
		System.out.println(str);
		String nativeBytes = null;
		nativeBytes=CLibrary.Instance.NLPIR_ParagraphProcess(str,stage);
		return nativeBytes;
		
	}

	public String wordFreqStat(String sText) {

		String str=null;
		str=CLibrary.Instance.NLPIR_WordFreqStat(sText);
		return str;
	}
	public void exitNL()
	{
		CLibrary.Instance.NLPIR_Exit();
	}
	
	public String getkeyWords(String str) throws Exception
	{
		String nativeByte = CLibrary.Instance.NLPIR_GetKeyWords(str,10,false);

		return nativeByte;
	}
	
	public int importUserDic(){
		String userDicPath="\\Data\\test.txt";
		//byte[] bt=userDicPath.getBytes();
		int dict=CLibrary.Instance.NLPIR_ImportUserDict(userDicPath.getBytes(),true);
		return dict;
	}
	
	public void addWord(List<String> newWord){
		for(int i=0;i<newWord.size();i++){
			int flag=CLibrary.Instance.NLPIR_AddUserWord(newWord.get(i));
			System.out.println(flag);
		}
		
	}
	
	public static void main(String args[]){
		fenCi fc=new fenCi();
		List<String> words=new ArrayList<>();
		words.add("课件 n");
		String str="请问老师，本周的四讲可以一次性学习完吗？:我因工作的关系，时间有限，不一定是在老师讲课"
				+ "的时候学习，请问老师，可以看课件自学吗"
				+ "？另外，如果说一次性把本周的四讲内容全部学习完成，可以吗？";
		try {
			fc.innit();
			String str_1=fc.wordFreqStat(str);
			
			//int dic=fc.importUserDic();
			fc.addWord(words);
			String str_2=fc.wordFreqStat(str);
			//System.out.println(dic);
			System.out.println("未导入词典"+str_1);
			System.out.println("导入词典："+str_2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}

