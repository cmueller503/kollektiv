package net.ddns.jazzsrv.kollektiv.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.ddns.jazzsrv.kollektiv.entity.Privilege;



public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

	Privilege findByName(String name);
}
