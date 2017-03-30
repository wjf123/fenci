package simCompute;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import entity.lessonUnit;
import entity.post;
import entity.postContent;
import logicPK.FileOP;
import simCompute.cosSim;


public class computeCS {
	private Map<String,Map<String,Double>> simMap=new HashMap();
	private List<Map.Entry<String, Integer>> freqList;
	private int N;//为了输出带N周的标签
	
	
	
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
	 * 计算每个帖子最相关的知识点相似度
	 * 假设已经初始化List<post>，List<lessonUnit>
	 * Map<帖子id,Map<知识点id,相似度>>
	 */
	public void compute(List<post> postList,List<lessonUnit> lesList){
	
		for(int i=0;i<postList.size();i++){
			
			//用来保存map<maxSim_lesson_unit_id,maxsim>
			Map<String,Double> lesSimMap=new HashMap();
		
			post p=postList.get(i);
			String post_id=p.getPostId();
			postContent postC=p.getPost_words();
			double maxSim=0.0;//保存与帖子相似度最大的知识点的相似度
			String maxSimLesId=lesList.get(0).getLesson_unit_id();
			
			for(int j=0;j<lesList.size();j++){
				//与之比较的课时单元知识点
				lessonUnit lesU=lesList.get(j);
				
				String lesson_unit_id=lesU.getLesson_unit_id();
				//如果帖子的关联资源是视频资源，直接将相似度设置为1,帖子的lesson_unit_id字段为空时，需计算与每个知识点的相似度
				if(p.getLesson_unit_id().equals(lesson_unit_id)){
					maxSim=1.0;
					maxSimLesId=lesson_unit_id;
					break;
				}else if(p.getLesson_unit_id().equals("\\N")){

					postContent lesC=lesU.getPointWords();
					cosSim cosS=new cosSim();
					System.out.println("ssssssssssssssssssssssssssssssssssssss"+lesU.getLesson_unit_id());
					cosS.weightMix(lesC, postC);//初始化词频矩阵
					double sim=cosS.computeCosSim();//计算相似度
					//与最大的相似度比较，选出最大相似度与其知识点id
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
	 * 计算知识点按热度进行排序。从高到低
	 * 未虑回帖人数、教师是否参与等
	 */
	public void  gethotLesUnitMap(){
		Map<String,Integer> freqMapstatic=new HashMap();//Map<lesson-unit-id,相关帖子数>
		
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
	 * 将帖子id，对应知识点id 相似度  保存到文件
	 */
	
	public void writeSimToFile(){
		String str="";
		for(Map.Entry<String, Map<String,Double>> entry:simMap.entrySet()){
			
			str=str+"post:"+entry.getKey()+" ";
			for(Map.Entry<String, Double> childEntry:entry.getValue().entrySet()){
				str=str+"lessonUnitid:"+childEntry.getKey()+" cosSim:"+childEntry.getValue()+"\n";
			}
		}
		
		FileOP fop=new FileOP();
		//fop.writeFile(str, "week"+N+"_0", "F:\\opr\\");
	}
	/*
	 * 将最关注的知识点id保存在文件
	 */
	public void writeHotPointToFile(){
		String str="";
		FileOP fop=new FileOP();
	
		for(int i=0;i<freqList.size()&&i<5;i++){
			//str=str+"lessonUnitId: "+freqList.get(i).getKey()+"  suport:"+freqList.get(i).getValue()+"\n";
		
			str=str+"lessonUnitId: "+freqList.get(i).getKey()+"  suport:"+freqList.get(i).getValue()+"\n";
			
		}
	
		//fop.writeFile(str, "week"+N+"_1", "F:\\opr\\");
		
	}
	
	/*
	 * 读取最关注的知识点的内容
	 * 
	 */
	
	public void getHotPointContent(String courseId,String termId){
		JSONObject jsonObj = new JSONObject();//创建json格式的数据
		
		String str="";
		FileOP fop=new FileOP();
		int j=0;//除了作业等，第几个最关注的知识点
		List<String> lesUnitCont=new ArrayList();
		List<Integer> suport=new ArrayList();
		//int suport[];
		for(int i=0;i<freqList.size()&&j<5;i++){
			
			String lessonId=freqList.get(i).getKey();
			//出去作业、课程以及交流
			if(lessonId.charAt(0)=='-'){
				continue;
			}
			
			try {
				lesUnitCont.add(fop.readFile("F:\\opr\\"+courseId+"_"+termId+"\\origPoint\\_"+lessonId));
				suport.add(freqList.get(i).getValue());
				 
				str=str+fop.readFile("F:\\opr\\"+courseId+"_"+termId+"\\origPoint\\_"+lessonId)+":";
				str=str+freqList.get(i).getValue()+"\n";
				//jsonObjArr.put(fop.readFile("F:\\opr\\origPoint\\_"+lessonId),freqList.get(i).getValue());
				System.out.println(str);
				j++;
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		jsonObj.put("x_cloud",lesUnitCont);
		jsonObj.put("cloud_num", suport);
		fop.writeFileJson(jsonObj.toString(), "week_"+N, "F:\\opr\\"+courseId+"_"+termId+"\\result\\");
	}
	
	
}
