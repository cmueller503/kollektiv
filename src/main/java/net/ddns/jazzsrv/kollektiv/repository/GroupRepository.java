package net.ddns.jazzsrv.kollektiv.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import net.ddns.jazzsrv.kollektiv.entity.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {
	Optional<Group> findByGroupName(String groupname);
}
