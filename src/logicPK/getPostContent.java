package logicPK;

import java.util.ArrayList;
import java.util.List;

import entity.lessonUnit;
import entity.post;
import entity.postContent;
import entity.postDetail;
public class getPostContent {
	readPostNoSQL readpost=new readPostNoSQL();
	//List<postDetail> pstDetailList=new ArrayList<>();
	
	//List<lessonUnit> lesList=new ArrayList<>();
	
	//�õ��ִʺ�����Ӷ����б�
	/*
	 * ��������id��ȡpost����detail�еĶ�Ӧ�������ݣ�
	 * ���ִʣ�����setPost_words()��post�����е�Post_words���г�ʼ����
	 * ������List<post>
	 */
	public List<post> getPost(List<post> pstList,List<postDetail> pstDetailList){
		//List<post> pstList=new ArrayList<>();
		
		FileOP fop=new FileOP();
		fenCi fc=new fenCi();
		String dir_Orig="F:\\opr\\origPost\\";
		String dir_Freq="F:\\opr\\freqPost\\";
		selectTag select=new selectTag();
		try {
			fc.innit();
			for(int i=0;i<pstList.size();i++){
				String pstId=pstList.get(i).getPostId();
				//Ѱ��������id��ͬ���������ݣ������зִʣ�����post�����post_words��
				String content="";
				for(int j=0;j<pstDetailList.size();j++){
					if(pstId.equals(pstDetailList.get(j).getPostId())){
						//
						content=pstDetailList.get(j).getPost_detail();
						break;
					}	

				}
				
				String str=pstList.get(i).getTitle()+content;		
				//fop.writeFile(str, pstList.get(i).getPostId(), dir_Orig);
				//����Ƶ��ʽ�ִ�
				str=fc.wordFreqStat(str);
				//fop.writeFile(str, pstList.get(i).getPostId(), dir_Freq);
				//setpost_detail
				pstList.get(i).setPost_detail(content);
				//setpost_words
				postContent pstCon=select.select(str);
				pstList.get(i).setPost_words(pstCon);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pstList;
	}
	/*
	 * ��֪ʶ����зִʣ���ʼ�� List<lessonUnit>�е�ÿ��PointWords��������lIST
	 */
	
	public List<lessonUnit> getPoint(List<lessonUnit> lesList){
		
		FileOP fop=new FileOP();
		fenCi fc=new fenCi();
		String dir_Orig="F:\\opr\\origPoint\\";
		String dir_Freq="F:\\opr\\freqPoint\\";
		selectTag select=new selectTag();
		try {
			fc.innit();
			for(int i=0;i<lesList.size();i++){
				String content=lesList.get(i).getName();
				String unitid=lesList.get(i).getLesson_unit_id();
			//	if(unitid.equals("412038")){
					//System.out.println("BIGBUG");
				//�Ȳ����
					fop.writeFile(content,lesList.get(i).getLesson_unit_id() , dir_Orig);
					content=fc.wordFreqStat(content);
					fop.writeFile(content,lesList.get(i).getLesson_unit_id(), dir_Freq);
					postContent pstCon=select.select(content);
					lesList.get(i).setPointWords(pstCon);
			//	}
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lesList;
	}

}
