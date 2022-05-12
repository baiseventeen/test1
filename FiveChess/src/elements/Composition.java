package elements;

public class Composition {
	private String username;
	private String gamename;
	private String name1;
	private String name2;
	private int[][] allChess = new int[16][16];
	long[] times = new long[256];
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getGamename() {
		return gamename;
	}
	public void setGamename(String gamename) {
		this.gamename = gamename;
	}
	public String getName1() {
		return name1;
	}
	public void setName1(String name1) {
		this.name1 = name1;
	}
	public String getName2() {
		return name2;
	}
	public void setName2(String name2) {
		this.name2 = name2;
	}
	public int[][] getAllChess() {
		return allChess;
	}
	public void setAllChess(int[][] allChess) {
		this.allChess = allChess;
	}
	public long[] getTimes() {
		return times;
	}
	public void setTimes(long[] times) {
		this.times = times;
	}
}
