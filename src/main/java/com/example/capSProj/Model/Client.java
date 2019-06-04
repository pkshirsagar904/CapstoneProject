package com.example.capSProj.Model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="client")
public class Client {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	
	@Column(name="username")
	private String username;
	
	@Column(name="name", length=100)
	private String name;

	
	
	@Column(name="password", length=50)
	private String password;
	
	@Column(name="email", length=60)
	private String email;
	
	@Column(name="age",length=15)
	private String age;
	
	

	@OneToMany(cascade = {CascadeType.ALL},mappedBy="clientid")
	private List<Services> services;
	

	public Client() {
		
	}
	
	

	public Client(Integer id, String username, String name, String password, String email, String age) {
		this.id = id;
		this.username = username;
		this.name = name;
		this.password = password;
		this.email = email;
		this.age = age;
	
	}



	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getAge() {
		return age;
	}



	public void setAge(String age) {
		this.age = age;
	}




	public List<Services> getServices() {
		return services;
	}



	public void setServices(List<Services> services) {
		this.services = services;
	}

}
