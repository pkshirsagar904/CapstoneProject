package com.example.capSProj.Repository;

import org.springframework.data.repository.CrudRepository;

import com.example.capSProj.Model.Messenger;

public interface MessengerRepository extends CrudRepository<Messenger, Integer> {

	
}
