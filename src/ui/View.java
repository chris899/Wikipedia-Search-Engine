package ui;
import main.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.awt.EventQueue;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.net.URL;
import java.time.LocalDate;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Desktop;

import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import java.awt.Choice;

public class View {

	private JFrame frame;

	private static ArrayList<String> urlist = new ArrayList<String>();
	private static String indexDir = "./"; 
	private JWindow autoSuggestionPopUpWindow;
    private JPanel suggestionsPanel;

	String dataDir = "./"; 
	Indexer indexer; 
	private static Searcher searcher;
	String erwtisi;
	ArrayList<String[]> a1 = new ArrayList<String[]>();
	ArrayList<String> a2 = new ArrayList<String>();
	ArrayList<JLabel> lab;
	int occupied = 0;
	int pageResults[] = new int[10];
	JPanel p = new JPanel();
	JPanel[] panels = new JPanel[10];
	int j ;
	int thesi;
	Boolean clicked = false;
	JLabel la;
	
	 public  JLabel mak(final String s,final String link, float x, float y){
	       final JLabel l = new JLabel(s);
	       l.addMouseListener(new MouseAdapter()
	       {

	           @Override
	           public void mouseExited(MouseEvent arg0)
	           {
	               l.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	               l.setText(s);
	           }

	           @Override
	           public void mouseEntered(MouseEvent arg0)
	           {
	               l.setCursor(new Cursor(Cursor.HAND_CURSOR));
	               l.setText(String.format("<HTML><FONT color = \"#000099\"><U>%s</U></FONT></HTML>", s));
	           }

	           @Override
	           public void mouseClicked(MouseEvent arg0)
	           {
	               try
	               {
	                   URI uri = new URI(link);
	                 
	                   if (Desktop.isDesktopSupported())
	                       Desktop.getDesktop().browse(uri);
	               } catch (Exception e)
	               {
	               }
	           }
	       });
	       int v1 = (int)x;
	       int v2 = (int)y;
	       l.setBounds(v1, v2, s.length()*5, 20);
	       l.setToolTipText(String.format("go to %s", link));
	       return l;
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
	/*private void search(String searchQuery) throws IOException, ParseException
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
			//System.out.println("File: " + doc.get(LuceneConstants.FILE_PATH) + " with Score " + scoreDoc.score); 
			a2.add("File: " + doc.get(LuceneConstants.FILE_NAME) + " with Score " + scoreDoc.score);
			String [] outp = new String[3];
			outp[0]=doc.get(LuceneConstants.FILE_NAME);
			outp[1] = " ------>Score: " + scoreDoc.score;
			outp[2] = doc.get(LuceneConstants.URL);
			a1.add(outp);
			//a1.add(doc.get(LuceneConstants.FILE_NAME));
		} 
	} */
	
	/**
	 * Launch the application.
	 */
	static MultithreadingScrapper object;
	static MainThread object2 = new MainThread("obj2"); 
	public synchronized static void main(String args[]) throws InterruptedException, IOException {
		searcher = new Searcher(indexDir);
		//object = new MultithreadingScrapper("obj1"); 
		
		object2.start();
		
		//object.start();
		//object.t.join();
		//client();
	}
	
	ArrayList<String> history = new ArrayList<String>();
	public ArrayList<String> takeHistory(){
		return this.history;
	}
	
	QueryThread queryth;
	
	public void setStart(String descr) {
		try {
			queryth.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(history.isEmpty()) {
			history.add(descr);
		}
		else {
			boolean uparcei = false;
			for(String htemp : history) {
				if(htemp.equals(descr)) {
					//uparcei idi i leksi sto history
					uparcei = true;
				}	
			}
			if(uparcei == false) {
				history.add(descr);
			}
		}
		writeHistoryToFile(history);
		
		try {
			FileWriter writer = new FileWriter("./YourFile.txt");
			String str = descr.toLowerCase();
			str = str.replaceAll("s$","");
			
			writer.write(str);
	        //writer.write("\r");   // write new line
	        writer.close();

	        Process p = Runtime.getRuntime().exec("cmd /c python ./eii.py");
	        p.waitFor();
		} catch (IOException | InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		clicked = true;
		a1.clear();
		
		try {
			searcher.search(descr);
			a1 = searcher.getArray();
			
		} catch (IOException | ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 

		//urlist = object.getScrapped();
		lab = new ArrayList<JLabel>();				
		for(String[] aa:a1) {

			la = new JLabel();
			la = mak(aa[0]+aa[1],"https://en.wikipedia.org/?curid="+aa[2],0,0);
			lab.add(la);
			
		}
		p.removeAll();
		p.updateUI();
		p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		j = 0;
		thesi = -1;
		for(int k = 0; k <10; k++){
			if(k==lab.size()) {
				break;
			}
			p.add(lab.get(k));
			j++;
			
		}
		panels[0] = p;
		pageResults[0] = j;
		thesi++;
		occupied = 0;
		scrollPane.setViewportView(panels[0]);
		frame.getContentPane().add(scrollPane);
	}
	
	public void writeHistoryToFile(ArrayList<String> history){
		
		History h = new History(history);
		
		try {
			FileOutputStream f = new FileOutputStream(new File("./History"));
			ObjectOutputStream o = new ObjectOutputStream(f);

			o.writeObject(h);
			o.close();
			f.close();

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Error initializing stream");
		}
	}
	
	public void readHistoryFromFile() {
		try {

			FileInputStream fi = new FileInputStream(new File("./History"));
			ObjectInputStream oi = new ObjectInputStream(fi);

			History h = (History) oi.readObject();
			

			history = h.getHistory();

		

			oi.close();
			fi.close();

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Error initializing stream");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void startNewQueryThread() {
		queryth = new QueryThread("obj2",this); 
	}
	
	
	
	
	public static void client() {
		EventQueue.invokeLater(new Runnable() {
			
			
			public void run() {
				
				
				try {
					View window = new View();
					
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
	}

	/**
	 * Create the application.
	 */
	private ViewController contr;

	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final ButtonGroup headerGroup = new ButtonGroup();

	/**
	 * @wbp.parser.entryPoint
	 */
	public View() throws IOException, InterruptedException {
		initialize();
		contr = new ViewController();

		
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	JScrollPane scrollPane = new JScrollPane();
	private void initialize() throws IOException, InterruptedException {
		readHistoryFromFile();
		frame = new JFrame("Wiki Search");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//object.t.join();
		//createIndex();
		
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		

		JMenuItem searchQuery = new JMenuItem("Search");
		searchQuery.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(SwingUtilities.isLeftMouseButton(e)) {
					JOptionPane pane = new JOptionPane();
					
					//String descr = customInputDialog(); //pane.showInputDialog("Give a query!");
	
					startNewQueryThread();
					queryth.start();
				
								
								
					
					
					
				}
			}
		});
		menuBar.add(searchQuery);	

	
		JMenuItem nextPage = new JMenuItem("Next Page");
		nextPage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(SwingUtilities.isLeftMouseButton(e) && clicked == true && j < lab.size()) {
					System.out.println("inside next page");
						JPanel p = new JPanel();
						p.removeAll();
						p.updateUI();
						int temporary = j;
						p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
						if(thesi < occupied) {
							System.out.println("inside if page");
							thesi++;
							j = j + pageResults[thesi];
							scrollPane.setViewportView(panels[thesi]);
							frame.getContentPane().add(scrollPane);
						}
						else {
							System.out.println("inside else page");
							for(int k = j; k<temporary + 10; k++){
								if(k==lab.size()) {
									break;
								}	
								p.add(lab.get(k));
								j++;
							}
							thesi++;
							occupied++;
							panels[thesi] = p;
							pageResults[thesi] = j - temporary;;
							scrollPane.setViewportView(panels[thesi]);
							frame.getContentPane().add(scrollPane);
						}
				}
			}
		});
		menuBar.add(nextPage);
		
		JMenuItem previusPage = new JMenuItem("Previous Page");
		previusPage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(SwingUtilities.isLeftMouseButton(e) && clicked == true) {
						System.out.println("previus page");
						if(thesi > 0) {
							JPanel p = new JPanel();
							p.removeAll();
							p.updateUI();
							p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
							System.out.println("previus page if");
							j = j - pageResults[thesi];
							thesi--;
							scrollPane.setViewportView( panels[thesi]);
							frame.getContentPane().add(scrollPane);
						}	
				}
			}
		});
		menuBar.add(previusPage);	
	}

	
}