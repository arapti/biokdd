package com.vaannila.protein;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.Table;

@Entity
@Table(name = "order10000")
public class RandomInteract10000 {

	private String id_interactor_A;
	private String id_interactor_B;

	private long row_a;
	private long row_b;

	public RandomInteract10000() {
	}

	public RandomInteract10000(String id_interactor_A, String id_interactor_B,
			long row_a, long row_b) {
		this.id_interactor_A = id_interactor_A;
		this.id_interactor_B = id_interactor_B;

		this.row_a = row_a;
		this.row_b = row_b;

	}

	@Id
	// @ManyToOne(cascade=CascadeType.MERGE)
	@GeneratedValue
	@Column(name = "Id_interactor_A", nullable = false)
	public String getIdinteractorA() {
		return this.id_interactor_A;
	}

	public void setIdinteractorA(String id_interactor_A) {
		this.id_interactor_A = id_interactor_A;
	}

	// @Id
	@GeneratedValue
	// @ManyToOne(cascade=CascadeType.MERGE)
	@Column(name = "Id_interactor_B", nullable = false)
	// @ManyToOne(cascade=CascadeType.MERGE)
	public String getIdinteractorB() {
		return this.id_interactor_B;
	}

	public void setIdinteractorB(String id_interactor_B) {
		this.id_interactor_B = id_interactor_B;
	}

	@Column(name = "row_A")
	public long getRowA() {
		return this.row_a;
	}

	public void setRowA(long row_a) {
		this.row_a = row_a;
	}

	@Column(name = "row_B")
	public long getRowB() {
		return this.row_b;
	}

	public void setRowB(long row_b) {
		this.row_b = row_b;
	}

}
