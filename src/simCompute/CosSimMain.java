package simCompute;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;import java.util.Map;

import entity.lessonUnit;
import entity.post;
import entity.postDetail;
import logicPK.getPostContent;
import logicPK.readLessonUnit;
import logicPK.readPostNoSQL;
import simCompute.computeCS;

public class CosSimMain {

	public static void main(String args[]) throws Exception{
		readPostNoSQL readpost=new readPostNoSQL();
		readLessonUnit readLessonU=new readLessonUnit(); 
		List<post> posts=new ArrayList<>();
		List<postDetail> postsDetail=new ArrayList<>();
		List<lessonUnit> lessonUnits=new ArrayList<>();
		//初始化
		posts=readpost.getPostWeek("46016","46002",2);
		postsDetail=readpost.getPostDetail("46016","46002");
		lessonUnits=readLessonU.readLessonUnit("46016","46002");
		//分词
		getPostContent getContent=new getPostContent();
		posts=getContent.getPost(posts, postsDetail);
		lessonUnits=getContent.getPoint(lessonUnits);
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
		computer.getHotPointContent();
		
	}
}
