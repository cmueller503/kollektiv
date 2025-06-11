package net.ddns.jazzsrv.kollektiv;


import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import net.ddns.jazzsrv.kollektiv.entity.User;
import net.ddns.jazzsrv.kollektiv.repository.UserRepository;



@Configuration
public class InitData {

    @Bean
    public CommandLineRunner init(UserRepository benutzerRepository, PasswordEncoder encoder) {
        return args -> {
            if (benutzerRepository.findByUserName("admin").isEmpty()) {
                User admin = new User();
                admin.setUserName("admin");
                admin.setPasswort(encoder.encode("geheim"));
//                admin.setRolle("ROLE_ADMIN");
                benutzerRepository.save(admin);
            }
        };
    }
    
    
//   Privilege createPrivilegeIfNotFound(PrivilegeRepository privilegeRepository, String name) {
// 
//	   Privilege privilege = privilegeRepository.findByName(name);
//        if (privilege == null) {
//           privilege = new Privilege(name);
//           privilegeRepository.save(privilege);
//        }
//        return privilege;
//    }
//
//
//    Role createRoleIfNotFound(RoleRepository roleRepository,
//      String name, Collection<Privilege> privileges) {
// 
//    	Role role = roleRepository.findByName(name);
//        if (role == null) {
//            role = new Role(name);
//            role.setPrivileges(privileges);
//            roleRepository.save(role);
//        }
//        return role;
//    }
}
