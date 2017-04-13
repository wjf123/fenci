package simCompute;

import java.util.ArrayList;
import java.util.List;import java.util.Map;
import java.util.Scanner;

import entity.lessonUnit;
import entity.post;
import entity.postDetail;
import entity.term;
import logicPK.getPostContent;
import logicPK.readLessonUnit;
import logicPK.readPostNoSQL;
import logicPK.readTerm;
import simCompute.computeCS;

public class CosSimMain {
	
	public void CosSimWeek(String courseId,String termId) throws Exception{
		readPostNoSQL readpost=new readPostNoSQL();
		readLessonUnit readLessonU=new readLessonUnit(); 
		List<post> posts=new ArrayList<>();
		List<postDetail> postsDetail=new ArrayList<>();
		List<lessonUnit> lessonUnits=new ArrayList<>();
		
		readTerm rdt=new readTerm();
		term nowTerm=new term();
		nowTerm=rdt.getTerm(courseId, termId);
		getPostContent getContent=new getPostContent();
		//分词
		lessonUnits=readLessonU.getLessonUnit(courseId, termId);
		lessonUnits=getContent.getPoint(lessonUnits,courseId, termId);
		for(int k=1;k<=Integer.parseInt(nowTerm.getDuration());k++){
			posts=readpost.getPostWeek(courseId, termId,k);
			postsDetail=readpost.getPostDetail(courseId, termId);
			posts=getContent.getPost(posts, postsDetail,courseId, termId);
			//计算CosSim
			computeCS computer=new computeCS();
			computer.setN(k);
			computer.compute(posts,lessonUnits);
			computer.gethotLesUnitMap();
			computer.writeSimToFile();
			computer.writeHotPointToFile();
			List<Map.Entry<String, Integer>> result=computer.getFreqList();
			for(int i=0;i<result.size();i++){
				System.out.println(result.get(i).getKey()+":"+result.get(i).getValue());
			}
			computer.getHotPointContent(courseId, termId);
			
		}
		
	}
	
	public void CosSimTerm(String courseId,String termId) throws Exception{
		readPostNoSQL readpost=new readPostNoSQL();
		readLessonUnit readLessonU=new readLessonUnit(); 
		List<post> posts=new ArrayList<>();
		List<postDetail> postsDetail=new ArrayList<>();
		List<lessonUnit> lessonUnits=new ArrayList<>();
		
		//readTerm rdt=new readTerm();
		//term nowTerm=new term();
		//nowTerm=rdt.getTerm(courseId, termId);
		getPostContent getContent=new getPostContent();
		//分词
		lessonUnits=readLessonU.getLessonUnit(courseId, termId);
		lessonUnits=getContent.getPoint(lessonUnits,courseId, termId);
		posts=readpost.getPostTerm(courseId, termId);
		postsDetail=readpost.getPostDetail(courseId, termId);
		posts=getContent.getPost(posts, postsDetail,courseId, termId);
		//计算CosSim
		computeCS computer=new computeCS();
		computer.compute(posts,lessonUnits);
		computer.gethotLesUnitMap();
		computer.writeSimToFile();
		computer.writeHotPointToFile();
		List<Map.Entry<String, Integer>> result=computer.getFreqList();
		for(int i=0;i<result.size();i++){
			System.out.println(result.get(i).getKey()+":"+result.get(i).getValue());
		}
		computer.getHotPointContent(courseId, termId);
	}

	public static void main(String args[]) throws Exception{
		
		CosSimMain csm=new CosSimMain();
		String courseId,termId;
		Scanner sc=new Scanner(System.in);
		courseId=sc.next();
		termId=sc.next();
		csm.CosSimTerm(courseId,termId);
		csm.CosSimWeek(courseId,termId);
		//csm.CosSimTerm("46006","47003");
		/*
		csm.CosSimTerm("21011","21011");
		csm.CosSimTerm("46016","46002");
		csm.CosSimTerm("7001","7001");
		csm.CosSimTerm("47024","252014");
		csm.CosSimTerm("199001","417003");
		
		*/
		/*
		csm.CosSimWeek("46006","47003");
		
		csm.CosSimWeek("21011","21011");
		csm.CosSimWeek("46016","46002");
		csm.CosSimWeek("7001","7001");
		csm.CosSimWeek("47024","252014");
		csm.CosSimWeek("199001","417003");
		*/
		
		
	}
}
