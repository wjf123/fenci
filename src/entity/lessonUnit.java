package entity;

public class lessonUnit {
	private String lesson_unit_id;//֪ʶ��id
	private String name;//֪ʶ��
	private String term_id;//ѧ��
	private String lesson_id;//�γ�
	private int content_type;//��Դ����
	private String content_id;//������Դ
	
	private postContent pointWords;//֪ʶ��ִʺ��
	
	public lessonUnit(){
		
	}
	

	@Override
	public String toString() {
		return "lessonUnit [name=" + name + ", term_id=" + term_id + ", lesson_id=" + lesson_id + ", content_id="
				+ content_id + "]";
	}

	

	public String getLesson_unit_id() {
		return lesson_unit_id;
	}


	public void setLesson_unit_id(String lesson_unit_id) {
		this.lesson_unit_id = lesson_unit_id;
	}


	public postContent getPointWords() {
		return pointWords;
	}


	public void setPointWords(postContent pointWords) {
		this.pointWords = pointWords;
	}


	public int getContent_type() {
		return content_type;
	}


	public void setContent_type(String content_type) {
		try{
			this.content_type = Integer.parseInt(content_type);
		}catch(NumberFormatException e)
		{
			this.content_type=-1;
		}
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTerm_id() {
		return term_id;
	}

	public void setTerm_id(String term_id) {
		this.term_id = term_id;
	}
	

	public String getLesson_id() {
		return lesson_id;
	}

	public void setLesson_id(String lesson_id) {
		this.lesson_id = lesson_id;
	}

	public String getContent_id() {
		return content_id;
	}

	public void setContent_id(String content_id) {
		this.content_id = content_id;
	}
	

}
