package net.ddns.jazzsrv.kollektiv.entity;

import java.util.Collection;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class Benutzer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String benutzerName;
	
	private String passwort;
	
	private String rolle;
	
	  @ManyToMany 
	    @JoinTable( 
	        name = "users_roles", 
	        joinColumns = @JoinColumn(
	          name = "user_id", referencedColumnName = "id"), 
	        inverseJoinColumns = @JoinColumn(
	          name = "role_id", referencedColumnName = "id")) 
	    private Collection<Role> roles;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBenutzerName() {
		return benutzerName;
	}

	public void setBenutzerName(String userName) {
		this.benutzerName = userName;
	}

	public String getPasswort() {
		return passwort;
	}

	public void setPasswort(String password) {
		this.passwort = password;
	}

	public String getRolle() {
		return rolle;
	}

	public void setRolle(String role) {
		this.rolle = role;
	}
	
	
	
	
}