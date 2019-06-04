package com.example.capSProj.Services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.capSProj.Model.Client;
import com.example.capSProj.Repository.ClientRepo;

@Service
@Transactional
public class ClientService {

	
	@Autowired
	private final ClientRepo cR;

	public ClientService(ClientRepo cR) {
		this.cR = cR;
	}
	
	public void saveClient(Client client) {
		cR.save(client);
	}
	
}
