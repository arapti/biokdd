package com.vaannila.protein;

import java.util.HashSet;

import java.util.Set;
 

import javax.persistence.CascadeType;

import javax.persistence.Column;

import javax.persistence.Entity;

import javax.persistence.GeneratedValue;

import javax.persistence.Id;

import javax.persistence.JoinColumn;

import javax.persistence.JoinTable;

import javax.persistence.OneToMany;

import javax.persistence.Table;

import com.vaannila.protein.Interactions;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;

@Entity
@Table(name = "protein")
public class Protein {
	private String accession;
	
	private String entry_name;
	
	private String status;
	private String protein_name;
	private String gene_names;
	private long length_;
	private String protein_existence;
    private String date_of_modification;
    private long pubmed_id;
    private String gene_location;
    private String protein_family;
    private long  protein_id;
    
   
    
    
    public Protein(){
    }
    
    	
    	public Protein(String protein_name){
    		this.protein_name=protein_name;
    }
	
    	@Id
    	@GeneratedValue
    	@Column(name = "Accession", nullable = false, length = 100)
    	public String getAccession() {
    	return this.accession;
    	}
	
    	public void setAccession(String accession) {
    		this.accession = accession;
    	}
    	
    	@OneToMany//(cascade ={ CascadeType.ALL})
    		@JoinTable(name = "interactions", 
    			joinColumns = { @JoinColumn(name = "Id_interactor_A", referencedColumnName="Accession"),
    			 @JoinColumn(name="Id_interactor_B",referencedColumnName="Accession",nullable=false) })
    			 private Set<Interactions> interactions = new HashSet<Interactions>(0);
    	
    	
    	
    	
    	//public Set<Interactions>  interactions; //getProteinInteractions() {
        	
        	//return this.proteinInteractions;
        	
        	//}
          	public void setProteinInteractions (Set<Interactions> interactions) {
          		this.interactions = interactions;
          		}
    	
        @Column(name = "Entry_name", nullable = false, length = 100)
    	
    	public String getEntryName() {
    	
    	return this.entry_name;
    	
    	} 
        
        public void setEntryName(String entry_name) {
    		this.entry_name = entry_name;
    	}
    	
        @Column(name = "Status", nullable = false, length = 100)
    	
    	public String getStatus() {
    	
    	return this.status;
    	
    	} 
        
        public void setStatus(String status) {
    		this.status = status;
    	}
        
        
    	
        
        
    	
    	@Column(name = "Protein_name", nullable = false, length = 100)
    	
    	public String getProteinName() {
    	
    	return this.protein_name;
    	
    	}    
    	
    	 public void setProteinName(String protein_name) {
     		this.protein_name = protein_name;
     	}
    	
    	 @Column(name = "Gene_names", nullable = false, length = 100)
     	
     	public String getGeneNames() {
     	
     	return this.gene_names;
     	
     	}    
     	
     	 public void setGeneNames(String gene_names) {
      		this.gene_names = gene_names;
      	}
    	 
     	 @Column(name = "Length_")
    	
    	public long getLength() {
    	
    	return this.length_;
    	
    	}    
    	
    	 public void setLength(long length_) {
     		this.length_ = length_;
     	}
    	
    	 @Column(name = "Protein_existence", nullable = false, length = 100)
     	
     	public String getProteinExistence() {
     	
     	return this.protein_existence;
     	
     	}    
     	
     	 public void setProteinExistence(String protein_existence) {
      		this.protein_existence = protein_existence;
      	}
     	 
       @Column(name = "Date_of_modification", nullable = false, length = 100)
    	
    	public String getDateOfModification() {
    	
    	return this.date_of_modification;
    	
    	}    
    	
    	 public void setDateOfModification(String date_of_modification) {
     		this.date_of_modification = date_of_modification;
     	}
    	 
    	 
     	 @Column(name = "Pubmed_Id")
    	
    	public long getPubmedId() {
    	
    	return this.pubmed_id;
    	
    	}    
    	
    	 public void setPubmedId(long pubmed_id) {
     		this.pubmed_id = pubmed_id;
     	}
    	 
    	 @Column(name = "Gene_location", nullable = false, length = 100)
      	
      	public String getGeneLocation() {
      	
      	return this.gene_location;
      	
      	}    
      	
      	 public void setGeneLocation(String gene_location) {
       		this.gene_location = gene_location;
       	}
    	 
    	 
      	 @Column(name = "Protein_family", nullable = false, length = 100)
      	
      	public String getProteinFamily() {
      	
      	return this.protein_family;
      	
      	}    
      	
      	 public void setProteinFamily(String protein_family) {
       		this.protein_family = protein_family;
       	}
      	 
      	 @Column(name = "Protein_Id")
     	
     	public long getId() {
     	
     	return this.protein_id;
     	
     	}    
     	
     	 public void setId(long protein_id) {
      		this.protein_id = protein_id;
      	}
      	
    	
      
      	
      	 
      	 
}
