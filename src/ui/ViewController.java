package ui;


import java.util.ArrayList;
import java.util.HashMap;
public class ViewController {
	
	
	private String path;
	private String func;
	private boolean header;
	ArrayList<String> history;
	private int p = 0;
	private Window window;
	private String winDescription;

	private String outputPath;
	
	public ViewController() {
		this.history= new ArrayList<String>();
		
	}
	
	public void makeWindow(ArrayList<String> win, String d,String at) {
		this.window = new Window(win,d,at);
		this.winDescription = d;
		//return new Window(win,d);
	}
	public void setOutputPath(String p) {
		this.outputPath = p;
	}
	public String getOutputPath() {
		return this.outputPath;
	}
	
	public Window getWindow() {
		return this.window;
	}
	public String getDescription() {
		return this.winDescription;
	}
	public void setHeader(boolean b) {
		this.header = b;
	}
	
	public boolean getHeader() {
		return this.header;
	}
	public void setPath(String p) {
		this.path = p;
	}
	public String getPath() {
		return this.path;
	}
	
	public String getFunc() {
		return this.func;
	}
	
	public void setFunc(String f) {
		this.func = f;
	}
	public void setDescription(String dd) {
		this.winDescription=dd;
	}
	
	
	public void historyEntry(String s) {
		this.history.add(this.p++ +")	"+s);
	}
	
	public ArrayList<String> getHistory(){
		return this.history;
	}
	
}
