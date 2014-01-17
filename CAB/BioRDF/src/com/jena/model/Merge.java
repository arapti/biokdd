package com.jena.model;

import java.io.IOException;
import java.io.PrintWriter;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.ReadWrite;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import com.hp.hpl.jena.tdb.TDBFactory;

public class Merge {

	@SuppressWarnings("deprecation")
	public static void main(String args[]) throws IOException {

		String directory = "C:/Users/angeliki/Desktop/fin_model";
		String dir = "C:/Users/angeliki/Desktop/inter_model";
		String dir2 = "C:/Users/angeliki/Desktop/protein_model";

		
		Model model1 = TDBFactory.createModel(dir);
	
		
		Model model2 = TDBFactory.createModel(dir2);

		//Dataset ds = TDBFactory.createDataset(directory);
		// we create the final model where the previous models are merged
		Model model = TDBFactory.createModel(directory);

		// we create the final model where we merge models model1 and model2
		// Model model = ModelFactory.createDefaultModel();
//ds.begin(ReadWrite.WRITE);
		model.add(model1);
		model.add(model2);
		//ds.end();
		//model1.close();
		//model2.close();
		// print the Model as RDF/XML
		model.write(new PrintWriter(System.out));
		// ds.close();

		model.close();
		//ds.close();

	}

}
