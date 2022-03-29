package main;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;

import org.apache.lucene.analysis.charfilter.HTMLStripCharFilter;
import org.apache.lucene.analysis.CharArrayMap.*;

public class MultithreadingScrapper extends Thread{
	   
	public Thread t;
	private String threadName;
    private ArrayList<String> scrapped;
	public MultithreadingScrapper( String name) {
	      threadName = name;
	      System.out.println("Creating " +  threadName );
	      this.scrapped = new ArrayList<String>();
	}
    
	public void run() 
    { 
        try
        { 
            // Displaying the thread that is running 
            System.out.println ("Thread " + 
                  Thread.currentThread().getId() + 
                  " is running"); 
            
            String command = "cmd /c python ./pars22.py";
            //String[] args = new String[] {"xterm", "python3" , "./pars.py"};
            //Process p = new ProcessBuilder(args).start();
			Process p = Runtime.getRuntime().exec(command);
			
		    BufferedReader bri = new BufferedReader(new InputStreamReader(p.getInputStream()));
		   // BufferedReader bre = new BufferedReader(new InputStreamReader(p.getErrorStream()));
		    String line;
		    
		    while ((line = bri.readLine()) != null) {
	            //System.out.println(line);
	            scrapped.add(line);
	            
	        }
		    
			bri.close();
	
			p.destroy();
  
        } 
        catch (Exception e) 
        { 
            // Throwing an exception 
            System.out.println ("Exception is caught"); 
            System.exit(-1);
        } 
    } 
    public void start () {
	      System.out.println("Starting " +  threadName );
	      if (t == null) {
	         t = new Thread (this, threadName);
	         t.start ();
	      }
	   }

    public ArrayList<String> getScrapped(){
    	return this.scrapped;
    }
} 

