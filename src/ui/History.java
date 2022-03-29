
package ui;
import java.io.Serializable;
import java.util.ArrayList;

public class History implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public History() {
	};
	
	private  ArrayList<String> history;
	public History( ArrayList<String> h) {
		this.history = h;
	}


	
	public ArrayList<String> getHistory() {
		return this.history;
	}
}

