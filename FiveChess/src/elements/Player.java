package elements;

import java.util.ArrayList;

public class Player {
    private String username;
    private String password;
    private ArrayList<Composition> compositionList = new ArrayList<Composition>();
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public ArrayList<Composition> getCompositionList() {
		return compositionList;
	}
	public void setCompositionList(ArrayList<Composition> compositionList) {
		this.compositionList = compositionList;
	}

}
