package ui;






	import java.io.BufferedReader;
	import java.io.IOException;
	import java.io.InputStreamReader;
	import java.io.StringReader;
	import java.util.ArrayList;

	import org.apache.lucene.analysis.charfilter.HTMLStripCharFilter;
	import org.apache.lucene.analysis.CharArrayMap.*;

	public  class  MainThread extends Thread{
		   
		public Thread tt;
		private String threadName;
	  
		public MainThread( String name) {
		      threadName = name;
		      System.out.println("Creating " +  threadName );
	
		}
	    
		public synchronized void run() 
	    { 
	        try
	        { 
	            // Displaying the thread that is running 
	            System.out.println ("Thread " + 
	                  Thread.currentThread().getId() + 
	                  " is running"); 
	            
	         View view = new View();
	         view.client();
	  
	        } 
	        catch (Exception e) 
	        { 
	            // Throwing an exception 
	            System.out.println ("Exception is caught"); 
	        } 
	    } 
	    public synchronized void start () {
		      System.out.println("Starting " +  threadName );
		      if (tt == null) {
		         tt = new Thread (this, threadName);
		         tt.start ();
		      }
		   }

	    
	    
	    public synchronized void waitForr() {

	       
	            try {
					wait();
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        
	        

	        // do something
	    }
	    
	    public synchronized void notifff() {
	    	this.notify();
	    	
	    }
	} 

