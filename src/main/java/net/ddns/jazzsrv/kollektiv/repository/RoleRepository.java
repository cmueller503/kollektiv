package net.ddns.jazzsrv.kollektiv.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.ddns.jazzsrv.kollektiv.entity.Role;

	

public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByName(String name);

}
