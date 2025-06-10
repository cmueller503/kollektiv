package net.ddns.jazzsrv.kollektiv.entity;

import java.util.Collection;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Privilege {
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    
    

    public Privilege() {
		super();
	}



	public Privilege(String name) {
		super();
		this.name = name;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public Collection<Role> getRoles() {
		return roles;
	}



	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}



	@ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;
}