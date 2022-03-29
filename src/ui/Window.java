package ui;

import java.util.ArrayList;

public class Window {

	private ArrayList<String> temp;
	private String description;
	private String aggType;
	
	public Window() {
		
	}
	public Window(ArrayList<String> t, String d, String atype) {
		this.temp = t;
		this.description = d;
		this.aggType = atype;
	}
	
	public ArrayList<String> getWindow() {
		return this.temp;
	}
	
	public String getDescription() {
		return this.description;
	}
	public String getAggType() {
		return this.aggType;
	}
}
