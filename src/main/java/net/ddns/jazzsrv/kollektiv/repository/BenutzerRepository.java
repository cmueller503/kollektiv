package net.ddns.jazzsrv.kollektiv.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import net.ddns.jazzsrv.kollektiv.entity.Benutzer;

public interface BenutzerRepository extends JpaRepository<Benutzer, Long> {
    Optional<Benutzer> findByBenutzerName(String benutzername);
}
