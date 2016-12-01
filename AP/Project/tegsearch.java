import java.io.*;
import java.util.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

//java -Xmx2048m -Xms2048m -DentityExpansionLimit=100000000 Javaparser
public class tegsearch
{
    String authorName,queryname,titleName;
    int counter=0,index=0,queryNo=0;

	List<List<String>> values = new ArrayList<List<String>>();
	List<String> singleVal = new ArrayList<String>();
	List<String> commonauthors = new ArrayList<String>();

	String[] tags;
	String name;
	int hits=0;


	public void prepare(List<String> arr)
	{
		for(int i=0;i<8;i++)
			arr.add("-");
	}


	public tegsearch(String attribute)
	{	
		
		tags= attribute.split("\\s+");
		System.out.println(Arrays.toString(tags));

		try
		{
		SAXParserFactory parserFactory = SAXParserFactory.newInstance();
		SAXParser parser = parserFactory.newSAXParser();
	    DefaultHandler handler = new Parser();
	    parser.parse(new File("dblp.xml"), handler);
	     	
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (SAXException e){
			e.printStackTrace();
		}
		catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		
	}
	

		
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		//new Javaparser(in.nextLine());
		new tegsearch("Parallel Algorithms for Finding the Most Vital Edge in Weighted Graphs.");
	}

	private class Parser extends DefaultHandler
	{
			boolean author,title,pages,year,volume,journal,url;
			boolean hasmatched,authdetails;
			String tempdata;
			
			public Parser() {
				author=title=pages=year=volume=journal=url=false;
				hasmatched=false;
				tempdata=null;

			}

			public void startDocument()
			{
			System.out.println("Begin parsing the document .. ");
			}

			public void endDocument()
			{
			System.out.println("End parsing document ..");
			}

			@Override
			public void startElement(String namespaceURI, String localName,String rawName, Attributes atts) throws SAXException {
				
				if (rawName.equalsIgnoreCase("article")||rawName.equalsIgnoreCase("inproceedings")||rawName.equalsIgnoreCase("proceedings")||rawName.equalsIgnoreCase("book")||rawName.equalsIgnoreCase("incollection")||rawName.equalsIgnoreCase("phdthesis")||rawName.equalsIgnoreCase("mastersthesis"))
				{
					hasmatched=false; 	//every tag is closed whenever a new article is encountered
					prepare(singleVal);
				}

				if(rawName.equalsIgnoreCase("www"))
				{
					authdetails=true;
				}
				
				if(rawName.equalsIgnoreCase("author")||rawName.equalsIgnoreCase("editor"))
				{
					if(authdetails==false)
						{ author=true;
							name="";
						}
				}
				
				else if(rawName.equalsIgnoreCase("title"))
				{
					title=true;
				}
			
				if(hasmatched==true)	//if matched
				{
				    
				    if(rawName.equalsIgnoreCase("journal"))
					{
						journal=true;
					}
				    if(rawName.equalsIgnoreCase("year"))
					{
						year=true;
					}
				    if(rawName.equalsIgnoreCase("pages"))
					{
						pages=true;
					}	
				    if(rawName.equalsIgnoreCase("volume"))
					{
						volume=true;
					}
				    if(rawName.equalsIgnoreCase("url"))
					{
						url=true;
					}
				}
			}
		
			@Override
			public void characters(char ch[], int start, int length) throws SAXException {
				tempdata=new String(ch, start, length);
				String[] temp = tempdata.split("\\s+");
				if(title)	//query2
				{
					for(String s : tags)
					{
						for(String t : temp)
						{
							if(s.equals(t))
							{
								hits++;
								if(t.equalsIgnoreCase("for") || t.equalsIgnoreCase("the") || t.equalsIgnoreCase("of") || t.equalsIgnoreCase("in") || t.equalsIgnoreCase("at") || t.equalsIgnoreCase("and") || t.equalsIgnoreCase("are"))
									hits--;
								break;
							}
						}
					}

					if(hits>tags.length/2)
					{
						//System.out.println("HITT: " + hits);
						//System.out.println(tempdata);
						counter++; 
						hasmatched=true;

						StringBuilder listString = new StringBuilder();

						for (String s : commonauthors)
						     listString.append(s+";");		//adding common authors to a single string

						 commonauthors=new ArrayList<String>();

						 singleVal.set(0,listString.toString());
						 singleVal.set(1,tempdata);
					}

					
					title=false;
				}				
					
					if(author)
					{
						name+=tempdata;
						//singleVal.add(tempdata);
						// author=false;	//closing tag
						// commonauthors.add(tempdata);
					}
					if(journal)
					{
						//journalList.add(tempdata);
						singleVal.set(2,tempdata);
						//System.out.println(tempdata);
						journal=false;
					}

					if(year)
					{
						//yearList.add(tempdata);
						singleVal.set(3,tempdata);
						//System.out.println(tempdata);
						year=false;
					}
					if(pages)
					{
						//pageList.add(tempdata);	
						singleVal.set(4,tempdata);
						//System.out.println(tempdata);
						pages=false;
					}
					if(volume)
					{
						//volumeList.add(tempdata);
						singleVal.set(5,tempdata);
						//System.out.println(tempdata);
						volume=false;
					}
					if(url)
					{
						//urlList.add(tempdata);
						singleVal.set(6,tempdata);
						//System.out.println(tempdata);
						url=false;
					}
				
				
			}
		       
		    

		    @Override
		    public void endElement(String uri, String localName, String qName) throws SAXException {
		    	if (qName.equalsIgnoreCase("article")||qName.equalsIgnoreCase("inproceedings")||qName.equalsIgnoreCase("proceedings")||qName.equalsIgnoreCase("book")||qName.equalsIgnoreCase("incollection")||qName.equalsIgnoreCase("phdthesis")||qName.equalsIgnoreCase("mastersthesis")||qName.equalsIgnoreCase("www"))
		    	{
		    	 
		    		if(hasmatched==true)	//if true add list to collection
					{	
		    			//System.out.println(Arrays.toString(singleVal.toArray()));
		    			singleVal.set(7,Integer.toString(hits));
		    			values.add(singleVal);
		    			//System.out.println(Arrays.toString(values.toArray()));
		    			//NOTE: by just clearing arraylist singlval, data was being overwritten to the same address and hence values was storing only 1 list n times
		    			singleVal=new ArrayList<String>();
		    		}

				    hasmatched=false;
				    authdetails=false;
				    hits=0;
				    singleVal.clear();	
				    commonauthors.clear();   		
				    
				}

				if(qName.equalsIgnoreCase("author")||qName.equalsIgnoreCase("editor"))
				{
					commonauthors.add(name);
					author=false;
				}
		    	
		    	if(qName.equals("dblp"))
		    	{
		    		System.out.println("It rocks"+ counter);
		    		printData();
		    	}
			      // System.out.println(x);
		    }

	public void printData()
	{
		Iterator<List<String>> it = values.iterator();


		while(it.hasNext())
			{
				System.out.println(it.next());
			}

		
	}

	}
	
}