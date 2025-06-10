package net.ddns.jazzsrv.kollektiv;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.vaadin.flow.spring.security.VaadinWebSecurity;

import net.ddns.jazzsrv.kollektiv.security.ui.view.LoginView;

@Configuration
public class SecurityConfig extends VaadinWebSecurity {
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http); 
        // Eigene Regeln für öffentliche Seiten
        /**
        http.authorizeHttpRequests(auth -> auth
            .requestMatchers("/registrieren").permitAll()
            .anyRequest().authenticated()
        );
        */
        setLoginView(http, LoginView.class);
    }

	/**

    @Bean
    public UserDetailsManager userDetailsManager() {
    	LoggerFactory.getLogger(SecurityConfig.class)
        .error("NOT FOR PRODUCITON: Using in-memory user details manager!"); 
    	var user = User.withUsername("user")
            .password("user")
            .roles("USER")
            .build();
    	var admin = User.withUsername("admin")
            .password("admin")
            .roles("ADMIN")
            .build();
    	return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/registrieren").permitAll()
                .anyRequest().authenticated()
            )
            //.formLogin(form -> form.loginPage("/login").permitAll())
            .formLogin(Customizer.withDefaults())
            //.logout(logout -> logout.permitAll())
            .logout(Customizer.withDefaults());

        return http.build();
    }
    **/

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
