package com.jena.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.sparql.function.library.bnode;
import com.hp.hpl.jena.tdb.TDBFactory;

public class Protein2 extends Object {

	@SuppressWarnings("deprecation")
	public static void main(String args[]) throws IOException {

		try {
			File datap = new File("C:/Users/angeliki/Desktop/proteinPPI.txt");
			// System.out.println(datap.getCanonicalPath());
			BufferedReader bReader = new BufferedReader(new FileReader(datap));

			String line;

			String directory = "C:/Users/angeliki/Desktop/protein_model";

			// Create an empty graph
			Model model = TDBFactory.createModel(directory);

			while (((line = bReader.readLine()) != null)) {

				/**
				 * Splitting the content of tabbed separated line
				 */
				String datavalue[] = line.split("\t"+";", -1);
				String value1 = datavalue[0];//Accession
				String value2 = datavalue[1];//Entry_name
				String value3 = datavalue[2];//Protein_name
				String value4 = datavalue[3];//Gene_names
				long value5 = Integer.parseInt(datavalue[4]);//Length
				String value6 = datavalue[5];//Protein_existence
				String value7 = datavalue[6];//Date_of_modification
				String value8 = datavalue[7];//Protein_family
				long value9 = Integer.parseInt(datavalue[8]);//Protein_Id

				String sPostcon = "http://www.protein_interactions.com/";
				
				String sRelated1 = "Accession";
				String sRelated2 ="Entry_name";
				String sRelated3 ="Protein_name";
				String sRelated4 ="Gene_names";
				String sRelated5 ="Length";
				String sRelated6 ="Protein_existence";
				String sRelated7 ="Modification_Date";
				String sRelated8 ="Protein_family";
				String sRelated9 ="Protein_Id";
				

				// String sRelated4="interaction";

				// Create the resources
				Resource proteins = model.createResource(sPostcon + value1);
				
				Property accession=model.createProperty(sPostcon,sRelated1);
				proteins.addProperty(accession,value1,XSDDatatype.XSDstring);
				
				Property entry_name=model.createProperty(sPostcon,sRelated2);
				proteins.addProperty(entry_name,value2,XSDDatatype.XSDstring);
				
				Property protein_name=model.createProperty(sPostcon,sRelated3);
				proteins.addProperty(protein_name,value3,XSDDatatype.XSDstring);
				
				Property gene_name=model.createProperty(sPostcon,sRelated4);
				proteins.addProperty(gene_name,value4,XSDDatatype.XSDstring);
				
				Property length=model.createProperty(sPostcon,sRelated5);
				proteins.addLiteral(length,value5);
				
				Property protein_existence=model.createProperty(sPostcon,sRelated6);
				proteins.addProperty(protein_existence,value6,XSDDatatype.XSDstring);
				
				
				Property modification_date=model.createProperty(sPostcon,sRelated7);
				proteins.addProperty(modification_date,value7,XSDDatatype.XSDstring);
			
				Property protein_family=model.createProperty(sPostcon,sRelated8);
				proteins.addProperty(protein_family,value8,XSDDatatype.XSDstring);
				
				Property protein_id=model.createProperty(sPostcon,sRelated9);
				proteins.addLiteral(protein_id,value9);
				
			}
			// Print RDF/XML of model to system output
			model.write(new PrintWriter(System.out));
			model.close();

			bReader.close();

		} catch (Exception e) {
			System.out.println("Failed: " + e);
		}

	}

}
