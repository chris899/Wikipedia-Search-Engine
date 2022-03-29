
package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer; 
import org.apache.lucene.document.Document; 
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.*; 
import org.apache.lucene.search.Query;
import org.apache.lucene.util.QueryBuilder;
import org.apache.lucene.search.ScoreDoc; 
import org.apache.lucene.search.TopDocs; 
import org.apache.lucene.store.Directory; 
import org.apache.lucene.store.FSDirectory; 
import org.apache.lucene.util.Version;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.BoostQuery;
import org.apache.lucene.search.IndexSearcher;

public class Searcher 
{ 
	IndexSearcher indexSearcher; 
	QueryParser queryParser1;
	QueryParser queryParser2; 
	Query query;
	private Analyzer analyzer = new StandardAnalyzer();
	private static final Analyzer SIMPLE_ANALYZER = new SimpleTokenAnalyzer();
	private Analyzer analz;
	private ArrayList<String[]> a1 = new ArrayList<String[]>();
	private TopDocs hits;
	private FileReader fr; 
	private BufferedReader reader;
	
	public Searcher(String indexDirectoryPath) throws IOException
	{ 
		//anal = new SimpleTokenAnalyzer();
		analz = new Ngram35Analyzer();
		Directory indexDirectory = FSDirectory.open(Paths.get(indexDirectoryPath));
		DirectoryReader ireader = DirectoryReader.open(indexDirectory);
		indexSearcher = new IndexSearcher(ireader);
		queryParser1 = new QueryParser(LuceneConstants.CONTENTS,analz);
		//queryParser1.setDefaultOperator(QueryParser.Operator.AND);
		queryParser2 = new QueryParser(LuceneConstants.FILE_NAME,analz);
		//queryParser2.setDefaultOperator(QueryParser.Operator.AND);
	} 

	public void search( String searchQuery) throws IOException, ParseException
	{ 
		query = queryParser1.parse(searchQuery);
		Query q = queryParser2.parse(searchQuery);
		Query boostedName = new BoostQuery(q, 4);
		System.out.println("to query einai :" + query);
		System.out.println("to Q einai :" + q);
	    BooleanQuery.Builder chainQryBldr = new BooleanQuery.Builder();
	    chainQryBldr.add(query, Occur.SHOULD);
	    chainQryBldr.add(boostedName, Occur.SHOULD);
	    fr = new FileReader("./embedResults.txt"); 
	    reader = new BufferedReader(fr);
	    String line = reader.readLine();
	    // reading the results of the embeddings, similar words, meaning, query 
	    while (line != null) {
	    	System.out.println("h grammh einai" + line);
	    	Query q0 = queryParser1.parse(line);
			Query q1 = queryParser2.parse(line);
			Query boostedName1 = new BoostQuery(q1,2);
		    chainQryBldr.add(q0, Occur.SHOULD);
		    chainQryBldr.add(boostedName1, Occur.SHOULD);
	    	line = reader.readLine();
	    }
	    fr.close();
	    reader.close();
	    BooleanQuery finalQry = chainQryBldr.build();
	    hits = indexSearcher.search(finalQry, LuceneConstants.MAX_SEARCH); 
	    results();

	} 
	
	public Document getDocument(ScoreDoc scoreDoc) throws CorruptIndexException, IOException
	{ 
		return indexSearcher.doc(scoreDoc.doc); 
	} 
	
	private void results() throws CorruptIndexException, IOException {
		System.out.println(hits.totalHits + " documents found. Time :"); 
		for(ScoreDoc scoreDoc : hits.scoreDocs) 
		{ 
			Document doc = getDocument(scoreDoc);
			//System.out.println("File: " + doc.get(LuceneConstants.FILE_NAME) + " with Score " + scoreDoc.score); 
			String [] outp = new String[3];
			outp[0]=doc.get(LuceneConstants.FILE_NAME);
			outp[1] = " ------>Score: " + scoreDoc.score;
			outp[2] = doc.get(LuceneConstants.URL);
			a1.add(outp);
			//a1.add(doc.get(LuceneConstants.FILE_NAME));	
		} 
	}
	
	public ArrayList<String[]> getArray(){
		return a1;
	}

}