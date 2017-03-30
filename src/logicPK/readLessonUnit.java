package logicPK;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import entity.lesson;
import entity.lessonUnit;

public class readLessonUnit {
	
	List<String> filterSimple=new ArrayList<String>(){{add("小结");add("例题");add("小节");add("问题求解");}};
	
	//在lessonUnit的列表中初始化前几个为与作业、平台、资料等相关的内容，以筛选出与知识点无关的帖子。
	public List<lessonUnit> init(String termId) throws FileNotFoundException, IOException{
		List<lessonUnit> listLesson=new ArrayList<lessonUnit>();
		String FilePath="F:\\opr\\other";
		ReadFile readf=new ReadFile();
		List<String> pathList=readf.readfile(FilePath);
		for(int i=0;i<pathList.size();i++){
			lessonUnit lesU=new lessonUnit();
			BufferedReader brf=new BufferedReader(new InputStreamReader(new FileInputStream(pathList.get(i)), "UTF-8"));
			String strtemp="";
			String str="";
			while((str=brf.readLine())!=null){
				strtemp+=str;
			}
		//	System.out.println("add other:"+strtemp);
			//与课程知识点不相关的帖子的lessonunitid设置为负，为了在后面生成文件时命名不冲突，可设置为-i
			lesU.setLesson_id("-"+i);
			lesU.setLesson_unit_id("-"+i);
			lesU.setName(strtemp);
			lesU.setTerm_id(termId);
			lesU.setContent_type("1");
			
			listLesson.add(lesU);
		}
		return listLesson;	
	}
	
	//判断str中是否包含filterSimple中的词语
	public int filter(String str){
		int flag=0;
		for(int k=0;k<filterSimple.size();k++){
			if(str.contains(filterSimple.get(k))){
				flag=1;
				break;
			}
				
				
		}
		return flag;
	}
	
	public List<lessonUnit> readLessonUnit(String courseId,String termId) throws FileNotFoundException, IOException{
		
		String FilePath="F:\\在线学习\\数据1\\"+courseId+"\\moc_lesson_unit";
		ReadFile readf=new ReadFile();
		List<String> pathList=readf.readfile(FilePath);
		
		//在lessonUnit的列表中初始化前几个为与作业、平台、资料等相关的内容，以筛选出与知识点无关的帖子。
		List<lessonUnit> listLesson=init(termId);
		
		
		for(int i=0;i<pathList.size();i++){
			try {
				BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(pathList.get(i)),"UTF-8"));
				String str="";
				int j=0;
				while((str=br.readLine())!=null){
					String temp[]=new String[11];
					
					temp=str.split("");
					try{
						j++;
						
						int flag=0;//判断是否为小节、例题解答等,若是，则flag=1否则flag=0
						//判断lessonunit是否为视频类型，即contenttype=1，并判断是否为指定学期
						
						if(Integer.parseInt(temp[8])==1&&temp[7].equals(termId)){
							
							//判断不是课程小结、例题详解、作业、等
							flag=filter(temp[3]);
							if(flag==0){
								lessonUnit lessonU=new lessonUnit();
								lessonU.setLesson_unit_id(temp[0]);
								lessonU.setName(temp[3]);
								lessonU.setLesson_id(temp[5]);
								lessonU.setTerm_id(temp[7]);
								lessonU.setContent_type(temp[8]);
								lessonU.setContent_id(temp[9]);
								System.out.println(j+": "+lessonU.toString());
								listLesson.add(lessonU);
							}
							
						}
						
					}catch(ArrayIndexOutOfBoundsException e){
						e.printStackTrace();
						continue;
					}		
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return listLesson;
		
	}
	
	//读课时表。。目前没用到。。。。
	public List<lesson> readLesson(String path){
		List<lesson> lessonList=new ArrayList<>();
		try {
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(path),"UTF-8"));
			String str;
			String temp[]=new String[10];
			while((str=br.readLine())!=null){
				lesson les=new lesson();
				temp=str.split("");
				les.setLessonid(temp[0]);
				les.setTypeid(temp[8]);
				System.out.println(les.toString());
				lessonList.add(les);
				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lessonList;
	}
	
	public static void main(String args[]) throws FileNotFoundException, IOException{
		readLessonUnit rdl=new readLessonUnit();
		//rdl.readLessonUnit("F:\\在线学习\\数据1\\46016\\moc_lesson_unit\\000000_0");
		rdl.readLessonUnit("46016", "46002");
		//rdl.readLesson("F:\\在线学习\\数据1\\46016\\moc_lesson\\000000_0");
	}

}
