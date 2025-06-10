package net.ddns.jazzsrv.kollektiv;


import java.util.Collection;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import net.ddns.jazzsrv.kollektiv.entity.Benutzer;
import net.ddns.jazzsrv.kollektiv.entity.Privilege;
import net.ddns.jazzsrv.kollektiv.entity.Role;
import net.ddns.jazzsrv.kollektiv.repository.BenutzerRepository;
import net.ddns.jazzsrv.kollektiv.repository.PrivilegeRepository;
import net.ddns.jazzsrv.kollektiv.repository.RoleRepository;



@Configuration
public class InitData {

    @Bean
    public CommandLineRunner init(BenutzerRepository benutzerRepository, PasswordEncoder encoder) {
        return args -> {
            if (benutzerRepository.findByBenutzerName("admin").isEmpty()) {
                Benutzer admin = new Benutzer();
                admin.setBenutzerName("admin");
                admin.setPasswort(encoder.encode("geheim"));
                admin.setRolle("ROLE_ADMIN");
                benutzerRepository.save(admin);
            }
        };
    }
    
    
   Privilege createPrivilegeIfNotFound(PrivilegeRepository privilegeRepository, String name) {
 
	   Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
           privilege = new Privilege(name);
           privilegeRepository.save(privilege);
        }
        return privilege;
    }


    Role createRoleIfNotFound(RoleRepository roleRepository,
      String name, Collection<Privilege> privileges) {
 
    	Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }
}
