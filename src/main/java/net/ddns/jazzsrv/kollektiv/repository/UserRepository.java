package net.ddns.jazzsrv.kollektiv.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import net.ddns.jazzsrv.kollektiv.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUserName(String username);
	
	
}
