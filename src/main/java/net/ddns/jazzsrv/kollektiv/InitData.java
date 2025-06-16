package net.ddns.jazzsrv.kollektiv;


import java.util.Arrays;
import java.util.HashSet;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import net.ddns.jazzsrv.kollektiv.entity.Group;
import net.ddns.jazzsrv.kollektiv.entity.Role;
import net.ddns.jazzsrv.kollektiv.entity.User;
import net.ddns.jazzsrv.kollektiv.repository.GroupRepository;
import net.ddns.jazzsrv.kollektiv.repository.UserRepository;



@Configuration
public class InitData {

    @Bean
    public CommandLineRunner init(UserRepository benutzerRepository, PasswordEncoder encoder, GroupRepository groupRepository) {
        return args -> {
            if (groupRepository.findByGroupName("admin").isEmpty()) {
                Group adminGroup = new Group();
                adminGroup.setGroupName("admin");
                Group admin_group = groupRepository.save(adminGroup);
                if (benutzerRepository.findByUserName("admin").isEmpty()) {
                    User admin = new User();
                    admin.setUserName("admin");
                    admin.setPasswort(encoder.encode("geheim"));
                    admin.getGroups().add(admin_group);
                    admin.setRoles(new HashSet<Role>(Arrays.asList(Role.ADMIN)));
//                    admin.setRolle("ROLE_ADMIN");
                    admin = benutzerRepository.save(admin);
                }
            }
            // Erst Gruppe anlegen, dann Benutzer und Gruppe dem Benutzer hinzufügen: funktioniert!
            if (groupRepository.findByGroupName("project_x").isEmpty()) {
                Group group = new Group();
                group.setGroupName("project_x");
                group = groupRepository.save(group);
                if (benutzerRepository.findByUserName("user_x").isEmpty()) {
                    User user = new User();
                    user.setUserName("user_x");
                    user.setPasswort(encoder.encode("geheim"));
                    user.getGroups().add(group);
                    user.setRoles(new HashSet<Role>(Arrays.asList(Role.MANAGER, Role.USER)));
//                    admin.setRolle("ROLE_ADMIN");
                    user = benutzerRepository.save(user);
                }
            }
            // Erst Benutzer anlegen, dann Gruppe und Benutzer der Gruppe hinzufügen: funktioniert nicht!
            if (benutzerRepository.findByUserName("user_y").isEmpty()) {
                User user = new User();
                user.setUserName("user_y");
                user.setPasswort(encoder.encode("geheim"));
                user.setRoles(new HashSet<Role>(Arrays.asList(Role.USER)));
//                admin.setRolle("ROLE_ADMIN");
                user = benutzerRepository.save(user);
                if (groupRepository.findByGroupName("project_y").isEmpty()) {
                    Group group = new Group();
                    group.setGroupName("project_y");
                    group.getUsers().add(user);
                    group = groupRepository.save(group);
                }
                
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
