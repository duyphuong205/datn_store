package com.anime.repo;

import com.anime.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
	User findByUsernameAndIsActive(String name, Boolean isActive);
	
	User findByEmailAndIsActive(String name, Boolean isActive);
}
