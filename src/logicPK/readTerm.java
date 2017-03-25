package logicPK;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import entity.term;

public class readTerm {
	List<term> termList=new ArrayList<>();
	public void readterm(String courseId) throws FileNotFoundException, IOException{
		String FilePath="F:\\在线学习\\数据1\\"+courseId+"\\moc_term";
		ReadFile readf=new ReadFile();
		List<String> pathList=readf.readfile(FilePath);
		for(int i=0;i<pathList.size();i++){
			String str;
			try {
				BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(pathList.get(i)),"UTF-8"));
				try {
					while((str=br.readLine())!=null){
						term t=new term();
						String temp[]=str.split("");
						t.setTermId(temp[0]);
						t.setCourseId(temp[3]);
						t.setStartTime(temp[4]);
						t.setEndTime(temp[6]);
						termList.add(t);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	public term getTerm(String courseId,String termId) throws FileNotFoundException, IOException{
		readterm(courseId);
		String str="";
		term t=new term();
		for(int i=0;i<termList.size();i++){
			t=termList.get(i);
			if(termId.equals(t.getTermId())){
				str=termList.get(i).getStartTime();
				break;
			}
		}
		return t;
	}
	
	public static void main(String args[]) throws FileNotFoundException, IOException{
		readTerm rt=new readTerm();
		String str=rt.getTerm("46016", "46002").getEndTime();
		System.out.println(str);
	}

}
