package com.jena.model;
import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.sparql.function.library.bnode;
import com.hp.hpl.jena.tdb.TDBFactory;

import java.io.PrintStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;


public class InteractRDF extends Object {
	
	@SuppressWarnings("deprecation")
	public static void main (String args[])  throws IOException {
		
	//String dataFileName = "C:/Documents and Settings/Stefanos/Desktop/ppi";
	
	
	
	//br = new BufferedReader(new FileReader("C:\\testing.txt"));
	
//String sURI = "http://burningbird.net/articles/monsters1.htm";
//String sPostcon = "http://www.burningbird.net/postcon/elements/1.0/";
//String sRelated = "related";
		
	try {
File datap = new File("C:/Users/nodarakis/Desktop/PPI/interactions.log");
//System.out.println(datap.getCanonicalPath());
BufferedReader bReader = new BufferedReader(
        new FileReader(datap));



String line;

String directory="C:/Users/nodarakis/workspace/RDF/int_model";
//File file  = new File("C://Users//nodarakis//workspace//RDF//int_model.xml");
//PrintStream printStream = new PrintStream(new FileOutputStream(file));
//System.setOut(printStream);//this turns output from console to the file specified above


//Create an empty graph
	Model model = TDBFactory.createModel(directory);

while (((line = bReader.readLine()) != null)) {
	
	
    /**
     * Splitting the content of tabbed separated line
     */
    String datavalue[] = line.split("\t",-1);
    String value1 = datavalue[0];
    String value2 = datavalue[1];
    String value3 = datavalue[2];
    long value4 = Integer.parseInt(datavalue[3]);
    
    String sPostcon="http://www.protein_interactions.com/";
    String sRelated="interacts";//"interactorA"; //define properties
   // String sRelated1="interactorB";
    String sRelated2="Interaction_type";
    String sRelated3="PubIdentifier";
    //String sRelated4="interaction";
    
    
    	

// Create the resources
Resource protein = model.createResource(sPostcon+value1);
Resource protein2 = model.createResource(sPostcon+value2);
Resource interaction = model.createResource(sPostcon+"interaction");

// Create the predicate (property)
Property interacts=model.createProperty(sPostcon,sRelated);
//Property interactorA = model.createProperty(sPostcon, sRelated);
//Property interactorB = model.createProperty(sPostcon, sRelated1);
Property type = model.createProperty(sPostcon, sRelated2);
Property pub = model.createProperty(sPostcon, sRelated3);
interaction.addProperty(interacts,
	model.createResource()
	.addProperty(interacts,protein)
	.addProperty(interacts,protein2)
	.addProperty(type,value3,XSDDatatype.XSDstring)
	.addLiteral(pub,value4));

//we refer to the same blank node
		//.addProperty(type,sRelated2)
		//.addProperty(pub,sRelated3));

//Add the properties with associated values (objects)
//interaction.addProperty(interactorA,
	//	value1,XSDDatatype.XSDstring);

//interaction.addProperty(interactorB,
	//	value2,XSDDatatype.XSDstring);




//Add the properties with associated values (objects)





}
//Print RDF/XML of model to system output
model.write(new PrintWriter(System.out));
model.close();

bReader.close();


} catch (Exception e) {
System.out.println("Failed: " + e);
}

	}	
	
}


