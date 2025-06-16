package net.ddns.jazzsrv.kollektiv.entity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum Role {
    ADMIN("admin"),
    USER("user"),
    MANAGER("manager");
	
	private String name;

	private Role(String name) {
		this.name = name;
	}
	
	public static Set<Role> valuesAsSet() {
		return new HashSet<>(Arrays.asList(values()));
	}

	public String getName() {
		return name;
	}
    
}