package com.example.capSProj.Model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Service")
public class Services {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer serviceid;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "client_id")
	private Client clientid;

	@Column(name = "servName")
	private String serviceName;

	@Column(name = "servdescription")
	private String servDescription;

	@Column(name = "servaliases")
	private String servAliases;
	
	@Column(name = "servkeywords")
	private String servKeywords;

	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
	@JoinColumn(name = "programid")
	private Programs serviceprogram;

	public Services() {

	}

	public Services(Integer serviceid, Client client, String serviceName, String servDescription, String servAliases,
			String servKeywords) {
		super();
		this.serviceid = serviceid;
		this.clientid = client;
		this.serviceName = serviceName;
		this.servDescription = servDescription;
		this.servAliases = servAliases;
		this.servKeywords=servKeywords;

	}

	public Integer getServiceid() {
		return serviceid;
	}

	public void setServiceid(Integer serviceid) {
		this.serviceid = serviceid;
	}

	public Client getClient() {
		return clientid;
	}

	public void setClient(Client client) {
		this.clientid = client;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServDescription() {
		return servDescription;
	}

	public void setServDescription(String servDescription) {
		this.servDescription = servDescription;
	}

	public String getServAliases() {
		return servAliases;
	}

	public void setServAliases(String servAliases) {
		this.servAliases = servAliases;
	}

	public Programs getServofprograms() {
		return serviceprogram;
	}

	public void setServofprograms(Programs servofprograms) {
		this.serviceprogram = servofprograms;
	}

	public String getServKeywords() {
		return servKeywords;
	}

	public void setServKeywords(String servKeywords) {
		this.servKeywords = servKeywords;
	}

	public Programs getServiceprogram() {
		return serviceprogram;
	}

	public void setServiceprogram(Programs serviceprogram) {
		this.serviceprogram = serviceprogram;
	}

	
	
}
