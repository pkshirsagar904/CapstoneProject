package com.example.capSProj.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.capSProj.Model.Client;
import com.example.capSProj.Model.Programs;
import com.example.capSProj.Model.Services;

@Repository
public interface ServiceRepo extends CrudRepository<Services, Integer>{

//	List<Services> findByServiceid(Integer programid);
	
//	public Services findbyService_Id(Integer serviceid);

	public Services findByserviceid(Integer serviceid);
	
	public List<Services> findByserviceprogram(Programs serviceprogram);

}
