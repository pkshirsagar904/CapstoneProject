package com.example.capSProj.Services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.capSProj.Model.Services;
import com.example.capSProj.Repository.ServiceRepo;

@Service
@Transactional
public class ServService {

	@Autowired
	private final ServiceRepo sRepo;

	public ServService(ServiceRepo sRepo) {
		this.sRepo = sRepo;
	}
	
	public void saveService(Services services) {
		sRepo.save(services);
	}
	
//	public Services findByServiceid(Services serviceid) {
//		sRepo.findByServiceid(serviceid);
//	}
	
//	public Services findbyserviceid(Integer Service_Id) {
//		return sRepo.findbyService_Id(Service_Id);
//	}
}
