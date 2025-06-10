package net.ddns.jazzsrv.kollektiv.service;

import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import net.ddns.jazzsrv.kollektiv.repository.BenutzerRepository;

@Service
public class BenutzerDetailsService implements UserDetailsService {

    private final BenutzerRepository repository;

    public BenutzerDetailsService(BenutzerRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	LoggerFactory.getLogger(this.getClass()).error("CM_DEBUG 1");
        UserDetails ret = repository.findByBenutzerName(username)
                .map(b -> User.builder()
                        .username(b.getBenutzerName())
                        .password(b.getPasswort())
                        .roles(b.getRolle().replace("ROLE_", "")
                        		)
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("Benutzer nicht gefunden"));
        LoggerFactory.getLogger(this.getClass()).error("CM_DEBUG 1 " + ret.getUsername() + ", " + ret.getPassword() + ", " + ret.getAuthorities());
        return ret;
    }
}