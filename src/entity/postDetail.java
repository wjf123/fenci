package entity;

public class postDetail {
	String postId;
	String post_detail;
	
	@Override
	public String toString() {
		return "postDetail [postId=" + postId + ", post_detail=" + post_detail + "]";
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
	

}
