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
	
	List<String> filterSimple=new ArrayList<String>(){{add("С��");add("����");add("С��");add("�������");}};
	
	//��lessonUnit���б��г�ʼ��ǰ����Ϊ����ҵ��ƽ̨�����ϵ���ص����ݣ���ɸѡ����֪ʶ���޹ص����ӡ�
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
			//��γ�֪ʶ�㲻��ص����ӵ�lessonunitid����Ϊ����Ϊ���ں��������ļ�ʱ��������ͻ��������Ϊ-i
			lesU.setLesson_id("-"+i);
			lesU.setLesson_unit_id("-"+i);
			lesU.setName(strtemp);
			lesU.setTerm_id(termId);
			lesU.setContent_type("1");
			
			listLesson.add(lesU);
		}
		return listLesson;	
	}
	
	//�ж�str���Ƿ����filterSimple�еĴ���
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
		
		String FilePath="F:\\����ѧϰ\\����1\\"+courseId+"\\moc_lesson_unit";
		ReadFile readf=new ReadFile();
		List<String> pathList=readf.readfile(FilePath);
		
		//��lessonUnit���б��г�ʼ��ǰ����Ϊ����ҵ��ƽ̨�����ϵ���ص����ݣ���ɸѡ����֪ʶ���޹ص����ӡ�
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
						
						int flag=0;//�ж��Ƿ�ΪС�ڡ��������,���ǣ���flag=1����flag=0
						//�ж�lessonunit�Ƿ�Ϊ��Ƶ���ͣ���contenttype=1�����ж��Ƿ�Ϊָ��ѧ��
						
						if(Integer.parseInt(temp[8])==1&&temp[7].equals(termId)){
							
							//�жϲ��ǿγ�С�ᡢ������⡢��ҵ����
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
	
	//����ʱ����Ŀǰû�õ���������
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
		//rdl.readLessonUnit("F:\\����ѧϰ\\����1\\46016\\moc_lesson_unit\\000000_0");
		rdl.readLessonUnit("46016", "46002");
		//rdl.readLesson("F:\\����ѧϰ\\����1\\46016\\moc_lesson\\000000_0");
	}

}
