package com.example.capSProj.Model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "programs")
public class Programs {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer programid;

	@Column(name = "progName")
	private String progName;

	@Column(name = "description", length = 255)
	private String description;

	@Column(name = "keywords")
	private String keywords;
	
	@Column(name = "aliases")
	private String aliases;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "orgid")
	private Organization organization;

	@OneToMany(mappedBy = "serviceprogram")
	private List<Services> selectedservices;

	public Programs() {

	}

	public Programs(Integer programid, String progName, String description, String aliases, Organization organization,
			String keywords) {

		this.programid = programid;
		this.progName = progName;
		this.description = description;
		this.aliases = aliases;
		this.organization = organization;
		this.keywords=keywords;
	}

	

	public String getProgName() {
		return progName;
	}

	public void setProgName(String progName) {
		this.progName = progName;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public List<Services> getSelectedservices() {
		return selectedservices;
	}

	public Integer getProgramid() {
		return programid;
	}

	public void setProgramid(Integer programid) {
		this.programid = programid;
	}

	public void setSelectedservices(List<Services> selectedservices) {
		this.selectedservices = selectedservices;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAliases() {
		return aliases;
	}

	public void setAliases(String aliases) {
		this.aliases = aliases;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

}
