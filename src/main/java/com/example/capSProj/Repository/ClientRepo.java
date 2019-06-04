package com.example.capSProj.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.capSProj.Model.Client;

@Repository
public interface ClientRepo extends CrudRepository<Client, Integer> {
	
	public Client findByEmail(String email);
	
	public Client findByid(Integer id);
	
}
