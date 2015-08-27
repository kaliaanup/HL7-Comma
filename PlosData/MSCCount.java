import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import javax.xml.parsers.*;

import org.xml.sax.SAXException;

import org.w3c.dom.*;
import org.xml.sax.SAXException;



public class MSCCount {
	
	public  DocumentBuilderFactory factory;
	public  DocumentBuilder builder;
	public  Document document;
	public NodeList nodes_i ;
	public static String mscModelFileName = new String();
	
	private static Hashtable<String,String> messageSequenceChart=new Hashtable<String,String>();
	private static Hashtable<String,String> messages=new Hashtable<String,String>();
	public static Set<String> uniqueMessages = new HashSet<String>();
	private static Hashtable<String,String> altOperand=new Hashtable<String,String>();
	private static Hashtable<String,String> optOperand=new Hashtable<String,String>();
	
	
	public boolean parseMSCModel(String filename)
	{
		 	factory = DocumentBuilderFactory.newInstance();
		
			try {
				builder = factory.newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				
				e.printStackTrace();
				return false;
			}
			try {
				document = builder.parse(filename);
				//nodes_i = document.getElementsByTagName("packagedElement");
				
			} catch (SAXException e) {
				
				e.printStackTrace();
				return false;
			} catch (IOException e) {
				
				e.printStackTrace();
				return false;
			}
		
		return true;
	}
public void unparseSequenceModel() throws  SAXException,IOException,ParserConfigurationException{
		
		String collaborationName = new String("");
		String interactionName = new String("");
		String messageSequenceChartName=new String("");
		
		nodes_i = document.getElementsByTagName("packagedElement");
		for(int i=0;i < nodes_i.getLength();i++)
		{
			Node node_i = nodes_i.item(i);
			if (node_i.getNodeType() == Node.ELEMENT_NODE)
			{
				Element packagedElement = (Element) node_i;
				collaborationName=packagedElement.getAttribute("name");//get CollaborationName
				//parse through all ownedBehaviour .Each ownedBehaviour represent a sequence diagram
				NodeList nodes_j = packagedElement.getElementsByTagName("ownedBehavior");
				for (int j = 0; j < nodes_j.getLength(); j++)
				{
					Node node_j=nodes_j.item(j);
					if (node_j.getNodeType() == Node.ELEMENT_NODE)
					{
						Element ownedBehaviorElement=(Element) node_j;
						
						interactionName=ownedBehaviorElement.getAttribute("name");//Store interaction name
						messageSequenceChartName=collaborationName+"_"+interactionName;
						messageSequenceChart.put(ownedBehaviorElement.getAttribute("xmi:id"),messageSequenceChartName);
						/*-------------------------MESSAGES------------------------------*/
						NodeList nodes_k = ownedBehaviorElement.getElementsByTagName("message");
						for (int k = 0; k < nodes_k.getLength(); k++)
						{
							Node node_k=nodes_k.item(k);
							Element message=(Element) node_k;
							//store all possible messages
							if(!messages.containsKey(message.getAttribute("xmi:id")))
							{
								messages.put(message.getAttribute("xmi:id"),message.getAttribute("name"));
							}
						}
						/*-----------------------------ALT NODES & OPT NODES-----------------------------*/
						NodeList nodes_l = ownedBehaviorElement.getElementsByTagName("fragment");
						for(int l = 0;l<nodes_l.getLength();l++)
						{
							Node node_l=nodes_l.item(l);
							if(node_l.getNodeType()== Node.ELEMENT_NODE)
							{
								Element fragment=(Element) node_l;
								/*-------------ALT----------------------------*/
								if(fragment.getAttribute("interactionOperator").equals("alt"))
								{
									if(!altOperand.containsKey(fragment.getAttribute("xmi:id")))
									{
										altOperand.put(fragment.getAttribute("xmi:id"),fragment.getAttribute("interactionOperator"));
									}
								}
								/*-----------OPT----------------------------*/
								if(fragment.getAttribute("interactionOperator").equals("opt"))
								{
									if(!optOperand.containsKey(fragment.getAttribute("xmi:id")))
									{
										optOperand.put(fragment.getAttribute("xmi:id"),fragment.getAttribute("interactionOperator"));
									}
								}
							}
						}
					}//end if
				}//end for
			}//end if
		}
   }
	public static void main(String args[]) throws Exception {
		// TODO Auto-generated method stub
		MSCCount instUml = new MSCCount();
		mscModelFileName=args[0].toString();
		if(instUml.parseMSCModel(mscModelFileName))
		{
			System.out.println("-----------------------------------------------------------------------");
			System.out.println("## PROTOS PARSED THE MESSAGE SEQUENCE CHART UML MODEL FILE SUCCESSFULLY ##");
			System.out.println("-----------------------------------------------------------------------");
		}
		else
		{
			System.out.println("PROTOS FAILED TO PARSE THE FILE");
			System.out.println("THANKS FOR USING PROTOS");
			System.exit(0);
		}
		instUml.unparseSequenceModel();
		String name=new String();
		String id = new String();
		Enumeration m;
		m=messages.keys();
		while(m.hasMoreElements())
		{
			id=m.nextElement().toString();
			name=messages.get(id).toString();
			uniqueMessages.add(name);
		}
		System.out.println("Total no of message sequence charts="+messageSequenceChart.size());
		System.out.println("Total no of messages="+messages.size());
		System.out.println("Total no of distinct messages="+uniqueMessages.size());
		System.out.println("Total no of alts="+altOperand.size());
		System.out.println("Total no of opts="+optOperand.size());
		
	}

}
