package com.example.capSProj.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="taxonomy")
public class Taxonomy {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int taxonomyid;

	private String TaxonomyName;

	public Taxonomy(String taxonomyName) {
		super();
		TaxonomyName = taxonomyName;
	}

	public String getTaxonomyName() {
		return TaxonomyName;
	}

	public void setTaxonomyName(String taxonomyName) {
		TaxonomyName = taxonomyName;
	}
	
	
}
