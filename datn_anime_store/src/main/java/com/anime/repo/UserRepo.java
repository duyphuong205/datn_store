package com.anime.repo;

import com.anime.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
	User findByUsernameAndIsActive(String name, Boolean isActive);
	
	User findByEmailAndIsActive(String name, Boolean isActive);
	
	@Modifying(clearAutomatically = true)
	@Query("UPDATE User u SET u.password = ?1 WHERE u.id = ?2")
	void updatePassword(String password, Long id);
}
