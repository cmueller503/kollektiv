package net.ddns.jazzsrv.kollektiv.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.vaadin.flow.spring.security.VaadinWebSecurity;

import net.ddns.jazzsrv.kollektiv.security.ui.view.LoginView;

@Configuration
@EnableMethodSecurity(jsr250Enabled = true) // Aktiviert @RolesAllowed
public class SecurityConfig extends VaadinWebSecurity {

    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Optional: Logging bei erfolgreichem Logout
    private LogoutSuccessHandler logoutSuccessHandler() {
        return (request, response, authentication) -> {
            if (authentication != null) {
                log.info("User '{}' logged out successfully.", authentication.getName());
            }
            response.sendRedirect("/login");
        };
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
        // Delegating the responsibility of general configurations
        // of http security to the super class. It's configuring
        // the followings: Vaadin's CSRF protection by ignoring
        // framework's internal requests, default request cache,
        // ignoring public views annotated with @AnonymousAllowed,
        // restricting access to other views/endpoints, and enabling
        // NavigationAccessControl authorization.
        // You can add any possible extra configurations of your own
        // here (the following is just an example):

        // http.rememberMe().alwaysRemember(false);

        // Configure your static resources with public access before calling
        // super.configure(HttpSecurity) as it adds final anyRequest matcher
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/manager/**").hasRole("MANAGER")
                .requestMatchers("/user/**").hasRole("USER")
                .requestMatchers("/login/**").permitAll()
//                .anyRequest().authenticated()
            );

        super.configure(http); 

        // This is important to register your login view to the
        // navigation access control mechanism:
        setLoginView(http, LoginView.class); 

    	// Zugriffsschutz über URL-Patterns
        /*+
        http.authorizeHttpRequests(auth -> auth
            .requestMatchers("/admin/**").hasRole("ADMIN")
            .requestMatchers("/manager/**").hasRole("MANAGER")
            .requestMatchers("/user/**").hasRole("USER")
//            .anyRequest().authenticated()
        );

        // Login- & Logout-Konfiguration
        http.formLogin(form -> form
            .loginPage("/login") // LoginView
            .permitAll()
        );

        http.logout(logout -> logout
            .logoutSuccessHandler(logoutSuccessHandler())
        );

        // Optional: CSRF für Vaadin deaktivieren (je nach Version nötig)
        http.csrf(csrf -> csrf
            .ignoringRequestMatchers("/vaadinServlet/**", "/VAADIN/**")
        );
        */

        log.info("Spring Security konfiguriert: Rollenbasierter Zugriff aktiv.");
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception {
        // Customize your WebSecurity configuration.
        super.configure(web);
    }
}