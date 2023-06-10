package com.anime.repo;

import com.anime.entity.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	
	@Query(value = "SELECT u FROM User u JOIN UserRole ur ON u.id = ur.user.id JOIN Role r ON ur.role.id = r.id "
			+ "WHERE u.isActive = ?1 AND r.name in ('CUSTOMER')")
	Page<User> findAllCustomer(Boolean isActive, Pageable pageable);
	
	@Query(value = "SELECT * FROM users u JOIN user_roles ur ON u.id = ur.user_id JOIN roles r ON ur.role_id = r.id "
			+ "WHERE u.is_active = ?1 AND CONCAT(u.username, u.fullname, u.email) LIKE %?2% AND r.name in ('CUSTOMER')", nativeQuery = true)
	Page<User> findAllSearchCustomer(Boolean isActive, String keyword, Pageable pageable);
	
	@Modifying(clearAutomatically = true)
	@Query("UPDATE User u SET u.isActive = ?1 WHERE u.id = ?2")
	void deleteLogical(Boolean isActive, Long id);
	
	@Query("FROM User u WHERE u.username=?1 OR email=?1")
	User findByUsernameOrEmail(String username);
}
