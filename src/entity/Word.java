package entity;
public class Word {
	private String name;
	private int count;
	private float weight;
	private String pos;
	public Word(){

	}
	public Word(String a,int i)
	{
		this.name=a;
		this.count=i;

		
	}

	public Word(String a,int i,float w)
	{
		this.name=a;
		this.count=i;
		this.weight=w;
	}
	public Word(String name,int count,String pos){
		this.name=name;
		this.count=count;
		//this.weight=weight;
		this.pos=pos;
	}
	public Word(String name,int count,float weight,String pos){
		this.name=name;
		this.count=count;
		this.weight=weight;
		this.pos=pos;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}
}
