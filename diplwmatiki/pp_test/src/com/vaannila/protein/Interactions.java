package com.vaannila.protein;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "interactions")
public class Interactions {
	
	private String id_interactor_A;
	private String id_interactor_B;

	//private String id_interactor_B;
	
	private String interaction_type;
	
	private long publication_identifier;
	
	public Interactions(){
	}
	
	public Interactions(String id_interactor_A,String id_interactor_B,String interaction_type, long publication_identifier){
		this.id_interactor_A=id_interactor_A;
		this.id_interactor_B=id_interactor_B;
		
		this.interaction_type=interaction_type;
		this.publication_identifier=publication_identifier;
		}
	
	
	
	@Id
	//@ManyToOne(cascade=CascadeType.MERGE)
	@GeneratedValue
	@Column(name = "Id_interactor_A", nullable = false)
	
	
	
	
	public String getIdinteractorA() {
	return this.id_interactor_A;
	}
	
	public void setIdinteractorA(String id_interactor_A) {
		this.id_interactor_A = id_interactor_A;
		}
	
	
    //@Id
    @GeneratedValue
    //@ManyToOne(cascade=CascadeType.MERGE)
    @Column(name = "Id_interactor_B", nullable = false)
	
	//@ManyToOne(cascade=CascadeType.MERGE)
	public String getIdinteractorB() {
	return this.id_interactor_B;
	}
	
	public void setIdinteractorB(String id_interactor_B) {
		this.id_interactor_B = id_interactor_B;
		}
	
 
	
	@Column(name = "Interaction_type", nullable = false, length = 100)
	public String getInteractionType() {
	return this.interaction_type;
	}
	
	public void setInteractionType(String interaction_type) {
		this.interaction_type = interaction_type;
		}
	
	@Column(name = "Publication_identifier")
	public long getPublicationIdentifier() {
	return this.publication_identifier;
	}
	
	public void setPublicationIdentifier(long publication_identifier) {
		this.publication_identifier = publication_identifier;
		}
	
	
	}


