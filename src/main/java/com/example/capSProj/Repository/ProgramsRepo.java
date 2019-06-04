package com.example.capSProj.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

import com.example.capSProj.Model.Organization;
import com.example.capSProj.Model.Programs;


@Repository
public interface ProgramsRepo extends CrudRepository<Programs, Integer> {

		List<Programs> findByOrganization(Organization organization);
	
		Programs findByProgramid(Integer program_id);

}
