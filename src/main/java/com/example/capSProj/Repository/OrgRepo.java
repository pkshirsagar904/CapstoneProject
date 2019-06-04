package com.example.capSProj.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.capSProj.Model.Organization;

@Repository
public interface OrgRepo extends CrudRepository<Organization, Integer> {
	
	public Organization findByOrgid(Integer orgid);

	public Organization findByEmail(String email);

	public Organization findByName(String name);
	

}
