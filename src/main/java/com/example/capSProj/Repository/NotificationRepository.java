package com.example.capSProj.Repository;

import org.springframework.data.repository.CrudRepository;
import java.util.*;
import com.example.capSProj.Model.Notification;

public interface NotificationRepository extends CrudRepository<Notification, Integer> {
	
	List<Notification> findByOrgName(String id);

	public Notification findByid(Integer id);
}
