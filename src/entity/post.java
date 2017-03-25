package entity;

public class post {
	private String postId;//帖子id
	private String title;//帖子标题
	private String term_id;//学期
	private String lesson_unit_id;//所属课时（知识点？）
	private String post_time;//发表时间
	private int tag_lector;//教师是否参与
	private int count_reply;//回复数
	private int count_vote;//投票数
	private int cout_browse;//浏览数
	private int tag_agree;//赞同数
	private String post_detail;//帖子内容
	private postContent post_words;//帖子内容词语
	
	public post(){
		
	}
	
	public String getPost_time() {
		return post_time;
	}

	public void setPost_time(String post_time) {
		this.post_time = post_time;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getPost_detail() {
		return post_detail;
	}

	public void setPost_detail(String post_detail) {
		this.post_detail = post_detail;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTerm_id() {
		return term_id;
	}
	public void setTerm_id(String term_id) {
		this.term_id = term_id;
	}
	public String getLesson_unit_id() {
		return lesson_unit_id;
	}
	public void setLesson_unit_id(String lesson_unit_id) {
		this.lesson_unit_id = lesson_unit_id;
	}
	public int getTag_lector() {
		return tag_lector;
	}
	public void setTag_lector(String tag_lector) {
		try{
			this.tag_lector = Integer.parseInt(tag_lector);
		}catch(NumberFormatException e){
			this.tag_lector=0;//教师没有参与
		}
		
		
	}
	public int getCount_reply() {
		return count_reply;
	}
	public void setCount_reply(String count_reply) {
		try{
			this.count_reply = Integer.parseInt(count_reply);
		}catch(NumberFormatException e){
			this.count_reply=0;//没有人回复
		}
		
	}
	public int getCount_vote() {
		return count_vote;
	}
	public void setCount_vote(String count_vote) {
		try{
			this.count_vote = Integer.parseInt(count_vote);
		}catch(NumberFormatException e){
			this.count_vote=0;
		}
		
	}
	public int getCout_browse() {
		return cout_browse;
	}
	public void setCout_browse(String cout_browse) {
		try{
			this.cout_browse = Integer.parseInt(cout_browse);
		}catch(NumberFormatException e){
			this.cout_browse=0;
		}
		
	}
	public int getTag_agree() {
		return tag_agree;
	}
	public void setTag_agree(String tag_agree) {
		try{
			this.tag_agree=Integer.parseInt(tag_agree);
		}catch(NumberFormatException e){
			this.tag_agree=0;
		}
	}
	public postContent getPost_words() {
		return post_words;
	}
	public void setPost_words(postContent post_words) {
		this.post_words = post_words;
	}
	
	

}
