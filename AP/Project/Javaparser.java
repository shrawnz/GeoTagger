import java.io.*;
import java.util.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

//java -Xmx2048m -Xms2048m -DentityExpansionLimit=100000000 Javaparser
public class Javaparser 
{
    String authorName,queryname,titleName;
    int counter=0,index=0,queryNo=0;

    String name;
   	static List<String> authorNames = new ArrayList<String>();
	
	List<List<String>> values = new ArrayList<List<String>>();
	List<String> singleVal = new ArrayList<String>();
	List<String> commonauthors = new ArrayList<String>();


	public void prepare(List<String> arr)
	{
		for(int i=0;i<7;i++)
			arr.add("-");
	}
	public void entityResolution(String s)
	{
		authorResolution obj = new authorResolution(s); //entity resolution
		authorNames=obj.authorList;
		authorNames.add(s);
		System.out.println(Arrays.toString(authorNames.toArray()));
	}
	

	public Javaparser(String attribute)
	{	

			 authorName=attribute;
			 
			entityResolution(attribute);

			
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
		new Javaparser("Chin-Chen Chang");

				
		
	}

	private class Parser extends DefaultHandler
	{
			boolean author,title,pages,year,volume,journal,url;
			boolean hasmatched,authdetails;
			String tempdata;
			
			public Parser() 
			{
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
			
				if(hasmatched==true)	//if matched
				{
				    
				    if(rawName.equalsIgnoreCase("title"))
					{
						title=true;
					}
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

				if(author)	//query1
				{
					for(String s : authorNames)
					{	
						if(tempdata.equals(s))
						{
							counter++; 
							hasmatched=true;
							break;
						}
					}
						//authorList.add(tempdata);
						name+=tempdata;
						//author=false;	//closing tag
				
				}		
					
					if(title)
					{
						
						StringBuilder listString = new StringBuilder();

						for (String s : commonauthors)
						     listString.append(s+" ");		//adding common authors to a single string

						 commonauthors=new ArrayList<String>();
						 singleVal.set(0,listString.toString());
						//System.out.println(tempdata);
						//titleList.add(tempdata);
						singleVal.set(1,tempdata); //title comes before pages, hence page add shifts title to index 1 automatically

						title=false;

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
		    			values.add(singleVal);
		    			//System.out.println(Arrays.toString(values.toArray()));
		    			//NOTE: by just clearing arraylist singlval, data was being overwritten to the same address and hence values was storing only 1 list n times
		    			singleVal=new ArrayList<String>();
		    		}

				    hasmatched=false;
				    authdetails=false;
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
		    		//printData();
		    	}
			      // System.out.println(x);
		    }

	public void printData()
	{
		// Iterator<String> authorItr = authorList.iterator();
		// Iterator<String> titleItr = titleList.iterator();
		// Iterator<String> pageItr = pageList.iterator();
		// Iterator<String> yearItr = yearList.iterator();
		// Iterator<String> volumeItr = volumeList.iterator();
		// Iterator<String> journalItr = journalList.iterator();
		// Iterator<String> urlItr = urlList.iterator();

		Iterator<List<String>> it = values.iterator();
		//HashMap<Integer,Integer> publications = new HashMap<Integer, Integer>();


		while(it.hasNext())
			{
				
				System.out.println(Arrays.toString(it.next().toArray()));
			}			
	}
	// 			List<String> temp = it.next();
	// 			int y = Integer.parseInt(temp.get(0));
				
	// 			if(publications.containsKey(y))
	// 				publications.put(y,publications.get(y)+1);
	// 			else
	// 				publications.put(y,1);

			
	// 		}

	// 		for (Integer n: publications.keySet())
	// 				{

	// 	            String key =n.toString();
	// 	            String value = publications.get(n).toString();  
	// 	            System.out.println(key + " " + value);  

	// 				} 		
	// }

	}
	
}
