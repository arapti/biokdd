package com.jena.model;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.ReadWrite;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.tdb.TDBFactory;

import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ByteArrayInputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.Date;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.InputSource;




public class Merge {
	
	public static void main (String args[]) throws IOException{
		
		String directory="C:/Users/nodarakis/workspace/RDF/fin_model";
		String dir="C:/Users/nodarakis/workspace/RDF/int_model";
		String dir2="C:/Users/nodarakis/workspace/RDF/prot_model";
		//File file  = new File("C://Users//nodarakis//workspace//RDF//testing");
		//PrintStream printStream = new PrintStream(new FileOutputStream(file));
		//System.setOut(printStream);//this turns output from console to the file specified above
		
		
		// use the FileManager to find the input file
		// InputStream in1 = new FileInputStream("C://Users//nodarakis//workspace//RDF//int_model.xml");
		//if (in1 == null) {
		  //  throw new IllegalArgumentException(
		          //                       "File: " + in1 + " not found");
		//}
		
		  //  InputStream in2 = new FileInputStream("C://Users//nodarakis//workspace//RDF//prot_model.xml");
			//if (in2 == null) {
			  //  throw new IllegalArgumentException(
			    //                             "File: " + in2 + " not found"); 
		    
			//}
			
			//Reader reader = new InputStreamReader(in2,"UTF-8");
			 
			//InputSource is = new InputSource(reader);
			//is.setEncoding("UTF-8");
			 
			//saxParser.parse(is, handler);
		
		Model model1 = TDBFactory.createModel(dir);
		Model model2 = TDBFactory.createModel(dir2);
		
		
		
		Dataset ds=TDBFactory.createDataset(directory);
		//we create the final model where the previous models are merged
		Model model = ds.getDefaultModel();
		
		
		//model1.read((in1), "C://Users//nodarakis//workspace//RDF//int_model ");
		//model2.read((in2), "C://Users//nodarakis//workspace//RDF//prot_model");
		
		
		//we create the final model where we merge models model1 and model2
		//Model model = ModelFactory.createDefaultModel();
		
		model=model1.union(model2);
	
		
		// print the Model as RDF/XML
		////model.write(System.out, "Turtle");
		//ds.close();
		
		model.close();
	
		
	}
	

}

