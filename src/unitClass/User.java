package unitClass;

public class User {
	
	private int sid;
	private String pw;
	private String name;
	
	public User(int sid, String pw, String name) {
		this.sid = sid;
		this.pw = pw;
		this.name = name;
	}
	
	public int getsid() {
		return sid;
	}
	public String getname() {
		return name;
	}
	public String getpw() {
		return pw;
	}

	public void setsid(int sid) {
		this.sid = sid;
	}
	public void setpw(String pw) {
		this.pw = pw;
	}
	public void setname(String name) {
		this.name = name;
	}
}
