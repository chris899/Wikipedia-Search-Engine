package ui;

import java.util.ArrayList;
import javax.swing.JTextField;

public class QueryThread extends Thread{
	   
	public Thread ttt;
	private String threadName;
    JTextField query = new JTextField();
	boolean bool = false;
	private View view;
	
    public QueryThread( String name , View v) {
	      threadName = name;
	      System.out.println("Creating " +  threadName );
	      this.view = v;
	}
    
	public void run() 
    { 
        try
        { 
            // Displaying the thread that is running 
            System.out.println ("Thread " + 
                  Thread.currentThread().getId() + 
                  " is running"); 
            
            
            ArrayList<String> history = view.takeHistory();
            
 
    		AutoComplete auto = new AutoComplete();
    		query = auto.mmain(history,this);
    		
    		while(bool == false) {
    			
    		}
    		

        } 
        catch (Exception e) 
        { 
            // Throwing an exception 
            System.out.println ("Exception is caught"); 
        } 
    } 
    public void start () {
	      System.out.println("Starting " +  threadName );
	      if (ttt == null) {
	         ttt = new Thread (this, threadName);
	         ttt.start ();
	      }
	   }
    
    public String getWord() {
    	return this.query.getText();
    }
    public boolean getBool() {
    	return this.bool;
    }
    public void setBool() {
    	this.bool = true;
    }
    
    public  void waitForQuery() {

        while (query==null) {
            try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
    }

    public void setStart(String sss) {
    	view.setStart(sss);
    }

} 

