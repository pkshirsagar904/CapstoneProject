package com.example.capSProj.Services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.capSProj.Model.Organization;
import com.example.capSProj.Repository.OrgRepo;

@Service
@Transactional
public class OrganizationService {

	@Autowired
	private final OrgRepo orgRepo;

	public OrganizationService(OrgRepo orgRepo) {
		this.orgRepo = orgRepo;
	}

	public Organization findByOrgEmail(String email) {
		return orgRepo.findByEmail(email);
	}

	public Organization findByOrgid(Integer orgid) {
		return orgRepo.findByOrgid(orgid);
	}
	
	public Organization findByOrgName(String name) {
		return orgRepo.findByName(name);
	}

	public void saveMyOrganization(Organization org) {
		orgRepo.save(org);
	}

}
