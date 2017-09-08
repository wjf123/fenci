package simCompute;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import entity.lessonUnit;
import entity.post;
import entity.postContent;
import logicPK.FileOP;
import simCompute.cosSim;


public class computeCS {
	private Map<String,Map<String,Double>> simMap=new HashMap<>();
	private List<Map.Entry<String, Integer>> freqList;
	private int N=-1;//Ϊ�������N�ܵı�ǩ
	
	
	
	//List<post> postList=new ArrayList<>();
	//List<lessonUnit> lesList=new ArrayList<>();
	
	public int getN() {
		return N;
	}
	public void setN(int n) {
		N = n;
	}
	public Map<String, Map<String, Double>> getSimMap() {
		return simMap;
	}
	public List<Map.Entry<String, Integer>> getFreqList() {
		return freqList;
	}
	/*
	 * ����ÿ����������ص�֪ʶ�����ƶ�
	 * �����Ѿ���ʼ��List<post>��List<lessonUnit>
	 * Map<����id,Map<֪ʶ��id,���ƶ�>>
	 */
	public void compute(List<post> postList,List<lessonUnit> lesList){
	
		for(int i=0;i<postList.size();i++){
			
			//��������map<maxSim_lesson_unit_id,maxsim>
			Map<String,Double> lesSimMap=new HashMap<>();
		
			post p=postList.get(i);
			String post_id=p.getPostId();
			postContent postC=p.getPost_words();
			double maxSim=0.0;//�������������ƶ�����֪ʶ������ƶ�
			String maxSimLesId=lesList.get(0).getLesson_unit_id();
			
			for(int j=0;j<lesList.size();j++){
				//��֮�ȽϵĿ�ʱ��Ԫ֪ʶ��
				lessonUnit lesU=lesList.get(j);
				
				String lesson_unit_id=lesU.getLesson_unit_id();
				//������ӵĹ�����Դ����Ƶ��Դ��ֱ�ӽ����ƶ�����Ϊ1,���ӵ�lesson_unit_id�ֶ�Ϊ��ʱ���������ÿ��֪ʶ������ƶ�
				if(p.getLesson_unit_id().equals(lesson_unit_id)){
					maxSim=1.0;
					maxSimLesId=lesson_unit_id;
					break;
				}else if(p.getLesson_unit_id().equals("\\N")){

					postContent lesC=lesU.getPointWords();
					cosSim cosS=new cosSim();
					//System.out.println("ssssssssssssssssssssssssssssssssssssss"+lesU.getLesson_unit_id());
					cosS.weightMix(lesC, postC);//��ʼ����Ƶ����
					double sim=cosS.computeCosSim();//�������ƶ�
					//���������ƶȱȽϣ�ѡ��������ƶ�����֪ʶ��id
					if(sim>maxSim){
						maxSim=sim;
						maxSimLesId=lesson_unit_id;
					//	maxLesU=lesU;
					}

				}
				
			}
			lesSimMap.put(maxSimLesId, maxSim);
			simMap.put(post_id, lesSimMap);		
		}
		
	}
	/*
	 * ����֪ʶ�㰴�ȶȽ������򡣴Ӹߵ���
	 * δ�ǻ�����������ʦ�Ƿ�����
	 */
	public void  gethotLesUnitMap(){
		Map<String,Integer> freqMapstatic=new HashMap<>();//Map<lesson-unit-id,���������>
		
		//int count=0;
		for(Map<String,Double> entry:simMap.values()){
			
			for(String str:entry.keySet()){
				if(freqMapstatic.containsKey(str))
				{
					int count=freqMapstatic.get(str)+1;
					freqMapstatic.put(str,count);
				}else{
					freqMapstatic.put(str, 1);
				}
				
			}
			
		}
		freqList=new ArrayList<Map.Entry<String,Integer>>(freqMapstatic.entrySet());
		Collections.sort(freqList, new Comparator<Map.Entry<String, Integer>>(){
			public int compare(Map.Entry<String, Integer> entry1,Map.Entry<String, Integer> entry2){
				return entry2.getValue().compareTo(entry1.getValue());
			}
		});
		//int maxFreq=0;
	}
	
	/*
	 * ������id����Ӧ֪ʶ��id ���ƶ�  ���浽�ļ�
	 */
	
	public void writeSimToFile(){
		String str="";
		for(Map.Entry<String, Map<String,Double>> entry:simMap.entrySet()){
			
			str=str+"post:"+entry.getKey()+" ";
			for(Map.Entry<String, Double> childEntry:entry.getValue().entrySet()){
				str=str+"lessonUnitid:"+childEntry.getKey()+" cosSim:"+childEntry.getValue()+"\n";
			}
		}
		
		//FileOP fop=new FileOP();
		//fop.writeFile(str, "week"+N+"_0", "F:\\opr\\");
	}
	/*
	 * �����ע��֪ʶ��id�������ļ�
	 */
	public void writeHotPointToFile(){
		String str="";
		//FileOP fop=new FileOP();
	
		for(int i=0;i<freqList.size()&&i<5;i++){
			//str=str+"lessonUnitId: "+freqList.get(i).getKey()+"  suport:"+freqList.get(i).getValue()+"\n";
		
			str=str+"lessonUnitId: "+freqList.get(i).getKey()+"  suport:"+freqList.get(i).getValue()+"\n";
			
		}
	
		//fop.writeFile(str, "week"+N+"_1", "F:\\opr\\");
		
	}
	
	/*
	 * ��ȡ���ע��֪ʶ�������
	 * 
	 */
	
	public void getHotPointContent(String courseId,String termId){
		JSONObject jsonObj = new JSONObject();//����json��ʽ������
		
		//String str="";
		FileOP fop=new FileOP();
		int j=0;//������ҵ�ȣ��ڼ������ע��֪ʶ��
		List<String> lesUnitCont=new ArrayList<>();
		List<Integer> suport=new ArrayList<>();
		//int suport[];
		for(int i=0;i<freqList.size()&&j<10;i++){
			
			String lessonId=freqList.get(i).getKey();
			//��ȥ��ҵ���γ��Լ�����
			if(lessonId.charAt(0)=='-'){
				continue;
			}
			
			try {
				String lesUnitcontent=fop.readFile("\\opr\\"+courseId+"_"+termId+"\\origPoint\\_"+lessonId);
				
				lesUnitCont.add(filter(lesUnitcontent));
				suport.add(freqList.get(i).getValue());
				 
				//str=str+filter(lesUnitcontent)+":";
				//str=str+freqList.get(i).getValue()+"\n";
				//jsonObjArr.put(fop.readFile("F:\\opr\\origPoint\\_"+lessonId),freqList.get(i).getValue());
				//System.out.println(str);
				j++;
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		jsonObj.put("x_cloud",lesUnitCont);
		jsonObj.put("cloud_num", suport);
		fop.writeFileJson(jsonObj.toString(), "week_"+N, "\\opr\\"+courseId+"_"+termId+"\\result\\");
	}
	
	/*
	 * ��֪ʶ���еġ��ڼ���/��/��Ԫor2-3/2.3�����½ڱ�־ȥ��
	 */
	
	public String filter(String str2){
		String substr="";
		int i,hanzi=0;
		
		for(i=0;i<str2.length();i++){
			substr+=str2.charAt(i);
			//([0-9]*[-|_|����|\\.]*)*
			if(str2.startsWith("��")){
				hanzi=1;
			}
			//��(��0-9��*|[һ|��|��|ʮ]*)[��Ԫ|��|��|��]
			if(substr.matches("��([0-9]*|[һ|��|��|ʮ]*)(��|��|��|(��Ԫ))")){
				i++;
				break;
			}else {
		
				if(substr.matches("([0-9]+?[-|_|����|\\.]*)*(��|��|��|\\s)*")){
					
					continue;
				}else{
					if(hanzi==1){
						continue;
					}else{
						break;
					}
				}
			}
			
		}
		if(i==0)
			return str2;
		else
			return str2.substring(i);
		
	} 
	
	
}
