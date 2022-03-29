package main;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.lucene.document.Document; 
import org.apache.lucene.queryparser.classic.*;
import org.apache.lucene.search.ScoreDoc; 
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.index.DirectoryReader;


public class LuceneTester 
{ 
	String indexDir = "C:\\Users\\chris\\Desktop\\pit"; 
	String dataDir = "C:\\Users\\chris\\Desktop\\pit"; 
	Indexer indexer; 
	Searcher searcher;
	String erwtisi;
	ArrayList<String> a1 = new ArrayList<String>();
	ArrayList<String> a2 = new ArrayList<String>();
	public LuceneTester(String x) {
		this.erwtisi=x;
		a1 = new ArrayList<String>();
		a2 = new ArrayList<String>();
	}
	public void doit() 
	{ 
		LuceneTester tester; 
		try 
		{ 
			MultithreadingScrapper object = new MultithreadingScrapper("obj1"); 
			//Multi object2 = new Multi("obj2"); 
            object.start(); 
           // object2.start(); 
            ArrayList<String> sc = object.getScrapped();
           // object.t.join();
            //System.out.println(sc.get(0));
           //System.out.println(filter(sc.get(0)));
			//tester = new LuceneTester(); 
			createIndex(); 
			search(erwtisi); 
		} 
		catch (IOException e) 
		{ 
			e.printStackTrace(); 
		} 
		catch (ParseException e) 
		{ 
			e.printStackTrace(); 
		}
	} 
	private void createIndex() throws IOException
	{ 
		indexer = new Indexer(indexDir); 
		long startTime = System.currentTimeMillis(); 
		indexer.createIndex(dataDir, new TextFileFilter()); 
		long endTime = System.currentTimeMillis(); 
		indexer.close(); 
		System.out.println(" File indexed, time taken: " +(endTime-startTime)+" ms"); 
		a2.add(" File indexed, time taken: " +(endTime-startTime)+" ms");
	} 
	private void search(String searchQuery) throws IOException, ParseException
	{ 
		searcher = new Searcher(indexDir); 
		long startTime = System.currentTimeMillis();
		TopDocs hits = searcher.search(searchQuery.toString());
		//TopDocs hits = searcher.AlternateSearch(searchQuery);
		long endTime = System.currentTimeMillis(); 
		System.out.println(hits.totalHits + " documents found. Time :" + (endTime - startTime)); 
		a2.add(hits.totalHits + " documents found. Time :" + (endTime - startTime));
		for(ScoreDoc scoreDoc : hits.scoreDocs) 
		{ 
			Document doc = searcher.getDocument(scoreDoc);
			System.out.println("File: " + doc.get(LuceneConstants.FILE_PATH) + " with Score " + scoreDoc.score); 
			a2.add("File: " + doc.get(LuceneConstants.FILE_PATH) + " with Score " + scoreDoc.score);
		} 
		searcher.close(); 
	} 
}	