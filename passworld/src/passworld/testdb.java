package passworld;

public class testdb {
	public static void main(String[] args) {
		DBtest2 dt = new DBtest2();
		System.out.println(dt.insertAccount("naver", "navsser", "aaaaa","aaaaa","aaaaa"));
		System.out.println(dt.getMember());
	}
}
