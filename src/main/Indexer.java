package main;

import java.io.File; 
import java.nio.file.*;
import java.io.FileFilter; 
import java.io.FileReader; 
import java.io.IOException; 
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.charfilter.HTMLStripCharFilter;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.*;
import org.apache.lucene.document.Document; 
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.*;
import org.apache.lucene.store.Directory; 
import org.apache.lucene.store.FSDirectory; 
import org.apache.lucene.util.Version; 
import org.apache.lucene.search.*;
import org.apache.lucene.util.Version;
import org.apache.lucene.index.*;

public class Indexer 
{ 
	private IndexWriter writer;
	private Analyzer analyzer = new StandardAnalyzer();
	private Directory indexDirectory;
	private String format;
	private FileReader fr;
	private static final Analyzer SIMPLE_ANALYZER = new SimpleTokenAnalyzer();
	private static final Analyzer NGRAM_ANALYZER = new Ngram35Analyzer();
	private Analyzer anal;
	public Indexer(String indexDirectoryPath) throws IOException
	{ 	
		//anal = new SimpleTokenAnalyzer();
	 anal = NGRAM_ANALYZER;
		this.indexDirectory = FSDirectory.open(Paths.get(indexDirectoryPath)); 
		IndexWriterConfig iwc = new IndexWriterConfig(anal);
		this.writer = new IndexWriter(indexDirectory, iwc);

	} 
	public void close() throws CorruptIndexException, IOException
	{ 
		this.writer.close(); 
	} 
	
	private Document getDocument(File file) throws IOException
	{ 
		Document document = new Document(); 

		fr = new FileReader(file);

		String[] array = file.getName().toLowerCase().split("[.]");
		String bar = file.getName();
		//System.out.println("to bar einai" + bar);
		bar = bar.substring(0, bar.length() - 4);
		String[] reader = bar.split("=");
		//System.out.println("to split son reader einai:"+ reader[0] + "eiii " + reader[1]);
		document.add(new TextField(LuceneConstants.CONTENTS,fr)); 
		document.add(new TextField(LuceneConstants.FILE_NAME, 
									reader[0], 
									Field.Store.YES));
		document.add(new StringField(LuceneConstants.URL, 
				reader[1], 
				Field.Store.YES));
		document.add(new StringField(LuceneConstants.FILE_PATH, 
									 file.getCanonicalPath(), 
									 Field.Store.NO));
		
		return document; 
	} 
	public void indexFile(File file) throws IOException
	{ 
		format = file.getCanonicalPath();
		//System.out.println("Indexing "+ format);

		Document document = getDocument(file); 
		writer.addDocument(document);
;

	} 
	public void createIndex(String dataDirPath, FileFilter filter) throws IOException
	{ 
		writer.deleteAll();
		//System.out.println("Inside createIndex");
		File[] files = new File(dataDirPath).listFiles(); 
		System.out.println("Directory has "+ files.length + "files");
		for (File file : files) 
		{ 
			if(!file.isDirectory() && !file.isHidden() && file.exists() && file.canRead() && filter.accept(file) )
			{ 
				indexFile(file);
				//fr.close();
			}
		}
		//fr.close();
		writer.close();
	
	} 
	
	public Directory getDirectory() {
		return indexDirectory;
	}
}