import java.io.*;
import java.util.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Query2
{
	 HashMap<String,Integer> authorPublication = new HashMap<String, Integer>();
	List<String> authorList = new ArrayList<String>();
	String name;
	
	public Query2()
	{	
		//new entityResolution();
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
		new Query2();

		//printData();
	}

	private class Parser extends DefaultHandler
	{
			boolean author,article;
			String tempdata;
			
			public Parser() {
				author=false;
				article=false;
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
				
				if (rawName.equalsIgnoreCase("article")||rawName.equalsIgnoreCase("inproceedings")||rawName.equalsIgnoreCase("proceedings")||rawName.equalsIgnoreCase("book")||rawName.equalsIgnoreCase("incollection")||rawName.equalsIgnoreCase("phdthesis")||rawName.equalsIgnoreCase("mastersthesis")||rawName.equalsIgnoreCase("wwww"))
				{
					article=true; 
				}

				if(rawName.equalsIgnoreCase("author")||rawName.equalsIgnoreCase("editor"))
				{
					author=true;
					name="";
				}
			}
		
			@Override
			public void characters(char ch[], int start, int length) throws SAXException {
				tempdata=new String(ch, start, length);

					if(author)
					{				
						name+=tempdata;
					}

			}

		    @Override
		    public void endElement(String uri, String localName, String qName) throws SAXException {
		    	if (qName.equalsIgnoreCase("article")||qName.equalsIgnoreCase("inproceedings")||qName.equalsIgnoreCase("proceedings")||qName.equalsIgnoreCase("book")||qName.equalsIgnoreCase("incollection")||qName.equalsIgnoreCase("phdthesis")||qName.equalsIgnoreCase("mastersthesis")||qName.equalsIgnoreCase("www"))
		    	{
		    	 	article=false;	  
				}

				if(qName.equalsIgnoreCase("author")||qName.equalsIgnoreCase("editor"))
				{
					if(authorPublication.containsKey(name))
							authorPublication.put(name,authorPublication.get(name)+1);
						else
							authorPublication.put(name,1);

					author=false;
				 }
		    	
		    	if(qName.equals("dblp"))
		    	{
		    		System.out.println("It rocks");
		    		//printData();
		    	}
			  
		    }

		public void printData()
	{
		for(String auth : authorPublication.keySet())
		{
			System.out.println(auth + " " + authorPublication.get(auth));
		}		
	}

	}
	
}
