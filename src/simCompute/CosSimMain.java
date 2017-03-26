package simCompute;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;import java.util.Map;

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
	
	public void CosSim(String courseId,String termId) throws Exception{
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
		lessonUnits=readLessonU.readLessonUnit(courseId, termId);
		lessonUnits=getContent.getPoint(lessonUnits);
		for(int k=1;k<=Integer.parseInt(nowTerm.getDuration());k++){
			posts=readpost.getPostWeek(courseId, termId,k);
			postsDetail=readpost.getPostDetail(courseId, termId);
			posts=getContent.getPost(posts, postsDetail);
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
			computer.getHotPointContent();
			
		}
		
	}

	public static void main(String args[]) throws Exception{
		/*
		readPostNoSQL readpost=new readPostNoSQL();
		readLessonUnit readLessonU=new readLessonUnit(); 
		List<post> posts=new ArrayList<>();
		List<postDetail> postsDetail=new ArrayList<>();
		List<lessonUnit> lessonUnits=new ArrayList<>();
		//初始化
		for(int k=1;k<13;k++){
			posts=readpost.getPostWeek("46016","46002",k);
			postsDetail=readpost.getPostDetail("46016","46002");
			lessonUnits=readLessonU.readLessonUnit("46016","46002");
			//分词
			getPostContent getContent=new getPostContent();
			posts=getContent.getPost(posts, postsDetail);
			lessonUnits=getContent.getPoint(lessonUnits);
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
			computer.getHotPointContent();
		}
		*/
		CosSimMain csm=new CosSimMain();
		csm.CosSim("46016","46002");
		
	}
}
