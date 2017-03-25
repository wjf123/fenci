package entity;

public class lesson {
	String lessonid;
	String typeid;//类型： 1普通课时 2作业 3测验 
	public String getLessonid() {
		return lessonid;
	}
	public void setLessonid(String lessonid) {
		this.lessonid = lessonid;
	}
	public String getTypeid() {
		return typeid;
	}
	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}
	@Override
	public String toString() {
		return "lesson [lessonid=" + lessonid + ", typeid=" + typeid + "]";
	}
	

}
