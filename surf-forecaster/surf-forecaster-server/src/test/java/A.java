import java.io.Serializable;


public class A implements Serializable{
	private String a;
	private B nonSer;
	public void setA(String a) {
		this.a = a;
	}
	public String getA() {
		return a;
	}
	public void setNonSer(B nonSer) {
		this.nonSer = nonSer;
	}
	public B getNonSer() {
		return nonSer;
	}

}
