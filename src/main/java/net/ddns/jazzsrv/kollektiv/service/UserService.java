package net.ddns.jazzsrv.kollektiv.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import net.ddns.jazzsrv.kollektiv.entity.User;
import net.ddns.jazzsrv.kollektiv.repository.UserRepository;

@Service
public class UserService {
	private final UserRepository repository;

	public UserService(UserRepository repository) {
		super();
		this.repository = repository;
	}
	 

	public void save(User benutzer) {
		
		repository.save(benutzer);
	}

	public void delete(User benutzer) {
		repository.delete(benutzer);
		
	}

	public List<User> findAll() {
		return repository.findAll();
	}


	public Optional<User> findByUsername(String name) {
		// TODO Auto-generated method stub
		return repository.findByUserName(name);
	}
}
