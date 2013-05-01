package com.jena.model;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Resource;
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



public class Proteins {
	
	
	public static void main (String args[])  throws IOException {
		try {
			
			File datap2 = new File("C:/Users/nodarakis/Desktop/PPI/proteins.log");
			//System.out.println(datap.getCanonicalPath());
			BufferedReader log = new BufferedReader(
			    new FileReader(datap2));
			
			String line2;
			String line;

			String directory="C:/Users/nodarakis/workspace/RDF/prot_model";
			
			//File file  = new File("C://Users//nodarakis//workspace//RDF//prot_model.xml");
			//PrintStream printStream = new PrintStream(new FileOutputStream(file));
			//System.setOut(printStream);//this turns output from console to the file specified above
			
			//Create an empty graph
			//Model model = ModelFactory.createDefaultModel();
			Model model = TDBFactory.createModel(directory);
			
			while ((line2 = log.readLine()) != null){
			
				
			/**
		     * Splitting the content of tabbed separated line
		     */
			String datavalue2[] = line2.split("\t",-1);
	    	String val1 = datavalue2[0];
	    	String val2 = datavalue2[1];
	    	String val3 = datavalue2[2];
	    	String val4 = datavalue2[3];
	    	String val5 = datavalue2[4];
	    	long val6 = Integer.parseInt(datavalue2[5]);
	    	String val7 = datavalue2[6];
	    	String val8 = datavalue2[7];
	    	long val9 = Integer.parseInt(datavalue2[8]);
	    	String val10 =datavalue2[9];//"null";
	    	String val11= datavalue2[10];//"null";
	    	//if (datavalue2[9]!=null){
	    		//val10=datavalue2[9];
	    	//} 
	    	
	    	
	    	//if (datavalue2[10]!=null){
	    		//val11 = datavalue2[10];
	    	//}
	    	
	    	
	    	
	    	String sPostcon2="http://www.protein_interactions.com/";
	        String sRelated4="Accession"; //define properties
	        String sRelated5="Entry_Name";
	        String sRelated6="Status";
	        String sRelated7="Protein_Name";
	        String sRelated8="Gene_names";
	        String sRelated9="Length";
	        String sRelated10="Protein_existence";
	        String sRelated11="Date_of_Modification";
	        String sRelated12="Pubmed_Id";
	        String sRelated13="Subcellular_location";
	        String sRelated14="Protein_Family";
	        
	      //Create the resource
	        Resource proteins = model.createResource(sPostcon2+val1);
	        //Create the predicate (property)
	        Property Accession = model.createProperty(sPostcon2, sRelated4);
	        //Add the properties with associated values (objects)
	        proteins.addProperty(Accession,
	        val1,XSDDatatype.XSDstring);


	        Property entry_name = model.createProperty(sPostcon2, sRelated5);
	        //Add the properties with associated values (objects)
	        proteins.addProperty(entry_name,
	        val2,XSDDatatype.XSDstring);


	        Property status = model.createProperty(sPostcon2, sRelated6);
	        //Add the properties with associated values (objects)
	        proteins.addProperty(status,
	        val3,XSDDatatype.XSDstring);


	        Property protein_name = model.createProperty(sPostcon2, sRelated7);
	        //Add the properties with associated values (objects)
	        proteins.addProperty(protein_name,
	        val4,XSDDatatype.XSDstring);


	        Property gene_names = model.createProperty(sPostcon2, sRelated8);
	        //Add the properties with associated values (objects)
	        proteins.addProperty(gene_names,
	        val5,XSDDatatype.XSDstring);



	        Property length = model.createProperty(sPostcon2, sRelated9);
	        //Add the properties with associated values (objects)
	        proteins.addLiteral(length,
	        val6);


	        Property protein_existence = model.createProperty(sPostcon2, sRelated10);
	        //Add the properties with associated values (objects)
	        proteins.addProperty(protein_existence,
	        val7,XSDDatatype.XSDstring);



	        Property date_modification = model.createProperty(sPostcon2, sRelated11);
	        //Add the properties with associated values (objects)
	        proteins.addLiteral(date_modification,
	        val8);


	        Property pubmed_id = model.createProperty(sPostcon2, sRelated12);
	        //Add the properties with associated values (objects)
	        proteins.addLiteral(pubmed_id,
	        val9);

	        Property sub_location = model.createProperty(sPostcon2, sRelated13);
	        //Add the properties with associated values (objects)
	        proteins.addProperty(sub_location,
	        val10,XSDDatatype.XSDstring);

	        Property protein_family = model.createProperty(sPostcon2, sRelated14);
	        //Add the properties with associated values (objects)
	        proteins.addProperty(protein_family,
	        val11);//,XSDDatatype.XSDstring);

	     
	        
			}
			
			 //Print RDF/XML of model to system output
	        //model.write(new PrintWriter(System.out));
			model.close();
			log.close();
		}
			catch (Exception e) {
				System.out.println("Failed: " + e);
				}
			}
}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

