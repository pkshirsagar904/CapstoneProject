package com.example.capSProj.Model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="organization")
public class Organization {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer orgid;
	
	@Column(name="address")
	private String address;
	
	@Column(name="Mission")
	private String Mission;
	
	@Column(name="Keywords")
	private String Keywords;
	
	@Column(name="Aliases")
	private String Aliases;
	
	@Column(name="LanguageSpoken")
	private String LanguageSpoken;
	
	@Column(name="EmployerIdentificationNumber")
	private String EmployerIdentificationNumber;
	
	@Column(name="GeographicArea")
	private String GeographicArea;
	
	@Column(name="OtherLocations")
	private String OtherLocations;
	
	@Column(name="State")
	private String State;
	
	@Column(name="City")
	private String City;
	
	@Column(name="ZipCode")
	private String ZipCode;
	
	@Column(name="name")
	private String name;
	
	
	@Column(name="email")
	private String email;
	
	@Column(name="password")
	private String password;
	
	@Column(name="fax")
	private String fax;
	
	@Column(name="webAddress")
	private String webAddress;
	
	@Column(name="orgType")
	private String orgType;
	
	@Column(name="phone")
	private String phone;
	
	@Column(name="mailingAdd")
	private String mailingAdd;
	
	@Column(name="hours")
	private String hours;
	
	@Column(name="aboutUs")
	private String aboutUs;
	
//	@Column(name="lastUpdate")
//	private String lastUpdate;
//	
//	@Column(name="maninspdate")
//	private String maninspdate;
//	
//	@Column(name="status")
//	private String status;
//	
	
//	private String 
	
	@OneToMany(cascade = {CascadeType.ALL},mappedBy="organization", fetch = FetchType.EAGER)
	private List<Programs> programs;

	
	public Organization() {
		
	}


//	public Organization(Integer orgid, String address, String name, String email, String password, String fax,
//			String webAddress, String orgType, String phone, String mailingAdd, String hours, String aboutUs) {
//		super();
//		this.orgid = orgid;
//		this.address = address;
//		this.name = name;
//		this.email = email;
//		this.password = password;
//		this.fax = fax;
//		this.webAddress = webAddress;
//		this.orgType = orgType;
//		this.phone = phone;
//		this.mailingAdd = mailingAdd;
//		this.hours = hours;
//		this.aboutUs = aboutUs;
//		
//	}


	public Integer getOrgid() {
		return orgid;
	}


	public Organization(Integer orgid, String address, String mission, String keywords, String aliases,
		String languageSpoken, String employerIdentificationNumber, String geographicArea, String otherLocations,
		String name, String email, String password, String fax, String webAddress, String orgType, String phone,
		String mailingAdd, String hours, String aboutUs, String State, String City) {
	super();
	this.orgid = orgid;
	this.address = address;
	this.Mission = mission;
	this.Keywords = keywords;
	this.Aliases = aliases;
	this.LanguageSpoken = languageSpoken;
	this.EmployerIdentificationNumber = employerIdentificationNumber;
	this.GeographicArea = geographicArea;
	this.OtherLocations = otherLocations;
	this.name = name;
	this.email = email;
	this.password = password;
	this.fax = fax;
	this.webAddress = webAddress;
	this.orgType = orgType;
	this.phone = phone;
	this.mailingAdd = mailingAdd;
	this.hours = hours;
	this.aboutUs = aboutUs;
	this.State=State;
	this.City=City;
}


	public void setOrgid(Integer orgid) {
		this.orgid = orgid;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getFax() {
		return fax;
	}


	public void setFax(String fax) {
		this.fax = fax;
	}


	public String getWebAddress() {
		return webAddress;
	}


	public void setWebAddress(String webAddress) {
		this.webAddress = webAddress;
	}


	public String getOrgType() {
		return orgType;
	}


	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getMailingAdd() {
		return mailingAdd;
	}


	public void setMailingAdd(String mailingAdd) {
		this.mailingAdd = mailingAdd;
	}


	public String getHours() {
		return hours;
	}


	public void setHours(String hours) {
		this.hours = hours;
	}


	public String getAboutUs() {
		return aboutUs;
	}


	public void setAboutUs(String aboutUs) {
		this.aboutUs = aboutUs;
	}


	public List<Programs> getPrograms() {
		return programs;
	}


	public void setPrograms(List<Programs> programs) {
		this.programs = programs;
	}


	public String getMission() {
		return Mission;
	}


	public void setMission(String mission) {
		Mission = mission;
	}


	public String getKeywords() {
		return Keywords;
	}


	public void setKeywords(String keywords) {
		Keywords = keywords;
	}


	public String getAliases() {
		return Aliases;
	}


	public void setAliases(String aliases) {
		Aliases = aliases;
	}


	public String getLanguageSpoken() {
		return LanguageSpoken;
	}


	public void setLanguageSpoken(String languageSpoken) {
		LanguageSpoken = languageSpoken;
	}


	public String getEmployerIdentificationNumber() {
		return EmployerIdentificationNumber;
	}


	public void setEmployerIdentificationNumber(String employerIdentificationNumber) {
		EmployerIdentificationNumber = employerIdentificationNumber;
	}


	

	public String getGeographicArea() {
		return GeographicArea;
	}


	public void setGeographicArea(String geographicArea) {
		GeographicArea = geographicArea;
	}


	public String getZipCode() {
		return ZipCode;
	}


	public void setZipCode(String zipCode) {
		ZipCode = zipCode;
	}


	public String getOtherLocations() {
		return OtherLocations;
	}


	public void setOtherLocations(String otherLocations) {
		OtherLocations = otherLocations;
	}


	public String getState() {
		return State;
	}


	public void setState(String state) {
		State = state;
	}


	public String getCity() {
		return City;
	}


	public void setCity(String city) {
		City = city;
	}

	
}