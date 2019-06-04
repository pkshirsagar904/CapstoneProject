package com.example.capSProj.Services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.example.capSProj.Model.Organization;
import com.example.capSProj.Model.Programs;
import com.example.capSProj.Repository.ProgramsRepo;

@Service
@Transactional
public class ProgramService {

	@Autowired
	private final ProgramsRepo progRep;

	public ProgramService(ProgramsRepo progRep) {
		this.progRep = progRep;
	}
	


	public void saveProgram(Programs programs) {
		progRep.save(programs);
	}
	
	public void deleteProgram(Integer programid) {
		progRep.deleteById(programid);
	}
	
	@Modifying
    @Transactional
	@Query("delete from programs u where u.programid = ?")
    public void deleteProgramsByprogramid(Integer programid) {
		
	}

//	public Programs findByProgramid(Integer program_id) {
//		return progRep.findById(program_id);
//	}

	

}
