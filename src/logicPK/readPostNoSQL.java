package logicPK;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import entity.post;
import entity.postDetail;
import entity.term;

public class readPostNoSQL {
	final long OneWeek=604800000;
	private int N;

	public int getN() {
		return N;
	}
	public void setN(int n) {
		N = n;
	}
/*
	 * ����ѧ�ڣ��������ļ��ж�ȡpost���ݣ���ɸѡ�����γ̵�����  
	 */
public List<post> getPostTerm(String courseId,String termId) throws FileNotFoundException, IOException{
	List<post> postList=new ArrayList<>();
	//�õ�post�ļ��������е��ļ�����·��
	String FilePath="F:\\����ѧϰ\\����1\\"+courseId+"\\moc_post";
	ReadFile readf=new ReadFile();
	List<String> pathList=readf.readfile(FilePath);
	for(int i=0;i<pathList.size();i++){
		try {
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(pathList.get(i)),"UTF-8"));
			String str="";
			while((str=br.readLine())!=null){
				post p=new post();
				try{
					String temp[]=str.split("");
					//ѡ����ʵĸÿγ�termId
					if(temp[7].equals(termId)){
						p.setPostId(temp[0]);
						
						p.setTitle(temp[10]);
						p.setPost_time(temp[9]);
						p.setLesson_unit_id(temp[4]);
						p.setTerm_id(temp[7]);
						p.setCout_browse(temp[19]);
						p.setCount_reply(temp[20]);
						p.setTag_lector(temp[18]);
						postList.add(p);
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
	
		return postList;
		
	}
	/*
	 * ��ȡ��n�ܵ�����
	 */
	public List<post> getPostWeek(String courseId,String termId,int n) throws Exception{
		//setN(n);
		//
		List<post> postList=new ArrayList<>();
		//�õ�post�ļ��������е��ļ�����·��
		String FilePath="F:\\����ѧϰ\\����1\\"+courseId+"\\moc_post";
		ReadFile readf=new ReadFile();
		List<String> pathList=readf.readfile(FilePath);
		//��ȡterm�Ŀ�ʼʱ��
		readTerm readt=new readTerm();
		term t=new term();
		t=readt.getTerm(courseId, termId);
		
		String termStartTime=t.getStartTime();
		//String termEndTime=readt.getTerm(courseId, termId).getEndTime();
		//����post�ļ��������е��ļ�����ȡ����n����Ҫ���post
		for(int i=0;i<pathList.size();i++){
			try {
				BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(pathList.get(i)),"UTF-8"));
				String str="";
				while((str=br.readLine())!=null){
					post p=new post();
					try{
						String temp[]=str.split("");
						
						long timeP=Long.parseLong(temp[9])-Long.parseLong(termStartTime);
						//ѡ����ʵĸÿγ�termId
						boolean a=temp[7].equals(termId);
						//long fg=(long)n*OneWeek;
						boolean b=timeP<=(long)n*OneWeek;
						//long d=(long)(n-1)*OneWeek;
						boolean c=timeP>(long)(n-1)*OneWeek;
						
						if(a&&b&&c){
							p.setPostId(temp[0]);
							
							p.setTitle(temp[10]);
							p.setPost_time(temp[9]);
							p.setLesson_unit_id(temp[4]);
							p.setTerm_id(temp[7]);
							p.setCout_browse(temp[19]);
							p.setCount_reply(temp[20]);
							p.setTag_lector(temp[18]);
							postList.add(p);
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
		
		
		return postList;
		
	}


	public List<postDetail> getPostDetail(String courseId,String termId) throws FileNotFoundException, IOException{
		List<postDetail> postDetailList=new ArrayList<>();
		String FilePath="F:\\����ѧϰ\\����1\\"+courseId+"\\moc_post_detail";
		ReadFile rdf=new ReadFile();
		List<String> pathList=rdf.readfile(FilePath);
		for(int i=0;i<pathList.size();i++){
			try {
				BufferedReader brf=new BufferedReader(new InputStreamReader(new FileInputStream(pathList.get(i)), "UTF-8"));
				String str="";
				
				while((str=brf.readLine())!=null){
					
					String temp[]=str.split("");
					try{
						postDetail pd=new postDetail();
						pd.setPostId(temp[0]);
						if(temp.length==4){
							//ȥ�����������е�html��ǩ
							temp[3]=temp[3].replaceAll("<[.[^<]]*>", "");
							temp[3]=temp[3].replaceAll("&nbsp;", "");
							pd.setPost_detail(temp[3]);
						}else{
							pd.setPost_detail("");
						}
						System.out.println(pd.toString());
						postDetailList.add(pd);
						
					}catch(ArrayIndexOutOfBoundsException e)
					{
						e.printStackTrace();
						continue;
					}
					
				}
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		return postDetailList;
	}
	
	
	public void getPoint(){
		
	}
	
	public static void main(String args[]) throws Exception{
		 
		readPostNoSQL readp=new readPostNoSQL();
		List<postDetail> plist=readp.getPostDetail("46016", "46002");
		for(int i=0;i<plist.size();i++){
			System.out.println(i+":"+plist.get(i).getPost_detail());
		}
		//readp.readPost("F:\\����ѧϰ\\����1\\2016-11-09\\7001\\moc_post\\000000_0");
		//readp.getPostDetail("F:\\����ѧϰ\\����1\\2016-11-09\\7001\\moc_post_detail\\000000_0");
	}

}
