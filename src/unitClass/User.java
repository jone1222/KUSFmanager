package unitClass;

public class User {
	
	private String sid;
	private String pw;
	private String name;
	
	public User(String sid, String pw, String name) {
		this.sid = sid;
		this.pw = pw;
		this.name = name;
	}
	
	public String getsid() {
		return sid;
	}
	public String getname() {
		return name;
	}
	public String getpw() {
		return pw;
	}

	public void setsid(String sid) {
		this.sid = sid;
	}
	public void setpw(String pw) {
		this.pw = pw;
	}
	public void setname(String name) {
		this.name = name;
	}
}
